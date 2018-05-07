package javadiptraceasciilib;

/**
 * A Diptrace integer attribute.
 */
final class DiptraceIntegerAttribute implements DiptraceAttribute {
    
    /**
     * The value as a string.
     */
    private String fStringValue;
    
    /**
     * The value as an integer.
     */
    private int fIntegerValue;
    
    /**
     * Initialize a DiptraceIntegerAttribute object.
     * @param stringValue the value as a string
     * @param integerValue the value as an integer
     */
    DiptraceIntegerAttribute(final String stringValue, final int integerValue) {
        this.fStringValue = stringValue;
        this.fIntegerValue = integerValue;
    }
    
    /**
     * Duplicate this attribute.
     * @return a copy of this attribute
     */
    @Override
    public DiptraceAttribute duplicate() {
        return new DiptraceIntegerAttribute(fStringValue, fIntegerValue);
    }
    
    /**
     * Get the attribute as a string.
     * @return the attribute
     */
    @Override
    public String getString() {
        return fStringValue;
    }
    
    /**
     * Get the attribute as a formatted string.
     * @return the attribute
     */
    @Override
    public String getFormattedString() {
        return fStringValue;
    }
    
    /**
     * Get the attribute as an integer.
     * @return the attribute
     */
    public int getInt() {
        return fIntegerValue;
    }
    
    /**
     * Set the attribute.
     * @param value the value
     */
    public void setInt(final int value) {
        fIntegerValue = value;
        fStringValue = Integer.toString(value);
    }
    
}
