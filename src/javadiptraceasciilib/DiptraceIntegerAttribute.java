package javadiptraceasciilib;

/**
 * A Diptrace integer attribute.
 */
public class DiptraceIntegerAttribute implements DiptraceAttribute {
    
    String fStringValue;
    int fIntegerValue;
    
    DiptraceIntegerAttribute(String stringValue, int integerValue) {
        this.fStringValue = stringValue;
        this.fIntegerValue = integerValue;
    }
    
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
