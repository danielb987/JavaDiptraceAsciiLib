package javadiptraceasciilib.diptrace.tokenizer;

/**
 * This class represents a token in the DipTrace ascii file.
 */
public final class DiptraceToken {
    
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
    private final int fIntValue;
    
    /**
     * The value of the token as a float, if the token is of type FLOAT.
     */
    private final double fFloatValue;
    
    /**
     * Initialize a DiptraceToken object with a type.
     * @param type the type of the token
     */
    public DiptraceToken(final DiptraceTokenType type) {
        this.fType = type;
        this.fValue = null;
        this.fIntValue = 0;
        this.fFloatValue = 0;
    }
    
    /**
     * Initialize a DiptraceToken object with a type and a value.
     * @param type the type of the token
     * @param value the value of the token
     */
    public DiptraceToken(final DiptraceTokenType type, final String value) {
        this.fType = type;
        this.fValue = value;
        this.fIntValue = 0;
        this.fFloatValue = 0;
    }
    
    /**
     * Initialize a DiptraceToken object with a type.
     * @param type the type of the token
     * @param value the value of the token
     * @param intValue the integer value of the token
     */
    public DiptraceToken(
        final DiptraceTokenType type,
        final String value,
        final int intValue) {
        
        this.fType = type;
        this.fValue = value;
        this.fIntValue = intValue;
        this.fFloatValue = 0;
    }
    
    /**
     * Initialize a DiptraceToken object with a type.
     * @param type the type of the token
     * @param value the value of the token
     * @param floatValue the float value of the token
     */
    public DiptraceToken(
        final DiptraceTokenType type,
        final String value,
        final double floatValue) {
        
        this.fType = type;
        this.fValue = value;
        this.fIntValue = 0;
        this.fFloatValue = floatValue;
    }
    
    
    /**
     * Returns the type of the token.
     * @return the type
     */
    public DiptraceTokenType getType() {
        return fType;
    }
    
    /**
     * Returns the value of the token.
     * @return the value
     */
    public String getValue() {
        if (fValue != null) {
            return fValue;
        } else {
            return "";
        }
    }
    
    /**
     * Returns the integer value of the token.
     * @return the integer value
     */
    public int getIntValue() {
        return fIntValue;
    }
    
    /**
     * Returns the float value of the token.
     * @return the float value
     */
    public double getFloatValue() {
        return fFloatValue;
    }
    
}
