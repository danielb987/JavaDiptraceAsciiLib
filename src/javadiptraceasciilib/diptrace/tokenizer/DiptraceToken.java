package javadiptraceasciilib.diptrace.tokenizer;

/**
 * A token
 */
public final class DiptraceToken {
    
    private final DiptraceTokenType fType;
    private final String fValue;
    private final int fIntValue;
    private final double fFloatValue;
    
    public DiptraceToken(final DiptraceTokenType type) {
        this.fType = type;
        this.fValue = null;
        this.fIntValue = 0;
        this.fFloatValue = 0;
    }
    
    public DiptraceToken(final DiptraceTokenType type, final String value) {
        this.fType = type;
        this.fValue = value;
        this.fIntValue = 0;
        this.fFloatValue = 0;
    }
    
    public DiptraceToken(
        final DiptraceTokenType type,
        final String value,
        final int intValue) {
        
        this.fType = type;
        this.fValue = value;
        this.fIntValue = intValue;
        this.fFloatValue = 0;
    }
    
    public DiptraceToken(
        final DiptraceTokenType type,
        final String value,
        final double floatValue) {
        
        this.fType = type;
        this.fValue = value;
        this.fIntValue = 0;
        this.fFloatValue = floatValue;
    }
    
    
    public DiptraceTokenType getType() {
        return fType;
    }
    
    public String getValue() {
        if (fValue != null)
            return fValue;
        else
            return "";
    }
    
    public int getIntValue() {
        return fIntValue;
    }
    
    public double getFloatValue() {
        return fFloatValue;
    }
    
}
