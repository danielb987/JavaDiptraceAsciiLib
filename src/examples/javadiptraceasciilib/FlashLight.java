package examples.javadiptraceasciilib;

import java.io.IOException;
import javadiptraceasciilib.diptrace.DiptraceProject;
import javadiptraceasciilib.diptrace.exceptions.IllegalTokenValue;
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
        
        final double x0 = 0;
        final double y0 = 0;
        final double radius = 10;
        
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
            
            // Get the LED D1 and the resistor R1 for both the schematics
            // and the PCB.
            DiptraceItem schematicsPartD1
                = diptraceOperations.getSchematicsComponentPart("D1");
            DiptraceItem pcbPartD1
                = diptraceOperations.getPCBComponentPart("D1");
            DiptraceItem schematicsPartR1
                = diptraceOperations.getSchematicsComponentPart("R1");
            DiptraceItem pcbPartR1
                = diptraceOperations.getPCBComponentPart("D1");
            
            for (int circleNo = 1; circleNo <= 4; circleNo++) {
//                for (int angle = 0; angle < 360; angle += 360/(circleNo*4)) {
                for (int index=0; index < 4*circleNo; index++) {
                    double angle = 360 / (4 * circleNo) * index;
                    double x = x0 + radius * Math.cos(Math.toRadians(angle));
                    double y = y0 + radius * Math.sin(Math.toRadians(angle));
                    double partAngle = angle + 90;
                    
                    int newDiodeNumber
                        = diptraceProject.getNewComponentNumber();
                    int newResistorNumber
                        = diptraceProject.getNewComponentNumber();
                    
                    DiptraceItem newSchematicsPartDiode
                        = diptraceOperations.duplicateComponent(
                            schematicsPartD1, newDiodeNumber);
                    DiptraceItem newPCBPartDiode
                        = diptraceOperations.duplicateComponent(
                            pcbPartD1, newDiodeNumber);
                    DiptraceItem newSchematicsPartResistor
                        = diptraceOperations.duplicateComponent(
                            schematicsPartR1, newResistorNumber);
                    DiptraceItem newPCBPartResistor
                        = diptraceOperations.duplicateComponent(
                            pcbPartR1, newResistorNumber);
                    
                    diptraceOperations.moveItemRelative(newSchematicsPartDiode, 0, 10);
                    diptraceOperations.moveItemAbsolute(newPCBPartDiode, x, y);
                    diptraceOperations.moveItemRelative(newSchematicsPartResistor, 0, 10);
                    diptraceOperations.moveItemAbsolute(newPCBPartResistor, x, y);
                }
            }
            
            // Write the diptrace ascii files
            diptraceProject.writeSchematicsAndPCB(
                "flashlight_schematics_new.asc", "flashlight_pcb_new.asc");
            
        } catch (IllegalTokenValue | NotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    /**
     * This class should never be instanciated.
     */
    private FlashLight() {
    }
    
}
