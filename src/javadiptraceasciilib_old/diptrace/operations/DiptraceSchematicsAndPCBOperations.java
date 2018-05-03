
package javadiptraceasciilib_old.diptrace.operations;

import javadiptraceasciilib_old.diptrace.DiptraceProject;
import javadiptraceasciilib_old.diptrace.exceptions.NotFoundException;
import javadiptraceasciilib_old.diptrace.tree.DiptraceItem;

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
