package javadiptraceasciilib;

/**
 * A Diptrace string attribute.
 */
public class DiptraceStringAttribute implements DiptraceAttribute {
    
    String fStringValue;
    final boolean fUseDoubleQuotes;
    
    DiptraceStringAttribute(String value, boolean useDoubleQuotes) {
        this.fStringValue = value;
        this.fUseDoubleQuotes = useDoubleQuotes;
    }
    
    @Override
    public DiptraceAttribute duplicate() {
        return new DiptraceStringAttribute(fStringValue, fUseDoubleQuotes);
    }
    
    @Override
    public String getString() {
        if (fUseDoubleQuotes) {
            return "\""+fStringValue+"\"";
        } else {
            return fStringValue;
        }
    }
    
    public void setString(String value) {
        fStringValue = value;
    }
    
}
