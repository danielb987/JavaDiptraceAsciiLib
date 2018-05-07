package javadiptraceasciilib;

/**
 * A components pin.
 */
public class DiptraceComponentPin {
    
    /**
     * The component that this pin is on.
     */
    private final DiptraceComponent fComponent;
    
    /**
     * The pin number.
     */
    private final int fPinNo;
    
    /**
     * The DiptraceItem of this pin in the schematics.
     */
    private final DiptraceItem fSchematicsItem;
    
    /**
     * The DiptraceItem of this pin in the pcb.
     */
    private final DiptraceItem fPCBItem;
    
    
    /**
     * Initialize a DiptraceComponentPin object.
     * @param component the component that this pin is on
     * @param pinNo the pin number
     * @param schematicsItem the schematics item of this pin
     * @param pcbItem the pcb item of this pin
     */
    DiptraceComponentPin(
        final DiptraceComponent component,
        final int pinNo,
        final DiptraceItem schematicsItem,
        final DiptraceItem pcbItem) {
        
        this.fComponent = component;
        this.fPinNo = pinNo;
        this.fSchematicsItem = schematicsItem;
        this.fPCBItem = pcbItem;
    }
    
}
