package javadiptraceasciilib;

import java.util.List;

/**
 * Holds an item from both the schematics and pcb tree.
 */
public class DiptraceNet {
    
    /**
     * The schematics net item.
     */
    private final DiptraceItem fSchematicsNet;
    
    /**
     * The pcb net item.
     */
    private final DiptraceItem fPCBNet;
    
    /**
     * Initialize a DiptraceNet object.
     * @param schematicsItem the schematics net item
     * @param pcbItem the pcb net item
     */
    DiptraceNet(
        final DiptraceItem schematicsNetItem,
        final DiptraceItem pcbNetItem) {
        
        this.fSchematicsNet = schematicsNetItem;
        this.fPCBNet = pcbNetItem;
    }
    
    /**
     * Get the schematics net item.
     * @return the item
     */
    public DiptraceItem getSchematicsNet() {
        return fSchematicsNet;
    }
    
    /**
     * Get the pcb net item.
     * @return the item
     */
    public DiptraceItem getPCBNet() {
        return fPCBNet;
    }
    
}
