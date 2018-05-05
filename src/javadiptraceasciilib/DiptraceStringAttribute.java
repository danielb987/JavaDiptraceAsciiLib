package javadiptraceasciilib;

/**
 * A Diptrace string attribute.
 */
public class DiptraceStringAttribute implements DiptraceAttribute {
    
    /**
     * The value as a String.
     */
    private String fStringValue;
    
    /**
     * Whenether we should use quotes.
     */
    private final UseQuotes fQuotes;
    
    /**
     * Initialize a DiptraceStringAttribute object.
     * @param value the value
     * @param quotes whenether to use quotes
     */
    DiptraceStringAttribute(String value, UseQuotes quotes) {
        this.fStringValue = value;
        this.fQuotes = quotes;
    }
    
    /**
     * Duplicate this attribute.
     * @return a copy of this attribute
     */
    @Override
    public DiptraceAttribute duplicate() {
        return new DiptraceStringAttribute(fStringValue, fQuotes);
    }
    
    @Override
    public String getString() {
        return fStringValue;
    }
    
    public String getQuotedString() {
        switch (fQuotes) {
            case DOUBLE_QUOTES:
                return "\""+fStringValue+"\"";
                
            case NO_QUOTES:
                return fStringValue;
                
            default:
                throw new RuntimeException(
                    String.format(
                        "fQuotes has invalid value: %s",
                        fQuotes.name()));
        }
    }
    
    public void setString(String value) {
        fStringValue = value;
    }
    
    
    enum UseQuotes {
        DOUBLE_QUOTES,
        NO_QUOTES,
    }
}
