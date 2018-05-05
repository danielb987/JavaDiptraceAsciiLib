package javadiptraceasciilib;

import java.util.Locale;

/**
 * A Diptrace double attribute.
 */
public final class DiptraceDoubleAttribute implements DiptraceAttribute {
    
    /**
     * The value as a string.
     */
    private String fStringValue;
    
    /**
     * The value as a double.
     */
    private double fDoubleValue;
    
    /**
     * Initialize a DiptraceDoubleAttribute object.
     * @param stringValue the value as a string
     * @param doubleValue the value as a double
     */
    DiptraceDoubleAttribute(
        final String stringValue,
        final Double doubleValue) {
        
        this.fStringValue = stringValue;
        this.fDoubleValue = doubleValue;
    }
    
    /**
     * Duplicate this attribute.
     * @return a copy of this attribute
     */
    @Override
    public DiptraceAttribute duplicate() {
        return new DiptraceDoubleAttribute(
            fStringValue, fDoubleValue);
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
     * Get the attribute as a string.
     * @return the attribute
     */
    @Override
    public String getFormattedString() {
        return fStringValue;
    }
    
    /**
     * Get the attribute as a double.
     * @return the attribute
     */
    public double getDouble() {
        return fDoubleValue;
    }
    
    /**
     * Set the attribute.
     * @param value the value
     */
    public void setDouble(final double value) {
        fDoubleValue = value;
        fStringValue = String.format(Locale.ROOT, "%1.3f", value);
    }
    
}
