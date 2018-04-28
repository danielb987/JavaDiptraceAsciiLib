package javadiptraceasciilib.diptrace.tokenizer;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Generates tokens of a Diptrace ascii file
 */
public final class DiptraceTokenizer {
    
    private final BufferedReader fReader;
    private StringBuilder fCurrentLine;
    private int fLineNo = 1;
    private boolean fLastTokenWasLeftParentheses = false;
    private DiptraceToken fNextToken;
    
    
    public DiptraceTokenizer(final BufferedReader reader) throws IOException {
        
        this.fReader = reader;
        this.fCurrentLine = new StringBuilder(reader.readLine().trim());
    }
    
    
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
    
    
    public DiptraceToken nextToken() throws IOException {
        if (fNextToken != null) {
            DiptraceToken token = fNextToken;
            fNextToken = null;
            return token;
        }
        return fetchNextToken();
    }
    
    
    public DiptraceToken previewNextToken() throws IOException {
        if (fNextToken == null) {
            fNextToken = fetchNextToken();
        }
        return fNextToken;
    }
    
    
    private DiptraceToken fetchNextToken() throws IOException {
        
        while ((fCurrentLine != null) && (fCurrentLine.length() == 0)) {
            fCurrentLine.setLength(0);
            String line = fReader.readLine();
            
            // End of file?
            if (line == null) {
                return null;
            }
            
            fCurrentLine.append(line.trim());
            fLineNo++;
        }
        
        if (fCurrentLine == null) {
            return null;
        } else if (fCurrentLine.charAt(0) == '(') {
            fCurrentLine.delete(0, 1);
            fLastTokenWasLeftParentheses = true;
            return new DiptraceToken(DiptraceTokenType.LEFT_PARENTHESES);
        } else if (fCurrentLine.charAt(0) == ')') {
            fCurrentLine.delete(0, 1);
            return new DiptraceToken(DiptraceTokenType.RIGHT_PARENTHESES);
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
                
                return new DiptraceToken(DiptraceTokenType.STRING, tokenValue);
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
                    tokenValue);
            } else {
                try {
                    int value = Integer.parseInt(tokenValue);
                    // If we are here, the fValue is a valid integer
                    return new DiptraceToken(
                        DiptraceTokenType.INTEGER,
                        tokenValue, value);
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
                                        value);
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
                                    value);
                } catch (NumberFormatException e) {
                    // If we are here, fValue is not a double so we just
                    // continue to the next statement.
                }
                
//                if ((tokenValue.charAt(0) == '"')
//                    && (tokenValue.charAt(tokenValue.length()-1) == '"'))
//                    return new DiptraceToken(
//                                DiptraceTokenType.STRING,
//                                tokenValue);
                
                throw new RuntimeException(
                            String.format("Unknown token type. LineNo: %d, %s",
                                fLineNo,
                                tokenValue));
            }
            
/*
            if (fCurrentLine.charAt(0) == ')') {
                fCurrentLine.delete(0, 1);
                return new DiptraceToken(DiptraceTokenType.RIGHT_PARENTHESES);
            }
            else
                throw new RuntimeException(String.format("Unknown token: %s",
                    fCurrentLine));
*/
        }
    }
}
