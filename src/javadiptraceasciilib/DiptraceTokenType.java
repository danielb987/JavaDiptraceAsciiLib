package javadiptraceasciilib;

/**
 * Constant definitions of token types.
 */
enum DiptraceTokenType {
    
    //CHECKSTYLE.OFF: JavadocVariable - Self explaining enums
    LEFT_PARENTHESES,
    RIGHT_PARENTHESES,
    IDENTIFIER,
    STRING,
    INTEGER,
    DOUBLE,
    PERCENT,
    /**
     * A string without double quotes. This is a bug in Diptrace and is found
     * in the Diptrace PCB ascii file.
     */
    NON_QUOTED_STRING,
    
    //CHECKSTYLE.ON: JavadocVariable - Self explaining enums
    
}
