package javadiptraceasciilib;

/**
 * Extended operations on a DiptraceProject which includes both schematics
 * and pcb.
 */
public class DiptraceOperations {
    
    private final DiptracePrimitiveOperations diptracePrimitiveOperations;
    
    /**
     * Initialize a DiptracePrimitiveOperations object.
     * @param project the project
     */
    public DiptraceOperations(final DiptraceProject project) {
        diptracePrimitiveOperations = new DiptracePrimitiveOperations(project);
    }
    
    /**
     * Duplicate a component.
     * @param originalRefDes the RefDes of the component to copy
     * @param newRefDes the RefDes that the new component is going to get
     */
    public void duplicateComponent(
        final String originalRefDes,
        final String newRefDes) {
        
    }
    
    /**
     * Move a component to an absolute position on the schematics.
     * @param refDes The RefDes of the component to move
     * @param x the x position
     * @param y the y position
     */
    public void moveComponentAbsoluteOnSchematics(
        final String refDes, final double x, final double y) {
        
    }
    
    /**
     * Move a component to an relative position on the schematics.
     * @param refDes The RefDes of the component to move
     * @param x the x distance
     * @param y the y distance
     */
    public void moveComponentRelativeOnSchematics(
        final String refDes, final double x, final double y) {
        
    }
    
    /**
     * Move a component to an absolute position on the pcb.
     * @param refDes The RefDes of the component to move
     * @param x the x position
     * @param y the y position
     */
    public void moveComponentAbsoluteOnPCB(
        final String refDes, final double x, final double y) {
        
    }
    
    /**
     * Move a component to an absolute position on the pcb.
     * @param refDes The RefDes of the component to move
     * @param x the x distance
     * @param y the y distance
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
