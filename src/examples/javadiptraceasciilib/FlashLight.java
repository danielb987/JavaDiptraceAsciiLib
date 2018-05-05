package examples.javadiptraceasciilib;

// import java.io.IOException;
import javadiptraceasciilib.DiptraceOperations;
import javadiptraceasciilib.DiptraceProject;
import javadiptraceasciilib.IllegalTokenValue;

//CHECKSTYLE.OFF: ParameterNumber - Accept many parameters

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
     * Duplicate a diode and a resistor at the desired place.
     * @param diptraceProject the DiptraceProject instance
     * @param diptraceOperations the DiptraceOperations instance
     * @param schematicsPartD1 the diode D1 on the schematics
     * @param pcbPartD1 the diode D1 on the pcb
     * @param schematicsPartR1 the resistor R1 on the schematics
     * @param pcbPartR1 the resistor R1 on the pcb
     * @param newDiodeName the new name of the diode
     * @param newResistorName the new name of the resistor
     * @param schematicsDistX how long to move the parts on the schematics
     * @param schematicsDistY how long to move the parts on the schematics
     * @param pcbX where to put the parts on the pcb
     * @param pcbY where to put the parts on the pcb
     * @throws IllegalTokenValue then a token cannot be given the desired value
     */
    private static void addDiodeAndResistor(
        final DiptraceProject diptraceProject,
        final DiptraceOperations diptraceOperations,
//        final DiptraceItem schematicsPartD1,
//        final DiptraceItem pcbPartD1,
//        final DiptraceItem schematicsPartR1,
//        final DiptraceItem pcbPartR1,
        final String newDiodeName,
        final String newResistorName,
        final double schematicsDistX,
        final double schematicsDistY,
        final double pcbX,
        final double pcbY
    ) throws IllegalTokenValue {
/*
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
                        schematicsDistX,
                        schematicsDistY);
                    
                    diptraceOperations.moveItemAbsolute(
                        newPCBPartDiode, pcbX, pcbY);
                    
                    diptraceOperations.moveItemRelative(
                        newSchematicsPartResistor,
                        schematicsDistX,
                        schematicsDistY);
                    
                    diptraceOperations.moveItemAbsolute(
                        newPCBPartResistor, pcbX, pcbY);
*/
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
        
/*
        // How many circles of LEDs do we want on the pcb?
//        final int numCircles = 4;
        final int numCircles = 5;
        
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
            
            // Move the diode and the resistor to the center of the circle
            diptraceOperations.moveItemAbsolute(
                pcbPartD1, x0, y0);
            
            diptraceOperations.moveItemAbsolute(
                pcbPartR1, x0, y0);
            
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
                    // on the schematics
                    double schematicsX = moveSchematicsComponentsX * numDiodes;
                    double schematicsY = moveSchematicsComponentsY * numDiodes;
                    
                    // Calculate where to put the new LED and the new resistor
                    // on the pcb.
                    double angle
                        = degrees360 / (numLEDInFirstCircle * circleNo) * index;
                    double pcbX = x0
                        + circleNo * radius * Math.cos(Math.toRadians(angle));
                    double pcbY = y0
                        + circleNo * radius * Math.sin(Math.toRadians(angle));
                    
                    // What name should the new components have?
                    String newDiodeName = String.format("D%d", ++numDiodes);
                    String newResistorName
                        = String.format("R%d", ++numResistors);
                    
                    addDiodeAndResistor(
                        diptraceProject,
                        diptraceOperations,
                        schematicsPartD1,
                        pcbPartD1,
                        schematicsPartR1,
                        pcbPartR1,
                        newDiodeName,
                        newResistorName,
                        schematicsX,
                        schematicsY,
                        pcbX, pcbY);
                }
            }
            
            //CHECKSTYLE.OFF: MagicNumber - Only for printout
            System.out.format(
                "Total num components: %d, Num pins: %d%n",
                numDiodes * 2 + 1, numDiodes * 4 + 11);
            //CHECKSTYLE.ON: MagicNumber - Only for printout
            
            // Write the diptrace ascii files.
            diptraceProject.writeSchematicsAndPCB(
                schematicsOutputFile, pcbOutputFile);
            
        // Since thise is an example, we don't do any fancy error handling.
        } catch (IllegalTokenValue | NotFoundException | IOException ex) {
            ex.printStackTrace();
        }
*/
    }
    
    
    /**
     * This class has static methods and should never be instanciated.
     * A private constructor prevents the class from being instanciated.
     */
    private FlashLight() {
    }
    
}

//CHECKSTYLE.ON: ParameterNumber - Accept many parameters
