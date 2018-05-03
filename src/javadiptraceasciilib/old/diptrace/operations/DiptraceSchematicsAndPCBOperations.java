
package javadiptraceasciilib.old.diptrace.operations;

import javadiptraceasciilib.old.diptrace.DiptraceProject;

/**
 * Extended operations on a DiptraceProject which includes both schematics
 * and pcb.
 */
public class DiptraceSchematicsAndPCBOperations extends DiptraceOperations {
    
    /**
     * Initialize a DiptraceOperations object.
     * @param project the project
     */
    public DiptraceSchematicsAndPCBOperations(final DiptraceProject project) {
        super(project);
    }
    
    /**
     * A
     * @param originalRefDes a
     * @param newRefDes a
     */
    public void duplicateComponent(
        final String originalRefDes, final String newRefDes) {
        
    }
    
    /**
     * A
     * @param refDes a
     * @param x a
     * @param y a
     */
    public void moveComponentAbsoluteOnSchematics(
        final String refDes, final double x, final double y) {
        
    }
    
    /**
     * A
     * @param refDes a
     * @param x a
     * @param y a
     */
    public void moveComponentRelativeOnSchematics(
        final String refDes, final double x, final double y) {
        
    }
    
    /**
     * A
     * @param refDes a
     * @param x a
     * @param y a
     */
    public void moveComponentAbsoluteOnPCB(
        final String refDes, final double x, final double y) {
        
    }
    
    /**
     * A
     * @param refDes a
     * @param x a
     * @param y a
     */
    public void moveComponentRelativeOnPCB(
        final String refDes, final double x, final double y) {
        
    }
    
/*
    public DiptraceSchematicsAndPCBItem getComponentByRefDes(String refDes) throws NotFoundException {
        
        DiptraceItem schematicsParts
            = getSchematicsComponentPart("D1");
        DiptraceItem schematicsPartR1
            = getSchematicsComponentPart("R1");
        
        return null;
    }
*/
}
