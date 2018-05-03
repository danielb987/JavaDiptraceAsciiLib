
package javadiptraceasciilib.diptrace.operations;

import java.util.List;
import javadiptraceasciilib.diptrace.tree.DiptraceItem;

/**
 * Holds an item from both the schematics and pcb tree.
 */
public class DiptraceSchematicsAndPCBItem {
    
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
