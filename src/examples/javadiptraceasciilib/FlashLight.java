package examples.javadiptraceasciilib;

import java.io.IOException;
import javadiptraceasciilib.diptrace.DiptraceProject;
import javadiptraceasciilib.diptrace.exceptions.NotFoundException;
import javadiptraceasciilib.diptrace.operations.DiptraceOperations;
import javadiptraceasciilib.diptrace.tree.DiptraceItem;

/**
 * Create a flash light PCB.
 */
public final class FlashLight {
    
    /**
     * Main class.
     * @param args command line arguments.
     */
    public static void main(final String[] args) {
        
        try {
            // Create a diptrace project
            DiptraceProject diptraceProject = new DiptraceProject();
            
            // Read the diptrace ascii files
            diptraceProject.readSchematicsAndPCB(
                "flashlight_schematics.asc",
                "flashlight_pcb.asc");
            
            // The class DiptraceOperations is used to do different operations
            // on the Diptrace project.
    		DiptraceOperations diptraceOperations
                = new DiptraceOperations(diptraceProject);
			DiptraceItem schematicsPartD1
                = diptraceOperations.getSchematicsComponentPart("D1");
			DiptraceItem pcbPartD1
                = diptraceOperations.getPCBComponentPart("D1");
			DiptraceItem schematicsPartR1
                = diptraceOperations.getSchematicsComponentPart("R1");
			DiptraceItem pcbPartR1
                = diptraceOperations.getPCBComponentPart("D1");
			diptraceOperations.duplicateItem(schematicsPartD1);
            
            // Write the diptrace ascii files
            diptraceProject.writeSchematicsAndPCB(
                "flashlight_schematics_new.asc", "flashlight_pcb_new.asc");
            
        } catch (NotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    /**
     * This class should never be instanciated.
     */
    private FlashLight() {
    }
    
}
