package javadiptraceasciilib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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
     * The last used number of any component on either schematics or pcb.
     */
    private int fLastComponentNumber = 0;
    
    /**
     * The last used hidden number of any component on either schematics or pcb.
     */
    private int fLastComponentHiddenIdentifier = 0;
    
    /**
     * Map of the nets in the schematics where the key is the component's
     * number.
     */
    private final Map<Integer, DiptraceItem> fNetComponentsNumberMap
        = new HashMap<>();
    
    /**
     * Map of the nets in the pcb where the key is the component's
     * number.
     */
    private final Map<Integer, DiptraceItem> fPCBNetsNumberMap
        = new HashMap<>();
    
    /**
     * Map of the numbers used as net numbers with flags that tells
     * whenether a number is used in the schematics and/or the pcb.
     */
    private final Map<Integer, SchematicsAndPCBFlags> fUsedNetNumbers
        = new HashMap<>();
    
    /**
     * The last used number of any net on either schematics or pcb.
     */
    private int fLastNetNumber = 0;
    
    /**
     * Constructs a DiptraceProject.
     */
    public DiptraceProject() {
    }
    
    /**
     * Get the root item of the schematics.
     * @return the root item of the Diptrace schematics item tree
     */
    public DiptraceItem getSchematicsRoot() {
        return fSchematicsRoot;
    }
    
    /**
     * Get the root item of the schematics.
     * @return the root item of the Diptrace schematics item tree
     */
    public DiptraceItem getPCBRoot() {
        return fPCBRoot;
    }
    
    
    /**
     * Get the map of the numbers used as component numbers with flags that
     * tells whenether a number is used in the schematics and/or the pcb.
     * @return a map of the used component numbers
     */
    Map<Integer, SchematicsAndPCBFlags> getUsedComponentNumbers() {
        return fUsedComponentNumbers;
    }
    
    /**
     * Update the last component number.
     * @param number a component number
     */
    private void updateLastComponentNumber(final int number) {
        if (fLastComponentNumber < number) {
            fLastComponentNumber = number;
        }
    }
    
    /**
     * Update the last component hidden number.
     * @param number a component number
     */
    private void updateLastComponentHiddenIdentifier(final int number) {
        if (fLastComponentHiddenIdentifier < number) {
            fLastComponentHiddenIdentifier = number;
        }
    }
    
    /**
     * Update the last net number.
     * @param number a net number
     */
    private void updateLastNetNumber(final int number) {
        if (fLastNetNumber < number) {
            fLastNetNumber = number;
        }
    }
    
    /**
     * Returns a unused component number.
     * @return a new component number
     */
    int getNewComponentNumber() {
        return ++fLastComponentNumber;
    }
    
    /**
     * Returns a unused component hidden number.
     * @return a new component number
     */
    int getNewComponentHiddenIdentifier() {
        return ++fLastComponentHiddenIdentifier;
    }
    
    /**
     * Returns a unused component number.
     * @return a new component number
     */
    int getNewNetNumber() {
        return ++fLastNetNumber;
    }
    
    /**
     * Get the components of the schematics.
     * @return the DiptraceItem that has all the components as DiptraceItem
     * children
     */
    DiptraceItem getSchematicsComponents() {
        return fSchematicsRoot.getSubItem("Schematic").getSubItem("Components");
    }
    
    /**
     * Get the components of the pcb.
     * @return the DiptraceItem that has all the components as DiptraceItem
     * children
     */
    DiptraceItem getPCBComponents() {
        return fPCBRoot.getSubItem("Board").getSubItem("Components");
    }
    
    /**
     * Get the nets of the schematics.
     * @return the DiptraceItem that has all the nets as DiptraceItem
     * children
     */
    DiptraceItem getSchematicsNets() {
        return fSchematicsRoot.getSubItem("Schematic").getSubItem("Nets");
    }
    
    /**
     * Get the nets of the pcb.
     * @return the DiptraceItem that has all the nets as DiptraceItem
     * children
     */
    DiptraceItem getPCBNets() {
        return fPCBRoot.getSubItem("Board").getSubItem("Nets");
    }
    
    /**
     * Parse a schematics file.
     * @param tokenizer the tokenizer that parses the Diptrace ascii file
     * @throws IOException when IO error occurs
     */
    void parseSchematics(final DiptraceTokenizer tokenizer)
        throws IOException {
        
        fSchematicsRoot.parse(tokenizer);
        
        DiptraceItem components = getSchematicsComponents();
        for (DiptraceItem part : components.getSubItems()) {
            DiptraceGenericItem numberItem
                = (DiptraceGenericItem) part.getSubItem("Number");
            int number
                = ((DiptraceIntegerAttribute) numberItem.getAttributes().get(0))
                    .getInt();
            System.out.format("Number: %d%n", number);
            updateLastComponentNumber(number);
            DiptraceGenericItem hiddenIdentifierItem
                = (DiptraceGenericItem) part.getSubItem("HiddenId");
            int hiddenIdentifier
                = ((DiptraceIntegerAttribute)
                    hiddenIdentifierItem.getAttributes().get(0))
                    .getInt();
            updateLastComponentHiddenIdentifier(hiddenIdentifier);
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
        
        DiptraceItem nets = getSchematicsNets();
        for (DiptraceItem net : nets.getSubItems()) {
            DiptraceGenericItem numberItem
                = (DiptraceGenericItem) net.getSubItem("Number");
            int number
                = ((DiptraceIntegerAttribute) numberItem.getAttributes().get(0))
                    .getInt();
            System.out.format("Number: %d%n", number);
            updateLastNetNumber(number);
            fPCBNetsNumberMap.put(number, net);
            SchematicsAndPCBFlags schematicsAndPCBFlags
                = fUsedNetNumbers.get(number);
            if (schematicsAndPCBFlags != null) {
                schematicsAndPCBFlags.fPCBFlag = true;
            } else {
                schematicsAndPCBFlags = new SchematicsAndPCBFlags();
                schematicsAndPCBFlags.fPCBFlag = true;
                fUsedNetNumbers.put(number, schematicsAndPCBFlags);
            }
        }
    }
    
    /**
     * Parse a pcb file.
     * @param tokenizer the tokenizer that parses the Diptrace ascii file
     * @throws IOException when IO error occurs
     */
    void parsePCB(final DiptraceTokenizer tokenizer)
        throws IOException {
        
        fPCBRoot.parse(tokenizer);
        
        DiptraceItem components = getPCBComponents();
        for (DiptraceItem component : components.getSubItems()) {
            DiptraceGenericItem numberItem
                = (DiptraceGenericItem) component.getSubItem("Number");
            int number
                = ((DiptraceIntegerAttribute) numberItem.getAttributes().get(0))
                    .getInt();
            System.out.format("Number: %d%n", number);
            updateLastComponentNumber(number);
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
        
        DiptraceItem nets = getPCBNets();
        for (DiptraceItem net : nets.getSubItems()) {
            DiptraceGenericItem numberItem
                = (DiptraceGenericItem) net.getSubItem("Number");
            int number
                = ((DiptraceIntegerAttribute) numberItem.getAttributes().get(0))
                    .getInt();
            System.out.format("Number: %d%n", number);
            updateLastNetNumber(number);
            fPCBNetsNumberMap.put(number, net);
            SchematicsAndPCBFlags schematicsAndPCBFlags
                = fUsedNetNumbers.get(number);
            if (schematicsAndPCBFlags != null) {
                schematicsAndPCBFlags.fPCBFlag = true;
            } else {
                schematicsAndPCBFlags = new SchematicsAndPCBFlags();
                schematicsAndPCBFlags.fPCBFlag = true;
                fUsedNetNumbers.put(number, schematicsAndPCBFlags);
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
        
        try (BufferedReader schematicsReader
                = new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(schematicsFilename),
                        StandardCharsets.UTF_8));
            BufferedReader pcbReader
                = new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(pcbFilename),
                        StandardCharsets.UTF_8))) {
            
            DiptraceTokenizer tokenizer;
            
            tokenizer = new DiptraceTokenizer(schematicsReader);
            parseSchematics(tokenizer);
            
            tokenizer = new DiptraceTokenizer(pcbReader);
            parsePCB(tokenizer);
        }
    }
    
    /**
     * Write a schematics file.
     * @param writer the writer that writes to the Diptrace ascii file
     * @throws IOException when IO error occurs
     */
    void writeSchematics(final Writer writer)
        throws IOException {
        
        fSchematicsRoot.write(writer, "");
    }
    
    /**
     * Write a pcb file.
     * @param writer the writer that writes to the Diptrace ascii file
     * @throws IOException when IO error occurs
     */
    void writePCB(final Writer writer)
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
        
        try (BufferedWriter schematicsWriter
                = new BufferedWriter(
                    new OutputStreamWriter(
                        new FileOutputStream(schematicsFilename),
                        StandardCharsets.UTF_8));
            BufferedWriter pcbWriter
                = new BufferedWriter(
                    new OutputStreamWriter(
                        new FileOutputStream(pcbFilename),
                        StandardCharsets.UTF_8))) {
            
            writeSchematics(schematicsWriter);
            writePCB(pcbWriter);
            
        }
    }
    
    /**
     * Holds flags for schematics and pcb.
     */
    static final class SchematicsAndPCBFlags {
        
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
        boolean getSchematicsFlag() {
            return fSchematicsFlag;
        }
        
        /**
         * Get the pcb flag.
         * @return the flag
         */
        boolean getPCBFlag() {
            return fPCBFlag;
        }
        
    }
    
}
