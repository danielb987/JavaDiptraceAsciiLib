package javadiptraceasciilib;

/**
 * A Diptrace double attribute.
 */
public class DiptraceDoubleAttribute implements DiptraceAttribute {
    
    String fStringValue;
    double fDoubleValue;
    final boolean fPercentValue;
    
    DiptraceDoubleAttribute(
        String stringValue,
        Double doubleValue,
        boolean percentValue) {
        
        this.fStringValue = stringValue;
        this.fDoubleValue = doubleValue;
        this.fPercentValue = percentValue;
    }
    
    @Override
    public DiptraceAttribute duplicate() {
        return new DiptraceDoubleAttribute(
            fStringValue, fDoubleValue, fPercentValue);
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
        
        if (fPercentValue) {
            fStringValue = String.format("%1.3f%%", value);
        } else {
            fStringValue = String.format("%1.3f", value);
        }
    }
    
}
