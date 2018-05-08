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
import java.util.List;
import java.util.Map;

/**
 * This class is holds a diptrace project. It can have both the schematics
 * and the pcb.
 */
public final class DiptraceProject {
    
    /**
     * Primitive operations object.
     */
    private final DiptraceOperations fDiptracePrimitiveOperations
        = new DiptraceOperations(this);
    
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
    private final Map<Integer, DiptraceItem> fSchematicsComponentNumberMap
        = new HashMap<>();
    
    /**
     * Map of the components in the pcb where the key is the component's
     * number.
     */
    private final Map<Integer, DiptraceItem> fPCBComponentNumberMap
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
    private final Map<Integer, DiptraceItem> fSchematicsNetNumberMap
        = new HashMap<>();
    
    /**
     * Map of the nets in the pcb where the key is the component's
     * number.
     */
    private final Map<Integer, DiptraceItem> fPCBNetNumberMap
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
     * Get the DiptraceOperations object.
     * @return diptracePrimitiveOperations
     */
    DiptraceOperations getDiptracePrimitiveOperations() {
        return fDiptracePrimitiveOperations;
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
     * Get the component by its number in the schematics.
     * @param number the number
     * @return the DiptraceItem
     */
    DiptraceItem getSchematicsComponent(final int number) {
        return fSchematicsComponentNumberMap.get(number);
    }
    
    /**
     * Get the component by its number in the pcb.
     * @param number the number
     * @return the DiptraceItem
     */
    DiptraceItem getPCBComponent(final int number) {
        return fPCBComponentNumberMap.get(number);
    }
    
    /**
     * Get the net by its number in the schematics.
     * @param number the number
     * @return the DiptraceItem
     */
    DiptraceItem getSchematicsNet(final int number) {
        return fSchematicsNetNumberMap.get(number);
    }
    
    /**
     * Get the net by its number in the pcb.
     * @param number the number
     * @return the DiptraceItem
     */
    DiptraceItem getPCBNet(final int number) {
        return fPCBNetNumberMap.get(number);
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
            DiptraceAttribute numberAttr = numberItem.getAttributes().get(0);
            int number = ((DiptraceIntegerAttribute) numberAttr).getInt();
            updateLastComponentNumber(number);
            DiptraceGenericItem hiddenIdentifierItem
                = (DiptraceGenericItem) part.getSubItem("HiddenId");
            int hiddenIdentifier
                = ((DiptraceIntegerAttribute)
                    hiddenIdentifierItem.getAttributes().get(0))
                    .getInt();
            updateLastComponentHiddenIdentifier(hiddenIdentifier);
            fSchematicsComponentNumberMap.put(number, part);
        }
        
        DiptraceItem nets = getSchematicsNets();
        for (DiptraceItem net : nets.getSubItems()) {
            DiptraceGenericItem numberItem
                = (DiptraceGenericItem) net.getSubItem("Number");
            DiptraceAttribute numberAttr = numberItem.getAttributes().get(0);
            int number = ((DiptraceIntegerAttribute) numberAttr).getInt();
            updateLastNetNumber(number);
            fSchematicsNetNumberMap.put(number, net);
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
            DiptraceAttribute numberAttr = numberItem.getAttributes().get(0);
            int number = ((DiptraceIntegerAttribute) numberAttr).getInt();
            updateLastComponentNumber(number);
            fPCBComponentNumberMap.put(number, component);
        }
        
        DiptraceItem nets = getPCBNets();
        for (DiptraceItem net : nets.getSubItems()) {
            DiptraceGenericItem numberItem
                = (DiptraceGenericItem) net.getSubItem("Number");
            DiptraceAttribute numberAttr = numberItem.getAttributes().get(0);
            int number = ((DiptraceIntegerAttribute) numberAttr).getInt();
            updateLastNetNumber(number);
            fPCBNetNumberMap.put(number, net);
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
     * Get a component by RefDes.
     * @param refDes the RefDes of the component
     * @return the component
     * @throws NotFoundException if component not found
     */
    public DiptraceComponent getComponentByRefDes(final String refDes)
        throws NotFoundException {
        
        List<DiptraceItem> schematicsComponentParts
            = fDiptracePrimitiveOperations.getSchematicsComponentParts(refDes);
        DiptraceItem pcbComponent
            = fDiptracePrimitiveOperations.getPCBComponent(refDes);
        
        DiptraceComponent diptraceComponent
            = new DiptraceComponent(
                this, schematicsComponentParts, pcbComponent);
        
        return diptraceComponent;
    }
    
    /**
     * Get a net by name.
     * @param name the name of the net
     * @return the net
     * @throws NotFoundException if the net is not found
     */
    public DiptraceNet getNetByName(final String name)
        throws NotFoundException {
        
        DiptraceItem schematicsNet
            = fDiptracePrimitiveOperations.getSchematicsNet(name);
        DiptraceItem pcbNet
            = fDiptracePrimitiveOperations.getPCBNet(name);
        
        DiptraceNet diptraceNet = new DiptraceNet(this, schematicsNet, pcbNet);
        
        return diptraceNet;
    }
    
}
