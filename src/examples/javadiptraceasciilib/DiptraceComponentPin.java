package examples.javadiptraceasciilib;

import javadiptraceasciilib.DiptraceComponent;
import javadiptraceasciilib.DiptraceItem;

/**
 * A components pin
 */
public class DiptraceComponentPin {
    
    private final DiptraceComponent fComponent;
    private final int fPinNo;
    private final DiptraceItem fSchematicsItem;
    private final DiptraceItem fPCBItem;
    
    DiptraceComponentPin(
        DiptraceComponent component,
        int pinNo,
        DiptraceItem schematicsItem,
        DiptraceItem pcbItem) {
        
        this.fComponent = component;
        this.fPinNo = pinNo;
        this.fSchematicsItem = schematicsItem;
        this.fPCBItem = pcbItem;
    }
    
}
