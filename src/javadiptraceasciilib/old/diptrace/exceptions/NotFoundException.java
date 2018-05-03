package javadiptraceasciilib.old.diptrace.exceptions;

/**
 * Item not found exception.
 */
public class NotFoundException extends Exception {

    /**
     * Creates a new instance of <code>NotFound</code> without detail message.
     */
    public NotFoundException() {
    }

    /**
     * Constructs an instance of <code>NotFound</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public NotFoundException(final String msg) {
        super(msg);
    }
    
}
