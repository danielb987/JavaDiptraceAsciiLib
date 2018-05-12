package javadiptraceasciilib;

/**
 * A layer on the PCB.
 */
public final class DiptraceLayer {
    
    /**
     * The item that has this layer.
     */
    private final DiptraceItem fLayerItem;
    
    /**
     * Initialize a DiptraceLayer object.
     * @param layerItem the layer item
     */
    DiptraceLayer(final DiptraceItem layerItem) {
        this.fLayerItem = layerItem;
        
        for (DiptraceAttribute attr
            : ((DiptraceGenericItem) layerItem).getAttributes()) {
            
            System.out.format(
                "Name: %s, Number: %d%n", getLayerName(), getLayerNo());
        }
    }
    
    /**
     * Get the name of this layer.
     * @return the name of the layer
     */
    public String getLayerName() {
        DiptraceAttribute attr
            = ((DiptraceGenericItem) fLayerItem).getAttributes().get(0);
        
        return attr.getString();
    }
    
    /**
     * Get the number of this layer.
     * @return the number
     */
    public int getLayerNo() {
        DiptraceItem numberItem = fLayerItem.getSubItem("Number");
        
        DiptraceAttribute attr
            = ((DiptraceGenericItem) numberItem).getAttributes().get(0);
        
        return ((DiptraceDoubleAttribute) attr).getInt();
    }
    
}
