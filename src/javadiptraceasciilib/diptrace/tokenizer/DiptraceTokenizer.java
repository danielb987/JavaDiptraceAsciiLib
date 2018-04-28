package javadiptraceasciilib.diptrace.tokenizer;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 */
public class DiptraceTokenizer {
    
    private final BufferedReader reader;
//    String currentLine;
    StringBuilder currentLine;
    int lineNo = 1;
    boolean lastTokenWasLeftParentheses = false;
    DiptraceToken nextToken;
    
    public DiptraceTokenizer(final BufferedReader reader) throws IOException {
        
        this.reader = reader;
        this.currentLine = new StringBuilder(reader.readLine().trim());
    }
    
    
    public void eatToken(final DiptraceTokenType type) throws IOException {
        if (nextToken == null)
            nextToken = fetchNextToken();
        
        if (nextToken == null)
            throw new RuntimeException(
                String.format(
                    "End of file has been reached premature. Token to eat: %s",
                    type.name()));
        
        if (nextToken.type != type)
            throw new RuntimeException(
                String.format(
                    "Token is not a %s token: Type: %s, %s",
                    type.name(),
                    nextToken.type.name(),
                    nextToken.value));
        
        nextToken = null;
    }
    
    
    public DiptraceToken nextToken() throws IOException {
        if (nextToken != null) {
            DiptraceToken token = nextToken;
            nextToken = null;
            return token;
        }
        return fetchNextToken();
    }
    
    
    public DiptraceToken previewNextToken() throws IOException {
        if (nextToken == null)
            nextToken = fetchNextToken();
        return nextToken;
    }
    
    
    private DiptraceToken fetchNextToken() throws IOException {
        
        while ((currentLine != null) && (currentLine.length() == 0)) {
            currentLine.setLength(0);
            String line = reader.readLine();
            
            // End of file?
            if (line == null)
                return null;
            
            currentLine.append(line.trim());
            lineNo++;
        }
        
        if (currentLine == null) {
            return null;
        }
        else if (currentLine.charAt(0) == '(') {
            currentLine.delete(0, 1);
            lastTokenWasLeftParentheses = true;
            return new DiptraceToken(DiptraceTokenType.LEFT_PARENTHESES);
        }
        else if (currentLine.charAt(0) == ')') {
            currentLine.delete(0, 1);
            return new DiptraceToken(DiptraceTokenType.RIGHT_PARENTHESES);
        }
        else {
            String tokenValue;
            
            if (currentLine.charAt(0) == '"') {
                int pos = 1;
                while ((pos < currentLine.length())
                        && (currentLine.charAt(pos) != '"'))
                    pos++;
                
                if (pos == currentLine.length())
                    throw new RuntimeException(
                        String.format(
                            "Invalid string token. No \" at end of string."
                                + "LineNo: %d, %s",
                            lineNo,
                            currentLine));
                
                tokenValue = currentLine.substring(1, pos);
                currentLine.delete(0, pos+1);
                
                while ((currentLine.length() > 0)
                        && (currentLine.charAt(0) == ' '))
                    currentLine.delete(0, 1);
                
                return new DiptraceToken(DiptraceTokenType.STRING, tokenValue);
            }
            
            
            int spacePos;
            if ((spacePos = currentLine.indexOf(" ")) >= 0) {
                tokenValue = currentLine.substring(0, spacePos);
                currentLine.delete(0, spacePos+1);
            }
            else {
                int length = currentLine.length();
                
                if (currentLine.charAt(length-1) == ')') {
                    tokenValue = currentLine.substring(0, length-1);
                    currentLine.delete(0, length-1);
                }
                else {
                    tokenValue = currentLine.substring(0, length);
                    currentLine.setLength(0);    // Clear string
                }
            }
            
            if (lastTokenWasLeftParentheses) {
                lastTokenWasLeftParentheses = false;
                return new DiptraceToken(
                    DiptraceTokenType.IDENTIFIER,
                    tokenValue);
            }
            else {
                try {
                    int value = Integer.parseInt(tokenValue);
                    // If we are here, the value is a valid integer
                    return new DiptraceToken(
                        DiptraceTokenType.INTEGER,
                        tokenValue, value);
                }
                catch (NumberFormatException e) {
                }
                
                if ((tokenValue.length() > 0)
                    && (tokenValue.charAt(tokenValue.length()-1) == '%')) {
                    
                    String percentValue
                        = tokenValue.substring(0, tokenValue.length()-1);
                    
                    try {
                        double value = Double.parseDouble(percentValue);
                        // If we are here, the value is a valid double
                        return new DiptraceToken(
                                        DiptraceTokenType.PERCENT,
                                        tokenValue,
                                        value);
                    }
                    catch (NumberFormatException e) {
                    }
                }
                
                try {
                    double value = Double.parseDouble(tokenValue);
                    // If we are here, the value is a valid double
                    return new DiptraceToken(
                                    DiptraceTokenType.FLOAT,
                                    tokenValue,
                                    value);
                }
                catch (NumberFormatException e) {
                }
                
//                if ((tokenValue.charAt(0) == '"')
//                    && (tokenValue.charAt(tokenValue.length()-1) == '"'))
//                    return new DiptraceToken(
//                                DiptraceTokenType.STRING,
//                                tokenValue);
                
                throw new RuntimeException(
                            String.format("Unknown token type. LineNo: %d, %s",
                                lineNo,
                                tokenValue));
            }
            
/*            
            if (currentLine.charAt(0) == ')') {
                currentLine.delete(0, 1);
                return new DiptraceToken(DiptraceTokenType.RIGHT_PARENTHESES);
            }
            else
                throw new RuntimeException(String.format("Unknown token: %s",
                    currentLine));
*/            
        }
    }
}
