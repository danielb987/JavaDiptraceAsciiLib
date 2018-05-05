
package javadiptraceasciilib;

/**
 *
 */
public class DiptraceDoublePercentAttribute implements DiptraceAttribute {
    
    /**
     * The value as a string.
     */
    private String fStringValue;
    
    /**
     * The value as a double with percent sign.
     */
    private double fDoubleValue;
    
    DiptraceDoublePercentAttribute(
        String stringValue,
        Double doubleValue) {
        
        this.fStringValue = stringValue;
        this.fDoubleValue = doubleValue;
    }
    
    /**
     * Duplicate this attribute.
     * @return a copy of this attribute
     */
    @Override
    public DiptraceAttribute duplicate() {
        return new DiptraceDoublePercentAttribute(
            fStringValue, fDoubleValue);
    }
    
    @Override
    public String getString() {
        return fStringValue;
    }
    
    public double getDouble() {
        return fDoubleValue;
    }
    
    public void setDouble(double value) {
        fDoubleValue = value;
        fStringValue = String.format("%1.3f%%", value);
    }

}
