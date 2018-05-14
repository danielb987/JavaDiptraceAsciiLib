package javadiptraceasciilib;

/**
 * Item not found exception.
 */
public class DiptraceNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>NotFound</code> without detail message.
     */
    public DiptraceNotFoundException() {
    }

    /**
     * Constructs an instance of <code>NotFound</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public DiptraceNotFoundException(final String msg) {
        super(msg);
    }
    
}
