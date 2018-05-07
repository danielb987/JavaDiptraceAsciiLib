package javadiptraceasciilib;

/**
 * This exception is thrown when the desired RefDes already exists and cannot
 * be duplicated.
 */
public class DiptraceNetNameAlreadyExistsException extends Exception {
    
    /**
     * Creates a new instance of <code>RefDesAlreadyExistsException</code>
     * without detail message.
     */
    public DiptraceNetNameAlreadyExistsException() {
    }
    
    /**
     * Constructs an instance of <code>RefDesAlreadyExistsException</code> with
     * the specified detail message.
     * @param msg the detail message.
     */
    public DiptraceNetNameAlreadyExistsException(final String msg) {
        super(msg);
    }
}
