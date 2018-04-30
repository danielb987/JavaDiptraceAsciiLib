package javadiptraceasciilib.diptrace;

// import javadiptraceasciilib.diptrace.tokenizer.DiptraceTokenType;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import javadiptraceasciilib.diptrace.tokenizer.DiptraceTokenizer;
// import javadiptraceasciilib.diptrace.tokenizer.DiptraceToken;
import javadiptraceasciilib.diptrace.tree.DiptraceRootItem;
import java.io.IOException;
import java.io.Writer;
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
    private final Map<Integer, SchematicsAndPCBFlags> fUsedComponentNumbers
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
     * Get the map of the numbers used as component numbers with flags that
     * tells whenether a number is used in the schematics and/or the pcb.
     * @return a map of the used component numbers
     */
    public Map<Integer, SchematicsAndPCBFlags> getUsedComponentNumbers() {
        return fUsedComponentNumbers;
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
     * Get the components of the pcb.
     * @return the DiptraceItem that has all the components as DiptraceItem
     * children
     */
    public DiptraceItem getPCBComponents() {
        return fPCBRoot.getSubItem("Board").getSubItem("Components");
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
        for (DiptraceItem part : components.getSubItems()) {
            DiptraceGenericItem numberItem
                = (DiptraceGenericItem) part.getSubItem("Number");
            int number = numberItem.getParameters().get(0).getIntValue();
            System.out.format("Number: %d%n", number);
            fSchematicsComponentsNumberMap.put(number, part);
            SchematicsAndPCBFlags schematicsAndPCBFlags
                = fUsedComponentNumbers.get(number);
            if (schematicsAndPCBFlags != null) {
                schematicsAndPCBFlags.fSchematicsFlag = true;
            } else {
                schematicsAndPCBFlags = new SchematicsAndPCBFlags();
                schematicsAndPCBFlags.fSchematicsFlag = true;
                fUsedComponentNumbers.put(number, schematicsAndPCBFlags);
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
            fPCBComponentsNumberMap.put(number, component);
            SchematicsAndPCBFlags schematicsAndPCBFlags
                = fUsedComponentNumbers.get(number);
            if (schematicsAndPCBFlags != null) {
                schematicsAndPCBFlags.fPCBFlag = true;
            } else {
                schematicsAndPCBFlags = new SchematicsAndPCBFlags();
                schematicsAndPCBFlags.fPCBFlag = true;
                fUsedComponentNumbers.put(number, schematicsAndPCBFlags);
            }
        }
    }
    
    /**
     * Read schematics and pcb to files.
     * @param schematicsFilename the schematics file name
     * @param pcbFilename the pcb file name
     * @throws FileNotFoundException if the file is not found
     * @throws IOException if any I/O error occurs
     */
    public void readSchematicsAndPCB(
                    final String schematicsFilename,
                    final String pcbFilename)
        throws FileNotFoundException, IOException {
        
        try (BufferedReader br
                = new BufferedReader(new FileReader(schematicsFilename));
            BufferedReader br2
                = new BufferedReader(new FileReader(pcbFilename))) {
            
            DiptraceTokenizer tokenizer = new DiptraceTokenizer(br);
            parseSchematics(tokenizer);
            
            tokenizer = new DiptraceTokenizer(br2);
            parsePCB(tokenizer);
        }
    }
    
    /**
     * Write a schematics file.
     * @param writer the writer that writes to the Diptrace ascii file
     * @throws IOException when IO error occurs
     */
    public void writeSchematics(final Writer writer)
        throws IOException {
        
        fSchematicsRoot.write(writer, "");
    }
    
    /**
     * Write a pcb file.
     * @param writer the writer that writes to the Diptrace ascii file
     * @throws IOException when IO error occurs
     */
    public void writePCB(final Writer writer)
        throws IOException {
        
        fPCBRoot.write(writer, "");
    }
    
    /**
     * Write schematics and pcb to files.
     * @param schematicsFilename the schematics file name
     * @param pcbFilename the pcb file name
     * @throws IOException if any I/O error occurs
     */
    public void writeSchematicsAndPCB(
                    final String schematicsFilename,
                    final String pcbFilename)
        throws IOException {
        
        try (BufferedWriter writer
                = new BufferedWriter(new FileWriter(schematicsFilename));
            BufferedWriter writer2
                = new BufferedWriter(new FileWriter(pcbFilename))) {
            
            writeSchematics(writer);
            writePCB(writer2);
            
        }
	}

    
    /**
     * Holds flags for schematics and pcb.
     */
    public static final class SchematicsAndPCBFlags {
        
        /**
         * Flag for schematics.
         */
        private boolean fSchematicsFlag;
        
        /**
         * Flag for pcb.
         */
        private boolean fPCBFlag;
        
        /**
         * Initialize a SchematicsAndPCBFlags object.
         */
        private SchematicsAndPCBFlags() {
            // This class is not intended to be created outside the class
            // DiptraceProject.
        }
        
        /**
         * Get the schematics flag.
         * @return the flag
         */
        public boolean getSchematicsFlag() {
            return fSchematicsFlag;
        }
        
        /**
         * Get the pcb flag.
         * @return the flag
         */
        public boolean getPCBFlag() {
            return fPCBFlag;
        }
        
    }
    
}
