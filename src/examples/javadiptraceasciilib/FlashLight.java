package examples.javadiptraceasciilib;

import java.io.IOException;
import javadiptraceasciilib.diptrace.DiptraceProject;
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
			DiptraceItem part
                = diptraceOperations.getSchematicsComponentPart("aaa");
			diptraceOperations.duplicateItem(part);
            
            // Write the diptrace ascii files
            diptraceProject.writeSchematicsAndPCB(
                "flashlight_schematics_new.asc", "flashlight_pcb_new.asc");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    /**
     * This class should never be instanciated.
     */
    private FlashLight() {
    }
    
}
