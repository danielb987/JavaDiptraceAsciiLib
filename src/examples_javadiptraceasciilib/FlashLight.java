package examples_javadiptraceasciilib;

import java.io.IOException;
import javadiptraceasciilib.diptrace.DiptraceProject;

/**
 * Create a flash light PCB.
 */
public final class FlashLight {
    
    /**
     * Main class.
     * @param args command line arguments.
     */
    public static void main(String args[]) {
        
		try {
            // Create a diptrace project
            DiptraceProject diptraceProject = new DiptraceProject();
            
            // Read the diptrace ascii files
			diptraceProject.readSchematicsAndPCB("schematics.asc", "pcb.asc");
            
            
            // Write the diptrace ascii files
			diptraceProject.writeSchematicsAndPCB(
                "schematics_new.asc", "pcb_new.asc");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
    }
    
}
