package javadiptraceasciilib;

/**
 * A Diptrace integer attribute.
 */
public class DiptraceIntegerAttribute implements DiptraceAttribute {
    
    /**
     * The value as a string.
     */
    private String fStringValue;
    
    /**
     * The value as an integer.
     */
    private int fIntegerValue;
    
    DiptraceIntegerAttribute(String stringValue, int integerValue) {
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
    
    @Override
    public String getString() {
        return fStringValue;
    }
    
    public int getInt() {
        return fIntegerValue;
    }
    
    public void setInt(int value) {
        fIntegerValue = value;
        fStringValue = Integer.toString(value);
    }
    
}
