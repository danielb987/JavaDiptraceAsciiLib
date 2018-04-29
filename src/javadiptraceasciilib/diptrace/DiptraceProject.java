package javadiptraceasciilib.diptrace;

// import javadiptraceasciilib.diptrace.tokenizer.DiptraceTokenType;
import javadiptraceasciilib.diptrace.tokenizer.DiptraceTokenizer;
// import javadiptraceasciilib.diptrace.tokenizer.DiptraceToken;
import javadiptraceasciilib.diptrace.tree.DiptraceRootItem;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javadiptraceasciilib.diptrace.tree.DiptraceGenericItem;
import javadiptraceasciilib.diptrace.tree.DiptraceItem;

/**
 * This class is holds a diptrace project. It can have both the schematics
 * and the pcb.
 */
public final class DiptraceProject {
    
    /**
     * Root of the diptrace schematics item tree.
     */
    private final DiptraceRootItem fSchematicsRoot = new DiptraceRootItem();
    
    /**
     * Root of the diptrace pcb item tree.
     */
    private final DiptraceRootItem fPCBRoot = new DiptraceRootItem();
    
    /**
     * Map of the components in the schematics where the key is the component's
     * number.
     */
    private final Map<Integer, DiptraceItem> fSchematicsComponentsNumberMap
        = new HashMap<>();
    
    /**
     * Map of the components in the pcb where the key is the component's
     * number.
     */
    private final Map<Integer, DiptraceItem> fPCBComponentsNumberMap
        = new HashMap<>();
    
    /**
     * Map of the numbers used as component numbers with flags that tells
     * whenether a number is used in the schematics and/or the pcb.
     */
    private final Map<Integer, SchematicsPCBFlags> fUsedComponentNumbers
        = new HashMap<>();
    
    /**
     * Constructs a DiptraceProject.
     */
    public DiptraceProject() {
    }
    
    /**
     * Get the root item of the schematics.
     * @return the root item of the Diptrace schematics item tree
     */
    public DiptraceRootItem getSchematicsRoot() {
        return fSchematicsRoot;
    }
    
    /**
     * Get the root item of the schematics.
     * @return the root item of the Diptrace schematics item tree
     */
    public DiptraceRootItem getPCBRoot() {
        return fPCBRoot;
    }
    
    
    /**
     * Get the components of the schematics.
     * @return the DiptraceItem that has all the components as DiptraceItem
     * children
     */
    public DiptraceItem getSchematicsComponents() {
        return fSchematicsRoot.getSubItem("Schematic").getSubItem("Components");
    }
    
    /**
     * Parse a schematics file.
     * @param tokenizer the tokenizer that parses the Diptrace ascii file
     * @throws IOException when IO error occurs
     */
    public void parseSchematics(final DiptraceTokenizer tokenizer)
        throws IOException {
        
        fSchematicsRoot.parse(tokenizer);
        
        DiptraceItem components = getSchematicsComponents();
        for (DiptraceItem parts : components.getSubItems()) {
            DiptraceGenericItem numberItem
                = (DiptraceGenericItem) parts.getSubItem("Number");
            int number = numberItem.getParameters().get(0).getIntValue();
            System.out.format("Number: %d%n", number);
            fSchematicsComponentsNumberMap.put(number, parts);
            SchematicsPCBFlags schematicsPCBFlags
                = fUsedComponentNumbers.get(number);
            if (schematicsPCBFlags != null) {
                schematicsPCBFlags.fSchematics = true;
            } else {
                schematicsPCBFlags = new SchematicsPCBFlags();
                schematicsPCBFlags.fSchematics = true;
                fUsedComponentNumbers.put(number, schematicsPCBFlags);
            }
        }
    }
    
    /**
     * Parse a pcb file.
     * @param tokenizer the tokenizer that parses the Diptrace ascii file
     * @throws IOException when IO error occurs
     */
    public void parsePCB(final DiptraceTokenizer tokenizer)
        throws IOException {
        
        fPCBRoot.parse(tokenizer);
        
        DiptraceItem components = getSchematicsComponents();
        for (DiptraceItem component : components.getSubItems()) {
            DiptraceGenericItem numberItem
                = (DiptraceGenericItem) component.getSubItem("Number");
            int number = numberItem.getParameters().get(0).getIntValue();
            System.out.format("Number: %d%n", number);
            fSchematicsComponentsNumberMap.put(number, component);
            SchematicsPCBFlags schematicsPCBFlags
                = fUsedComponentNumbers.get(number);
            if (schematicsPCBFlags != null) {
                schematicsPCBFlags.fPCB = true;
            } else {
                schematicsPCBFlags = new SchematicsPCBFlags();
                schematicsPCBFlags.fPCB = true;
                fUsedComponentNumbers.put(number, schematicsPCBFlags);
            }
        }
    }
    
    
    /**
     * Holds flags for schematics and pcb.
     */
    private class SchematicsPCBFlags {
        
        private boolean fSchematics;
        private boolean fPCB;
    }
    
}
