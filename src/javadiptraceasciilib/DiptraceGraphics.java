package javadiptraceasciilib;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * Draw a schematics or a pcb on a Java Graphics2D object.
 * Note that there may be a slight difference between the graphics produced
 * by Diptrace and the graphics produced by this class. The content is the
 * same, but the size of some shapes may differ slight and the fonts may
 * differ.
 */
public final class DiptraceGraphics {
    
    
    /**
     * The Diptrace project.
     */
    private final DiptraceProject fProject;
    
    /**
     * Initialize a DiptraceGraphics object.
     * @param project the DiptraceProject
     */
    public DiptraceGraphics(final DiptraceProject project) {
        
        this.fProject = project;
    }
    
    
    /**
     * Draw an item.
     * @param graphics the graphics to drawPCB on
     * @param item the item to paint
     * @param layerInFocus the side that is in front of the viewer
     * @param layerToDraw the layer to paint now
     * @param sideTransparency the transparency for the other side
     */
    void drawSchematicsItem(
        final Graphics2D graphics,
        final DiptraceItem item) {
        
        AffineTransform oldTransform = graphics.getTransform();
        
//        if (! item.getClass().equals(DiptraceGenericItem.class))
//            System.out.format("Item: %s%n", item.getClass().getName());
        
        item.paint(graphics);
        
        for (DiptraceItem subItem : item.getChildren()) {
            drawSchematicsItem(graphics, subItem);
        }
        
        graphics.setTransform(oldTransform);
    }
    
    
    /**
     * Draw the schematics on a Java Graphics2D object.
     * @param graphics the graphics to draw the schematics on
     */
    public void drawSchematics(
        final Graphics2D graphics) {
        
        // Not yet implemented.
		throw new UnsupportedOperationException("Not supported yet.");
        
//        drawSchematicsItem(
//            graphics,
//            (DiptraceItem) fProject.getSchematicsRoot());
    }
    
    
    /**
     * Draw an item.
     * @param graphics the graphics to drawPCB on
     * @param item the item to paint
     * @param layerInFocus the side that is in front of the viewer
     * @param layerToDraw the layer to paint now
     * @param sideTransparency the transparency for the other side
     */
    void drawPCBItem(
        final Graphics2D graphics,
        final DiptraceItem item,
        final int layerInFocus,
        final int layerToDraw,
        final SideTransparency sideTransparency) {
        
        AffineTransform oldTransform = graphics.getTransform();
        
//        if (! item.getClass().equals(DiptraceGenericItem.class))
//            System.out.format("Item: %s%n", item.getClass().getName());
        
        item.paint(graphics, layerInFocus, layerToDraw, sideTransparency);
/*
        if (item.getIdentifier().equals("Shape")) {
            drawShape(
                graphics, item, layerInFocus, layerToDraw, sideTransparency);
        }
*/
        
        for (DiptraceItem subItem : item.getChildren()) {
            drawPCBItem(
                graphics, subItem, layerInFocus, layerToDraw, sideTransparency);
        }
        
        graphics.setTransform(oldTransform);
    }
    
    
    /**
     * Draw the pcb on a Java Graphics2D object.
     * @param graphics the graphics to draw the PCB on
     * @param layerInFocus the side that is in front of the viewer
     * @param sideTransparency the transparency
     */
    public void drawPCB(
        final Graphics2D graphics,
        final int layerInFocus,
        final SideTransparency sideTransparency) {
        
        for (DiptracePCBLayer layer : fProject.getPCBLayers()) {
            
            int layerNo = layer.getLayerNo();
            if (layerInFocus != layerNo) {
                drawPCBItem(
                    graphics,
                    (DiptraceItem) fProject.getPCBRoot(),
                    layerInFocus,
                    layerNo,
                    sideTransparency);
            }
        }
        
        drawPCBItem(
            graphics,
            (DiptraceItem) fProject.getPCBRoot(),
            layerInFocus,
            layerInFocus,
            sideTransparency);
    }
    
}
