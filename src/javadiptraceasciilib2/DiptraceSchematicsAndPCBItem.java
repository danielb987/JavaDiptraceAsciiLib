package javadiptraceasciilib2;

import java.util.List;

/**
 * Holds an item from both the schematics and pcb tree.
 */
class DiptraceSchematicsAndPCBItem {
    
    /**
     * The schematics item.
     */
    private final List<DiptraceItem> fSchematicsItems;
    
    /**
     * The pcb item.
     */
    private final DiptraceItem fPCBItem;
    
    /**
     * Initialize a DiptraceSchematicsAndPCBItem object.
     * @param schematicsItems a list of the schematics items
     * @param pcbItem the pcb item
     */
    public DiptraceSchematicsAndPCBItem(
        final List<DiptraceItem> schematicsItems,
        final DiptraceItem pcbItem) {
        
        this.fSchematicsItems = schematicsItems;
        this.fPCBItem = pcbItem;
    }
    
    /**
     * Get the schematics item.
     * @return the item
     */
    public List<DiptraceItem> getSchematicsItems() {
        return fSchematicsItems;
    }
    
    /**
     * Get the pcb item.
     * @return the item
     */
    public DiptraceItem getPCBItem() {
        return fPCBItem;
    }
    
}
