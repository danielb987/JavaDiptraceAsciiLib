
package javadiptraceasciilib.diptrace.operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javadiptraceasciilib.diptrace.DiptraceProject;
import javadiptraceasciilib.diptrace.tree.DiptraceItem;

/**
 * Do different operations on a DiptraceProject
 */
public class DiptraceOperations {
    
    /**
     * The project that the operations are done at.
     */
    private final DiptraceProject fProject;
    
    /**
     * Initialize a DiptraceOperations object
     * @param project the project
     */
    public DiptraceOperations(final DiptraceProject project) {
        this.fProject = project;
    }
    
    /**
     * Duplicate a component to a lot of other components of the same type
     * in both schematics and pcb.
     * @param number the number of the component to duplicate
     * @return a map of the new components with the component number as key
     */
    public Map<Integer, DiptraceItem> duplicateComponents(int number) {
        Map<Integer, DiptraceItem> newComponents = new HashMap<>();
        
        return newComponents;
    }
    
}
