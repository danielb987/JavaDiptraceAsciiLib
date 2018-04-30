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
        
        createFlashLight(
            "flashlight_schematics.asc",
            "flashlight_schematics_new.asc",
            "flashlight_pcb.asc",
            "flashlight_pcb_new.asc");
    }
    
    /**
     * Main class.
     * @param schematicsInputFile the schematics input file
     * @param schematicsOutputFile the schematics output file
     * @param pcbInputFile the pcb input file
     * @param pcbOutputFile the pcb outputfile
     */
    public static void createFlashLight(
        final String schematicsInputFile,
        final String schematicsOutputFile,
        final String pcbInputFile,
        final String pcbOutputFile) {
        
        final int numCircles = 4;
        
        final double x0 = 0;
        final double y0 = 0;
        final double radius = 10;
        final int numLEDInFirstCircle = 4;
        final int degrees90 = 90;
        final int degrees360 = 360;
        
        final int moveSchematicsComponentsX = 0;
        final int moveSchematicsComponentsY = 10;
        
        try {
            // Create a diptrace project
            DiptraceProject diptraceProject = new DiptraceProject();
            
            // Read the diptrace ascii files
            diptraceProject.readSchematicsAndPCB(
                schematicsInputFile,
                pcbInputFile);
            
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
            
            for (int circleNo = 1; circleNo <= numCircles; circleNo++) {
//                for (int angle = 0; angle < 360; angle += 360/(circleNo*4)) {
                for (int index = 0;
                    index < numLEDInFirstCircle * circleNo;
                    index++) {
                    
                    double angle
                        = degrees360 / (numLEDInFirstCircle * circleNo) * index;
                    double x = x0 + radius * Math.cos(Math.toRadians(angle));
                    double y = y0 + radius * Math.sin(Math.toRadians(angle));
                    double partAngle = angle + degrees90;
                    
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
                    
                    diptraceOperations.moveItemRelative(
                        newSchematicsPartDiode,
                        moveSchematicsComponentsX,
                        moveSchematicsComponentsY);
                    
                    diptraceOperations.moveItemAbsolute(
                        newPCBPartDiode, x, y);
                    
                    diptraceOperations.moveItemRelative(
                        newSchematicsPartResistor,
                        moveSchematicsComponentsX,
                        moveSchematicsComponentsY);
                    
                    diptraceOperations.moveItemAbsolute(
                        newPCBPartResistor, x, y);
                }
            }
            
            // Write the diptrace ascii files
            diptraceProject.writeSchematicsAndPCB(
                schematicsOutputFile, pcbOutputFile);
            
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
