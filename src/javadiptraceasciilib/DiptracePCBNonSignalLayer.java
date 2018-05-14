package javadiptraceasciilib;

import java.awt.Color;

/**
 * A non signal layer on the PCB.
 */
public final class DiptracePCBNonSignalLayer {
    
    /**
     * The item that has this layer.
     */
    private final DiptraceItem fLayerItem;
    
    /**
     * Initialize a DiptraceLayer object.
     * @param layerItem the layer item
     */
    DiptracePCBNonSignalLayer(final DiptraceItem layerItem) {
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
        
        final int layerAttrNo = 2;
        
        DiptraceAttribute attr
            = ((DiptraceGenericItem) fLayerItem)
                .getAttributes().get(layerAttrNo);
        
        return ((DiptraceDoubleAttribute) attr).getInt();
    }
    
    /**
     * Get the side of this layer.
     * @return the side
     */
    public int getLayerSide() {
        
        final int sideAttrNo = 3;
        
        DiptraceAttribute attr
            = ((DiptraceGenericItem) fLayerItem)
                .getAttributes().get(sideAttrNo);
        
        return ((DiptraceDoubleAttribute) attr).getInt();
    }
    
    /**
     * Get the color of this layer.
     * @return the color
     */
    public Color getLayerColor() {
        
        final int colorAttrNo = 1;
        
        DiptraceAttribute attr
            = ((DiptraceGenericItem) fLayerItem)
                .getAttributes().get(colorAttrNo);
        
        int colorNo = ((DiptraceDoubleAttribute) attr).getInt();
        
        return new Color(
            (colorNo & 0x0000ff),           // Red
            (colorNo & 0x00ff00) >> 8,      // Green
            (colorNo & 0xff0000) >> 16);    // Blue
    }
    
}
