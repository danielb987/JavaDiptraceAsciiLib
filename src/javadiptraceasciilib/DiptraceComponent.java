package javadiptraceasciilib;

import java.util.ArrayList;
import java.util.List;

/**
 * A Diptrace component.
 */
public class DiptraceComponent {
    
    /**
     * A list of the parts of this component.
     */
    private final List<DiptraceItem> fSchematicsComponentParts;
    
    private final DiptraceItem fPCBComponent;
    
    /**
     * Initialize a DiptraceComponent object.
     * @param diptraceOperations the diptraceOperations obhject
     * @param refDes the RefDes of the component
     */
//    DiptraceComponent(DiptraceOperations diptraceOperations, String refDes) {
//
//    }
    
    /**
     * Initialize a DiptraceComponent object.
     * @param schematicsComponentPart the schematics component part
     * @param pcbComponent the pcb component
     */
    DiptraceComponent(
        List<DiptraceItem> schematicsComponentPart,
        DiptraceItem pcbComponent) {
        
        this.fSchematicsComponentParts = schematicsComponentPart;
        this.fPCBComponent = pcbComponent;
    }
    
    /**
     * Get the schematics component parts.
     * @return the parts
     */
    List<DiptraceItem> getSchematicsComponentParts() {
        return fSchematicsComponentParts;
    }
    
    /**
     * Get the pcb component.
     * @return the component
     */
    DiptraceItem getPCBComponent() {
        return fPCBComponent;
    }
    
}
