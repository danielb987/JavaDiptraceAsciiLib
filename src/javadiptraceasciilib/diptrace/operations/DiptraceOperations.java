
package javadiptraceasciilib.diptrace.operations;

import java.util.HashMap;
import java.util.Map;
import javadiptraceasciilib.diptrace.DiptraceProject;
import javadiptraceasciilib.diptrace.exceptions.NotFoundException;
import javadiptraceasciilib.diptrace.tree.DiptraceGenericItem;
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
     * Get the component part in the schematics by its part name.
     * @param partName the name of the part
     * @return the part
     * @throws NotFoundException if the component part is not found
     */
    public DiptraceItem getSchematicsComponentPart(final String partName)
        throws NotFoundException {
        
        DiptraceItem components = fProject.getSchematicsComponents();
        for (DiptraceItem part : components.getSubItems()) {
            
            DiptraceGenericItem theItem = (DiptraceGenericItem) part;
            
            if (partName.equals(
                theItem.getParameters().get(1).getValue().equals(partName))) {
                
//                DiptraceGenericItem numberItem
//                    = (DiptraceGenericItem) part.getSubItem("Number");
//                int number = numberItem.getParameters().get(0).getIntValue();
//                System.out.format("Number: %d%n", number);
                return part;
            }
        }
        
        throw new NotFoundException(
                    String.format(
                        "Component part %s is not found in schematics",
                        partName));
    }
    
    /**
     * Duplicate an item with all its children and adds the new item to the
     * tree.
     * @param item the item to duplicate
     * @return the new item
     */
    public DiptraceItem duplicateItem(final DiptraceItem item) {
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
