
package javadiptraceasciilib.diptrace.operations;

import java.util.HashMap;
import java.util.Map;
import javadiptraceasciilib.diptrace.DiptraceProject;
import javadiptraceasciilib.diptrace.tree.DiptraceItem;

/**
 * Do different operations on a DiptraceProject.
 */
public class DiptraceOperations {
    
    /**
     * The project that the operations are done at.
     */
    private final DiptraceProject fProject;
    
    /**
     * Initialize a DiptraceOperations object.
     * @param project the project
     */
    public DiptraceOperations(final DiptraceProject project) {
        this.fProject = project;
    }
    
    /**
     * Duplicate an item with all its children and adds the new item to the
     * tree.
     * @param item the item to duplicate
     * @return the new item
     */
    public DiptraceItem duplicateItem(DiptraceItem item) {
        DiptraceItem newItem = item.duplicate(item.getParent());
        
        item.getSubItems().add(newItem);
        
        return newItem;
    }
    
    /**
     * Duplicate a component to a lot of other components of the same type
     * in both schematics and pcb.
     * @param number the number of the component to duplicate
     * @return a map of the new components with the component number as key
     */
    public Map<Integer, DiptraceItem> duplicateComponents(final int number) {
        Map<Integer, DiptraceItem> newComponents = new HashMap<>();
        
        return newComponents;
    }
    
}
