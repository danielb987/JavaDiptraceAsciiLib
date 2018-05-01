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
     * Create a flash light.
     * This class reads a schematics file and a pcb file that has a LED, a
     * resistor and a USB connector. The USB connector is only used for power
     * to the flash light.
     * The method then dublicates the LED and the resistor to get the number of
     * components that is desired an places these components at the correct
     * positions on both the schematics and the pcb card.
     * At last. the method saves the schematics and the pcb to new files.
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
        
        // How many circles of LEDs do we want on the pcb?
        final int numCircles = 4;
        
        // Where on the pcb do we want the center of the flasch light?
        final double x0 = 0;
        final double y0 = -100;
        
        // What radius do we want for the inner most circle on the pcb?
        final double radius = 15;
        
        // How many LEDs do we want in the inner most circle?
        final int numLEDInFirstCircle = 4;
        
        // How long distance do we want to move the new components on the
        // schematics?
        final int moveSchematicsComponentsX = 0;
        final int moveSchematicsComponentsY = 25;
        
        final double degrees90 = 90;
        final double degrees360 = 360;
        
        try {
            // Create a diptrace project. It holds both the schematics and
            // the pcb.
            DiptraceProject diptraceProject = new DiptraceProject();
            
            // Read the diptrace ascii files.
            diptraceProject.readSchematicsAndPCB(
                schematicsInputFile,
                pcbInputFile);
            
            // The class DiptraceOperations is a helper class used to do
            // different operations on the Diptrace project.
            DiptraceOperations diptraceOperations
                = new DiptraceOperations(diptraceProject);
            
            // The DipTrace ascii files keeps the data in a tree structure
            // and a DiptraceItem is a node in that tree. Note that the
            // schematics has one tree and the pcb as another tree. So we work
            // with both these two trees at the same time.
            //
            // We now want the DiptraceItem for the LED in both the
            // schematics and the pcb, as well as the DiptraceItem for the
            // resistor in both the schematics and pcb.
            DiptraceItem schematicsPartD1
                = diptraceOperations.getSchematicsComponentPart("D1");
            DiptraceItem pcbPartD1
                = diptraceOperations.getPCBComponentPart("D1");
            DiptraceItem schematicsPartR1
                = diptraceOperations.getSchematicsComponentPart("R1");
            DiptraceItem pcbPartR1
                = diptraceOperations.getPCBComponentPart("R1");
            
            
            // Count how many LEDs and resistors we have. We start with one
            // since we already have one item of each in the Diptrace ascii
            // files that we already has opened to create our project.
            int numDiodes = 1;
            int numResistors = 1;
            
            for (int circleNo = 1; circleNo <= numCircles; circleNo++) {
                
                for (int index = 0;
                    index < numLEDInFirstCircle * circleNo;
                    index++) {
                    
                    // Calculate where to put the new LED and the new resistor
                    // on the pcb.
                    double angle
                        = degrees360 / (numLEDInFirstCircle * circleNo) * index;
                    double x = x0
                        + circleNo * radius * Math.cos(Math.toRadians(angle));
                    double y = y0
                        + circleNo * radius * Math.sin(Math.toRadians(angle));
                    
                    // We want to rotate the new components as well.
                    int partAngle = (int) Math.round(angle + degrees90);
                    
                    // All new components need a new unique number.
                    int newDiodeNumber
                        = diptraceProject.getNewComponentNumber();
                    int newResistorNumber
                        = diptraceProject.getNewComponentNumber();
                    
                    // All new components need a new unique hidden number.
                    int newDiodeHiddenIdentifier
                        = diptraceProject.getNewComponentHiddenIdentifier();
                    int newResistorHiddenIdentifier
                        = diptraceProject.getNewComponentHiddenIdentifier();
                    
                    // What name should the new components have?
                    String newDiodeName = String.format("D%d", ++numDiodes);
                    String newResistorName
                        = String.format("R%d", ++numResistors);
                    
                    // Create the new components, both LED and resistor, and
                    // both on the schematics and on the pcb.
                    DiptraceItem newSchematicsPartDiode
                        = diptraceOperations.duplicateComponent(
                            schematicsPartD1,
                            newDiodeNumber,
                            newDiodeHiddenIdentifier,
                            newDiodeName);
                    
                    DiptraceItem newPCBPartDiode
                        = diptraceOperations.duplicateComponent(
                            pcbPartD1,
                            newDiodeNumber,
                            newDiodeHiddenIdentifier,
                            newDiodeName);
                    
                    DiptraceItem newSchematicsPartResistor
                        = diptraceOperations.duplicateComponent(
                            schematicsPartR1,
                            newResistorNumber,
                            newResistorHiddenIdentifier,
                            newResistorName);
                    
                    DiptraceItem newPCBPartResistor
                        = diptraceOperations.duplicateComponent(
                            pcbPartR1,
                            newResistorNumber,
                            newResistorHiddenIdentifier,
                            newResistorName);
                    
                    // We want to move the components so they don't end up at
                    // the same spot. We move the components on the schematic
                    // a relative distance and the components on the pcb to
                    // an absolute position.
                    diptraceOperations.moveItemRelative(
                        newSchematicsPartDiode,
                        moveSchematicsComponentsX * numDiodes,
                        moveSchematicsComponentsY * numDiodes);
                    
                    diptraceOperations.moveItemAbsolute(
                        newPCBPartDiode, x, y);
                    
                    diptraceOperations.moveItemRelative(
                        newSchematicsPartResistor,
                        moveSchematicsComponentsX * numResistors,
                        moveSchematicsComponentsY * numResistors);
                    
                    diptraceOperations.moveItemAbsolute(
                        newPCBPartResistor, x, y);
                    
                    // We also want to rotate the components on the pcb.
                    diptraceOperations.rotateItemAbsolute(
                        newPCBPartDiode, partAngle);
                    
                    diptraceOperations.rotateItemAbsolute(
                        newPCBPartResistor, partAngle);
                    
                }
            }
            
            // Write the diptrace ascii files.
            diptraceProject.writeSchematicsAndPCB(
                schematicsOutputFile, pcbOutputFile);
            
        // Since thise is an example, we don't do any fancy error handling.
        } catch (IllegalTokenValue | NotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    /**
     * This class has static methods and should never be instanciated.
     * A private constructor prevents the class from being instanciated.
     */
    private FlashLight() {
    }
    
}
