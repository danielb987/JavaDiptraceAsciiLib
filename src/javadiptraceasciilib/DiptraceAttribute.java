
package javadiptraceasciilib;

/**
 * A Diptrace attribute.
 */
public interface DiptraceAttribute {
    
    /**
     * Duplicate this attribute.
     * @return a copy of this attribute
     */
    DiptraceAttribute duplicate();
    
    /**
     * Get the attribute as a String.
     * @return the attribute as a String
     */
    String getString();
    
}
