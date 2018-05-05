package javadiptraceasciilib;

/**
 * A Diptrace double attribute.
 */
public class DiptraceDoubleAttribute implements DiptraceAttribute {
    
    /**
     * The value as a string.
     */
    private String fStringValue;
    
    /**
     * The value as a double.
     */
    private double fDoubleValue;
    
    DiptraceDoubleAttribute(
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
        return new DiptraceDoubleAttribute(
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
        fStringValue = String.format("%1.3f", value);
    }
    
}
