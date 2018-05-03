package javadiptraceasciilib2;

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
    
    public void duplicateComponent(String originalRefDes, String newRefDes) {
        
    }
    
    public void moveComponentAbsoluteOnSchematics(
        String refDes, double x, double y) {
        
    }
    
    public void moveComponentRelativeOnSchematics(
        String refDes, double x, double y) {
        
    }
    
    public void moveComponentAbsoluteOnPCB(String refDes, double x, double y) {
        
    }
    
    public void moveComponentRelativeOnPCB(String refDes, double x, double y) {
        
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
