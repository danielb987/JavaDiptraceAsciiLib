package javadiptraceasciilib.diptrace.tokenizer;

import javadiptraceasciilib.diptrace.exceptions.IllegalTokenValue;

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
    private String fValue;
    
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
    public DiptraceToken(
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
    public DiptraceToken(
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
    public DiptraceToken(
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
    public DiptraceToken(
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
    public DiptraceToken(final DiptraceToken token) {
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
    public DiptraceTokenType getType() {
        return fType;
    }
    
    /**
     * Get the value of the token.
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
     * Set the value of the token.
     * @param value the new value
     * @throws IllegalTokenValue if the token is not a string or
     * non quoted string token
     */
    public void setValue(final String value) throws IllegalTokenValue {
        if ((fType == DiptraceTokenType.STRING)
            || (fType == DiptraceTokenType.NON_QUOTED_STRING)) {
            
            fValue = value;
        } else {
            throw new IllegalTokenValue(
                String.format(
                    "A token of type %s cannot be given a string value",
                    fType.name()));
        }
    }
    
    /**
     * Get the integer value of the token.
     * @return the integer value
     */
    public int getIntValue() {
        return fIntValue;
    }
    
    /**
     * Set the value of the token.
     * @param value the new value
     * @throws IllegalTokenValue if the token is not an integer token
     */
    public void setIntValue(final int value) throws IllegalTokenValue {
        if (fType == DiptraceTokenType.INTEGER) {
            
            fIntValue = value;
            fValue = String.format("%d", value);
        } else {
            throw new IllegalTokenValue(
                String.format(
                    "A token of type %s cannot be given a string value",
                    fType.name()));
        }
    }
    
    /**
     * Get the float value of the token.
     * @return the float value
     */
    public double getDoubleValue() {
        return fDoubleValue;
    }
    
    /**
     * Set the value of the token.
     * @param value the new value
     * @throws IllegalTokenValue if the token is not a double token
     */
    public void setDoubleValue(final double  value) throws IllegalTokenValue {
        if (fType == DiptraceTokenType.DOUBLE) {
            
            fDoubleValue = value;
            fValue = String.format("%1.3f", value);
        } else {
            throw new IllegalTokenValue(
                String.format(
                    "A token of type %s cannot be given a double value",
                    fType.name()));
        }
    }
    
    /**
     * Get whenether this token was preceded by a new line or not.
     * @return true if this token was preceded by a new line
     */
    public boolean getPrecededWithNewline() {
        return fPrecededWithNewline;
    }
    
}
