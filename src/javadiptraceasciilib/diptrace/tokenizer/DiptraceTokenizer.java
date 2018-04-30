package javadiptraceasciilib.diptrace.tokenizer;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * This class has methods to parse a DipTrace ascii file into tokens.
 */
public final class DiptraceTokenizer {
    
    /**
     * The reader that the tokenizer reads from.
     */
    private final BufferedReader fReader;
    
    /**
     * The current line that the tokenizer reads from.
     */
    private final StringBuilder fCurrentLine;
    
    /**
     * The line number of the current line.
     */
    private int fLineNo = 1;
    
    /**
     * True if the last token was a left parentheses.
     */
    private boolean fLastTokenWasLeftParentheses = false;
    
    /**
     * The next token.
     */
    private DiptraceToken fNextToken;
    
    /**
     * Initializes a DiptraceTokenizer object with a reader that reads a
     * Diptrace ASCII file.
     * @param reader the reader
     * @throws IOException on any I/O error
     */
    public DiptraceTokenizer(final BufferedReader reader) throws IOException {
        
        this.fReader = reader;
        
        String line = reader.readLine();
        if (line != null) {
            this.fCurrentLine = new StringBuilder(line.trim());
        } else {
            this.fCurrentLine = null;
        }
    }
    
    /**
     * Eat the current token. It checks that the token is of a particular type.
     * @param type the type of token that is expected.
     * @throws RuntimeException if the token is not of the expected type
     * @throws IOException on any I/O error
     */
    public void eatToken(final DiptraceTokenType type) throws IOException {
        if (fNextToken == null) {
            fNextToken = fetchNextToken();
        }
        
        if (fNextToken == null) {
            throw new RuntimeException(
                String.format(
                    "End of file has been reached premature. Token to eat: %s",
                    type.name()));
        }
        
        if (fNextToken.getType() != type) {
            throw new RuntimeException(
                String.format("Token is not a %s token: Type: %s, %s",
                    type.name(),
                    fNextToken.getType().name(),
                    fNextToken.getValue()));
        }
        
        fNextToken = null;
    }
    
    
    /**
     * Returns the next token.
     * @return the token
     * @throws IOException on any I/O error
     */
    public DiptraceToken nextToken() throws IOException {
        if (fNextToken != null) {
            DiptraceToken token = fNextToken;
            fNextToken = null;
            return token;
        }
        return fetchNextToken();
    }
    
    
    /**
     * Returns the next token but keeps it in the queue.
     * @return the next token
     * @throws IOException on any I/O error
     */
    public DiptraceToken previewNextToken() throws IOException {
        if (fNextToken == null) {
            fNextToken = fetchNextToken();
        }
        return fNextToken;
    }
    
    
    /**
     * Fetch the next token from the reader.
     * @return the next token
     * @throws IOException on any I/O error
     */
    private DiptraceToken fetchNextToken() throws IOException {
        
        boolean firstTokenOnLine = false;
        
        while ((fCurrentLine != null) && (fCurrentLine.length() == 0)) {
            fCurrentLine.setLength(0);
            String line = fReader.readLine();
            
            // End of file?
            if (line == null) {
                return null;
            }
            
            fCurrentLine.append(line.trim());
            fLineNo++;
            
            firstTokenOnLine = true;
        }
        
        if (fCurrentLine == null) {
            return null;
        } else if (fCurrentLine.charAt(0) == '(') {
            fCurrentLine.delete(0, 1);
            fLastTokenWasLeftParentheses = true;
            return new DiptraceToken(
                            DiptraceTokenType.LEFT_PARENTHESES,
                            firstTokenOnLine);
        } else if (fCurrentLine.charAt(0) == ')') {
            fCurrentLine.delete(0, 1);
            return new DiptraceToken(
                            DiptraceTokenType.RIGHT_PARENTHESES,
                            firstTokenOnLine);
        } else {
            String tokenValue;
            
            if (fCurrentLine.charAt(0) == '"') {
                int pos = 1;
                while ((pos < fCurrentLine.length())
                        && (fCurrentLine.charAt(pos) != '"')) {
                    pos++;
                }
                
                if (pos == fCurrentLine.length()) {
                    throw new RuntimeException(
                        String.format(
                            "Invalid string token. No \" at end of string."
                            + "LineNo: %d, %s",
                        fLineNo,
                        fCurrentLine));
                }
                
                tokenValue = fCurrentLine.substring(1, pos);
                fCurrentLine.delete(0, pos + 1);
                
                while ((fCurrentLine.length() > 0)
                        && (fCurrentLine.charAt(0) == ' ')) {
                    fCurrentLine.delete(0, 1);
                }
                
                return new DiptraceToken(
                                DiptraceTokenType.STRING,
                                tokenValue,
                                firstTokenOnLine);
            }
            
            
            int spacePos = fCurrentLine.indexOf(" ");
            if (spacePos >= 0) {
                tokenValue = fCurrentLine.substring(0, spacePos);
                fCurrentLine.delete(0, spacePos + 1);
            } else {
                int length = fCurrentLine.length();
                
                if (fCurrentLine.charAt(length - 1) == ')') {
                    tokenValue = fCurrentLine.substring(0, length - 1);
                    fCurrentLine.delete(0, length - 1);
                } else {
                    tokenValue = fCurrentLine.substring(0, length);
                    fCurrentLine.setLength(0);    // Clear string
                }
            }
            
            if (fLastTokenWasLeftParentheses) {
                fLastTokenWasLeftParentheses = false;
                return new DiptraceToken(
                    DiptraceTokenType.IDENTIFIER,
                    tokenValue,
                    firstTokenOnLine);
            } else {
                try {
                    int value = Integer.parseInt(tokenValue);
                    // If we are here, the fValue is a valid integer
                    return new DiptraceToken(
                        DiptraceTokenType.INTEGER,
                        tokenValue, value,
                        firstTokenOnLine);
                } catch (NumberFormatException e) {
                    // If we are here, fValue is not an integer so we just
                    // continue to the next statement.
                }
                
                if ((tokenValue.length() > 0)
                    && (tokenValue.charAt(tokenValue.length() - 1) == '%')) {
                    
                    String percentValue
                        = tokenValue.substring(0, tokenValue.length() - 1);
                    
                    try {
                        double value = Double.parseDouble(percentValue);
                        // If we are here, the fValue is a valid double
                        return new DiptraceToken(
                                        DiptraceTokenType.PERCENT,
                                        tokenValue,
                                        value,
                                        firstTokenOnLine);
                    } catch (NumberFormatException e) {
                        // If we are here, fValue is not a double so we just
                        // continue to the next statement.
                    }
                }
                
                try {
                    double value = Double.parseDouble(tokenValue);
                    // If we are here, the fValue is a valid double
                    return new DiptraceToken(
                                    DiptraceTokenType.FLOAT,
                                    tokenValue,
                                    value,
                                    firstTokenOnLine);
                } catch (NumberFormatException e) {
                    // If we are here, fValue is not a double so we just
                    // continue to the next statement.
                }
                
                // Check if token value is a identifier
                boolean isIdentifier =
                    (tokenValue.length() > 0)
                    && (Character.isAlphabetic(tokenValue.charAt(0)));
                
                for (int i = 0; i < tokenValue.length(); i++) {
                    if (!Character.isLetterOrDigit(tokenValue.charAt(i))) {
                        isIdentifier = false;
                    }
                }
                
                if (isIdentifier) {
                    return new DiptraceToken(
                        DiptraceTokenType.IDENTIFIER,
                        tokenValue,
                        firstTokenOnLine);
                }
                
                // Bug fix. Diptrace PCB ascii files not always put strings
                // in " and ".
                return new DiptraceToken(
                    DiptraceTokenType.NON_QUOTED_STRING,
                    tokenValue,
                    firstTokenOnLine);
                
//                throw new RuntimeException(
//                            String.format(
//                                "Unknown token type. LineNo: %d, %s",
//                                fLineNo,
//                                tokenValue));
            }
        }
    }
}
