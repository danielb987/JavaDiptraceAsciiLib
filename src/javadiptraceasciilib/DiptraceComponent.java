package javadiptraceasciilib;

import java.util.List;

/**
 * A Diptrace component.
 */
public final class DiptraceComponent {
    
    /**
     * A list of the schematics component parts of this component.
     */
    private final List<DiptraceItem> fSchematicsComponentParts;
    
    /**
     * The pcb component of this component.
     */
    private final DiptraceItem fPCBComponent;
    
    /**
     * Initialize a DiptraceComponent object.
     * @param schematicsComponentPart the schematics component part
     * @param pcbComponent the pcb component
     */
    DiptraceComponent(
        final List<DiptraceItem> schematicsComponentPart,
        final DiptraceItem pcbComponent) {
        
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
