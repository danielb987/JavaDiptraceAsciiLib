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
import java.util.ArrayList;
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
    private final DiptraceOperations fDiptraceOperations
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
     * The list of layers on the PCB.
     */
    private final List<DiptracePCBLayer> fPCBLayers = new ArrayList<>();
    
    /**
     * The list of non signal layers on the PCB.
     */
    private final List<DiptracePCBNonSignalLayer> fPCBNonSignalLayers
        = new ArrayList<>();
    
    /**
     * Constructs a DiptraceProject.
     */
    public DiptraceProject() {
    }
    
    /**
     * Get the DiptraceOperations object.
     * @return diptracePrimitiveOperations
     */
    DiptraceOperations getDiptraceOperations() {
        return fDiptraceOperations;
    }
    
    /**
     * Get the root node of the schematics.
     * @return the root node of the Diptrace schematics tree
     */
    public DiptraceTreeNode getSchematicsRoot() {
        return fSchematicsRoot;
    }
    
    /**
     * Get the root node of the schematics.
     * @return the root node of the Diptrace PCB tree
     */
    public DiptraceTreeNode getPCBRoot() {
        return fPCBRoot;
    }
    
    /**
     * Get the list of the PCB layers.
     * @return the list of the layers
     */
    public List<DiptracePCBLayer> getPCBLayers() {
        return fPCBLayers;
    }
    
    /**
     * Get the PCB layer by its number.
     * @param layerNo the layer number
     * @return the layer
     * @throws DiptraceNotFoundException if not found
     */
    public DiptracePCBLayer getPCBLayer(final int layerNo)
        throws DiptraceNotFoundException {
        
        for (DiptracePCBLayer pcbLayer : fPCBLayers) {
            if (pcbLayer.getLayerNo() == layerNo) {
                return pcbLayer;
            }
        }
        
        throw new DiptraceNotFoundException(
            String.format(
                "PCB layer %d is not found",
                layerNo));
    }
    
    /**
     * Get the list of the PCB layers.
     * @return the list of the layers
     */
    public List<DiptracePCBNonSignalLayer> getPCBNonSignalLayers() {
        return fPCBNonSignalLayers;
    }
    
    /**
     * Get the PCB layer by its number.
     * @param layerNo the layer number
     * @return the layer
     * @throws DiptraceNotFoundException if not found
     */
    public DiptracePCBNonSignalLayer getPCBNonSignalLayer(final int layerNo)
        throws DiptraceNotFoundException {
        
        for (DiptracePCBNonSignalLayer pcbLayer : fPCBNonSignalLayers) {
            if (pcbLayer.getLayerNo() == layerNo) {
                return pcbLayer;
            }
        }
        
        throw new DiptraceNotFoundException(
            String.format(
                "PCB non signal layer %d is not found",
                layerNo));
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
 children
     */
    DiptraceItem getSchematicsComponents() {
        return fSchematicsRoot.getSubItem("Schematic").getSubItem("Components");
    }
    
    /**
     * Get the components of the pcb.
     * @return the DiptraceItem that has all the components as DiptraceItem
 children
     */
    DiptraceItem getPCBComponents() {
        return fPCBRoot.getSubItem("Board").getSubItem("Components");
    }
    
    /**
     * Get the nets of the schematics.
     * @return the DiptraceItem that has all the nets as DiptraceItem
 children
     */
    DiptraceItem getSchematicsNets() {
        return fSchematicsRoot.getSubItem("Schematic").getSubItem("Nets");
    }
    
    /**
     * Get the nets of the pcb.
     * @return the DiptraceItem that has all the nets as DiptraceItem
 children
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
        for (DiptraceItem part : components.getChildren()) {
            
            DiptraceGenericItem numberItem
                = (DiptraceGenericItem) part.getSubItem("Number");
            DiptraceAttribute numberAttr = numberItem.getAttributes().get(0);
            int number = ((DiptraceDoubleAttribute) numberAttr).getInt();
            updateLastComponentNumber(number);
            DiptraceGenericItem hiddenIdentifierItem
                = (DiptraceGenericItem) part.getSubItem("HiddenId");
            int hiddenIdentifier
                = ((DiptraceDoubleAttribute)
                    hiddenIdentifierItem.getAttributes().get(0))
                    .getInt();
            updateLastComponentHiddenIdentifier(hiddenIdentifier);
            fSchematicsComponentNumberMap.put(number, part);
        }
        
        DiptraceItem nets = getSchematicsNets();
        for (DiptraceItem net : nets.getChildren()) {
            DiptraceGenericItem numberItem
                = (DiptraceGenericItem) net.getSubItem("Number");
            DiptraceAttribute numberAttr = numberItem.getAttributes().get(0);
            int number = ((DiptraceDoubleAttribute) numberAttr).getInt();
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
        for (DiptraceItem component : components.getChildren()) {
            DiptraceGenericItem numberItem
                = (DiptraceGenericItem) component.getSubItem("Number");
            DiptraceAttribute numberAttr = numberItem.getAttributes().get(0);
            int number = ((DiptraceDoubleAttribute) numberAttr).getInt();
            updateLastComponentNumber(number);
            fPCBComponentNumberMap.put(number, component);
        }
        
        DiptraceItem nets = getPCBNets();
        for (DiptraceItem net : nets.getChildren()) {
            DiptraceGenericItem numberItem
                = (DiptraceGenericItem) net.getSubItem("Number");
            DiptraceAttribute numberAttr = numberItem.getAttributes().get(0);
            int number = ((DiptraceDoubleAttribute) numberAttr).getInt();
            updateLastNetNumber(number);
            fPCBNetNumberMap.put(number, net);
        }
        
        fPCBLayers.clear();
        DiptraceItem layersItem
            = fPCBRoot.getSubItem("Board").getSubItem("Layers");
        
        for (DiptraceItem layerItem : layersItem.getChildren()) {
            fPCBLayers.add(new DiptracePCBLayer(layerItem));
        }
        
        fPCBNonSignalLayers.clear();
        layersItem
            = fPCBRoot.getSubItem("Board").getSubItem("NonSignals");
//            = fPCBRoot.getSubItem("Board").getSubItem("Layers");
        
        for (DiptraceItem layerItem : layersItem.getChildren()) {
            fPCBNonSignalLayers.add(new DiptracePCBNonSignalLayer(layerItem));
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
     * @throws DiptraceNotFoundException if component not found
     */
    public DiptraceComponent getComponentByRefDes(final String refDes)
        throws DiptraceNotFoundException {
        
        List<DiptraceItem> schematicsComponentParts
            = fDiptraceOperations.getSchematicsComponentParts(refDes);
        DiptraceItem pcbComponent
            = fDiptraceOperations.getPCBComponent(refDes);
        
        DiptraceComponent diptraceComponent
            = new DiptraceComponent(
                this, schematicsComponentParts, pcbComponent);
        
        return diptraceComponent;
    }
    
    /**
     * Get a net by name.
     * @param name the name of the net
     * @return the net
     * @throws DiptraceNotFoundException if the net is not found
     */
    public DiptraceNet getNetByName(final String name)
        throws DiptraceNotFoundException {
        
        DiptraceItem schematicsNet
            = fDiptraceOperations.getSchematicsNet(name);
        DiptraceItem pcbNet
            = fDiptraceOperations.getPCBNet(name);
        
        DiptraceNet diptraceNet = new DiptraceNet(this, schematicsNet, pcbNet);
        
        return diptraceNet;
    }
    
}
