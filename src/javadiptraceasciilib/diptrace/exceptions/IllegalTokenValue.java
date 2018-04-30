package javadiptraceasciilib.diptrace.exceptions;

/**
 * Thrown to indicate that an attempt has been done to give a token an invalid
 * value. For example, an integer token cannot be given a string value.
 */
public class IllegalTokenValue extends Exception {

    /**
     * Creates a new instance of <code>IllegalTokenValue</code> without detail
     * message.
     */
    public IllegalTokenValue() {
    }


    /**
     * Constructs an instance of <code>IllegalTokenValue</code> with the
     * specified detail message.
     * @param msg the detail message.
     */
    public IllegalTokenValue(final String msg) {
        super(msg);
    }
}
