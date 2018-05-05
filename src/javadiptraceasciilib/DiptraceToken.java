package javadiptraceasciilib;

/**
 * This class represents a token in the DipTrace ascii file.
 */
final class DiptraceToken {
    
    /**
     * The type of the token.
     */
    private final DiptraceTokenType fType;
    
    /**
     * The value of the token as a String.
     */
    private final String fValue;
    
    /**
     * The value of the token as an integer, if the token is of type INTEGER.
     */
    private int fIntValue;
    
    /**
     * The value of the token as a float, if the token is of type DOUBLE.
     */
    private double fDoubleValue;
    
    /**
     * True if this token was preceded by a new line.
     */
    private final boolean fPrecededWithNewline;
    
    /**
     * Initialize a DiptraceToken object with a type.
     * @param type the type of the token
     * @param precededWithNewline true if this token was preceded by a new line
     */
    DiptraceToken(
        final DiptraceTokenType type,
        final boolean precededWithNewline) {
        
        this.fType = type;
        this.fValue = null;
        this.fIntValue = 0;
        this.fDoubleValue = 0;
        this.fPrecededWithNewline = precededWithNewline;
    }
    
    /**
     * Initialize a DiptraceToken object with a type and a value.
     * @param type the type of the token
     * @param value the value of the token
     * @param precededWithNewline true if this token was preceded by a new line
     */
    DiptraceToken(
        final DiptraceTokenType type,
        final String value,
        final boolean precededWithNewline) {
        
        this.fType = type;
        this.fValue = value;
        this.fIntValue = 0;
        this.fDoubleValue = 0;
        this.fPrecededWithNewline = precededWithNewline;
    }
    
    /**
     * Initialize a DiptraceToken object with a type.
     * @param type the type of the token
     * @param value the value of the token
     * @param intValue the integer value of the token
     * @param precededWithNewline true if this token was preceded by a new line
     */
    DiptraceToken(
        final DiptraceTokenType type,
        final String value,
        final int intValue,
        final boolean precededWithNewline) {
        
        this.fType = type;
        this.fValue = value;
        this.fIntValue = intValue;
        this.fDoubleValue = 0;
        this.fPrecededWithNewline = precededWithNewline;
    }
    
    /**
     * Initialize a DiptraceToken object with a type.
     * @param type the type of the token
     * @param value the value of the token
     * @param floatValue the float value of the token
     * @param precededWithNewline true if this token was preceded by a new line
     */
    DiptraceToken(
        final DiptraceTokenType type,
        final String value,
        final double floatValue,
        final boolean precededWithNewline) {
        
        this.fType = type;
        this.fValue = value;
        this.fIntValue = 0;
        this.fDoubleValue = floatValue;
        this.fPrecededWithNewline = precededWithNewline;
    }
    
    
    /**
     * Initialize a DiptraceToken object with another DiptraceToken. The new
     * token will be a copy of the old token.
     * @param token the token to copy
     */
    DiptraceToken(final DiptraceToken token) {
        this.fType = token.fType;
        this.fValue = token.fValue;
        this.fIntValue = token.fIntValue;
        this.fDoubleValue = token.fDoubleValue;
        this.fPrecededWithNewline = token.fPrecededWithNewline;
    }
    
    
    /**
     * Get the type of the token.
     * @return the type
     */
    DiptraceTokenType getType() {
        return fType;
    }
    
    /**
     * Get the value of the token.
     * @return the value
     */
    String getValue() {
        if (fValue != null) {
            return fValue;
        } else {
            return "";
        }
    }
    
    /**
     * Get the integer value of the token.
     * @return the integer value
     */
    int getIntValue() {
        return fIntValue;
    }
    
    /**
     * Get the float value of the token.
     * @return the float value
     */
    double getDoubleValue() {
        return fDoubleValue;
    }
    
    /**
     * Get whenether this token was preceded by a new line or not.
     * @return true if this token was preceded by a new line
     */
    boolean getPrecededWithNewline() {
        return fPrecededWithNewline;
    }
    
}
