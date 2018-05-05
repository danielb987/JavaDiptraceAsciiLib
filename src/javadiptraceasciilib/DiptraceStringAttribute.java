package javadiptraceasciilib;

/**
 * A Diptrace string attribute.
 */
public final class DiptraceStringAttribute implements DiptraceAttribute {
    
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
    DiptraceStringAttribute(final String value, final UseQuotes quotes) {
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
    
    /**
     * Get the attribute as a string.
     * @return the attribute
     */
    @Override
    public String getString() {
        return fStringValue;
    }
    
    /**
     * Get the attribute as a formatted string, possibly with quotes.
     * @return the attribute
     */
    @Override
    public String getFormattedString() {
        switch (fQuotes) {
            case DOUBLE_QUOTES:
                return "\"" + fStringValue + "\"";
                
            case NO_QUOTES:
                return fStringValue;
                
            default:
                throw new RuntimeException(
                    String.format(
                        "fQuotes has invalid value: %s",
                        fQuotes.name()));
        }
    }
    
    /**
     * Set the attribute.
     * @param value the value
     */
    public void setString(final String value) {
        fStringValue = value;
    }
    
    
    /**
     * Whenether to use quotes or not.
     */
    enum UseQuotes {
        
        /**
         * Use double quotes.
         */
        DOUBLE_QUOTES,
        
        /**
         * Don't use quotes.
         */
        NO_QUOTES,
    }
}
