package examples.javadiptraceasciilib;

import java.io.IOException;
import javadiptraceasciilib.DiptraceComponent;
import javadiptraceasciilib.DiptraceNet;
import javadiptraceasciilib.DiptraceNetNameAlreadyExistsException;
import javadiptraceasciilib.DiptraceProject;
import javadiptraceasciilib.DiptraceRefDesAlreadyExistsException;
import javadiptraceasciilib.NotFoundException;

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
        
        // Remove this later!!
        final int add = 5;
        
        try {
            // Create a diptrace project. It holds both the schematics and
            // the pcb.
            DiptraceProject diptraceProject = new DiptraceProject();
            
            // Read the diptrace ascii files.
            diptraceProject.readSchematicsAndPCB(
                schematicsInputFile,
                pcbInputFile);
            
            // Get the D1 component and the R1 component.
            DiptraceComponent diptraceComponentD1
                = diptraceProject.getComponentByRefDes("D1");
            DiptraceComponent diptraceComponentR1
                = diptraceProject.getComponentByRefDes("R1");
            
            DiptraceNet diptraceNetNet1
                = diptraceProject.getNetByName("Net 1");
//            DiptraceNet diptraceNetNet3a5
//                = diptraceOperations.getNetByName("Net   3 a     5");
            
            // The DipTrace ascii files keeps the data in a tree structure
            // and a DiptraceItem is a node in that tree. Note that the
            // schematics has one tree and the pcb as another tree. So we work
            // with both these two trees at the same time.
            
            diptraceComponentD1.moveAbsoluteOnPCB(x0, y0);
            diptraceComponentR1.moveAbsoluteOnPCB(x0, y0);
            
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
                    String newDiodeName
                        = String.format("D%d", ++numDiodes + add);
                    String newResistorName
                        = String.format("R%d", ++numResistors + add);
                    
                    String newNetName
                        = String.format("Net %d", numResistors + add);
                    
                    DiptraceComponent newDiodeComponent =
                        diptraceComponentD1.duplicate(newDiodeName);
                    
                    DiptraceComponent newResistorComponent =
                        diptraceComponentR1.duplicate(newResistorName);
                    
                    newDiodeComponent
                        .moveRelativeOnSchematics(schematicsX, schematicsY);
                    newResistorComponent
                        .moveRelativeOnSchematics(schematicsX, schematicsY);
                    
                    newDiodeComponent.moveAbsoluteOnPCB(pcbX, pcbY);
                    newResistorComponent.moveAbsoluteOnPCB(pcbX, pcbY);
                    
                    DiptraceNet newNet =
                        diptraceNetNet1.duplicateNet(newNetName);
                    
//                    DiptraceComponentPin componentPinNewDiodePin0
//                        = newDiodeComponent.getPin(0);
//                    DiptraceComponentPin componentPinNewDiodePin1
//                        = diptraceOperations.getComponentPin(
//                            newDiodeComponent,1);
//                    DiptraceComponentPin componentPinNewResistorPin0
//                        = newDiodeComponent.getPin(0);
//                    DiptraceComponentPin componentPinNewResistorPin1
//                        = newDiodeComponent.getPin(1);
//                    newNet.connectToPin(componentPinNewDiodePin0);
//                    newNet.connectToPin(componentPinNewDiodePin1);
//                    newNet.connectToPin(componentPinNewResistorPin0);
//                    newNet.connectToPin(componentPinNewResistorPin1);
                }
            }
            
            // Write the diptrace ascii files.
            diptraceProject.writeSchematicsAndPCB(
                schematicsOutputFile, pcbOutputFile);
            
        // Since thise is an example, we don't do any fancy error handling.
        } catch (DiptraceRefDesAlreadyExistsException
            | DiptraceNetNameAlreadyExistsException | NotFoundException
            | IOException ex) {
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
