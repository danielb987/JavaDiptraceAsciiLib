package javadiptraceasciilib;

import java.util.Locale;

/**
 * A Diptrace double attribute.
 */
final class DiptraceDoubleAttribute implements DiptraceAttribute {
    
    /**
     * The value as a string.
     */
    private String fStringValue;
    
    /**
     * Is the value an integer?
     */
    private boolean isInteger;
    
    /**
     * The value as a integer.
     */
    private int fIntValue;
    
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
        final int intValue) {
        
        this.isInteger = true;
        this.fStringValue = stringValue;
        this.fIntValue = intValue;
        this.fDoubleValue = intValue;
    }
    
    /**
     * Initialize a DiptraceDoubleAttribute object.
     * @param stringValue the value as a string
     * @param doubleValue the value as a double
     */
    DiptraceDoubleAttribute(
        final String stringValue,
        final double doubleValue) {
        
        this.isInteger = false;
        this.fStringValue = stringValue;
        this.fDoubleValue = doubleValue;
    }
    
    /**
     * Duplicate this attribute.
     * @return a copy of this attribute
     */
    @Override
    public DiptraceAttribute duplicate() {
        if (isInteger) {
            return new DiptraceDoubleAttribute(
                fStringValue, fIntValue);
        } else {
            return new DiptraceDoubleAttribute(
                fStringValue, fDoubleValue);
        }
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
     * Get the attribute as an integer.
     * @return the attribute
     */
    public int getInt() {
        return fIntValue;
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
     * @throws NumberFormatException if the attribute is a double
     */
    public void setInt(final int value) {
        if (!isInteger) {
            throw new NumberFormatException(
                "This value is a double");
        }
        fIntValue = value;
        fDoubleValue = value;
        fStringValue = Integer.toString(value);
    }
    
    /**
     * Set the attribute.
     * @param value the value
     * @throws NumberFormatException if the attribute is an integer. Use the
     * method forceSetDouble to force the value to a double if you are sure
     * that is possible.
     */
    public void setDouble(final double value) {
        
        if (isInteger) {
            throw new NumberFormatException(
                "This value is possibly an integer");
        }
        fIntValue = (int) Math.round(value);
        fDoubleValue = value;
        fStringValue = String.format(Locale.ROOT, "%1.3f", value);
    }
    
    /**
     * Force the attributeto be set to a double value. Use this method with
     * caution. Some values in the Diptrace Ascii file may be either integer
     * or double. Since it's not clear by the file format which it is, this
     * library prefers to be safe than sorry.
     * @param value the value
     */
    public void forceSetDouble(final double value) {
        isInteger = false;
        fIntValue = (int) Math.round(value);
        fDoubleValue = value;
        fStringValue = String.format(Locale.ROOT, "%1.3f", value);
    }
    
}
