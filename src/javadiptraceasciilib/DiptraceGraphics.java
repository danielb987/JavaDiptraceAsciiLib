package javadiptraceasciilib;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javadiptraceasciilib.DiptraceShapeItem.PlacementLayer;

/**
 * Draw a schematics or a pcb on a Java Graphics2D object.
 * Note that there may be a slight difference between the graphics produced
 * by Diptrace and the graphics produced by this class. The content is the
 * same, but the size of some shapes may differ slight and the fonts may
 * differ.
 */
public final class DiptraceGraphics {
    
    
    /**
     * Should the other side of the PCB be visible?
     */
    public enum SideTransparency {
        
        /**
         * Don't show the opposite side.
         */
        NONE,
        
        /**
         * Show the opposite side with some transparency.
         */
        PART,
        
        /**
         * Show the other side clear.
         */
        FULL,
    }
    
    /**
     * A map with the side of the PCB for each layer.
     */
    private static final Map<PlacementLayer, DiptracePCBSide> LAYER_SIDE_MAP
        = new HashMap<>();
    
    /**
     * A map with the color of the shapes on the PCB for each layer when the
     * layer is on the viewer's point of view of the PCB.
     */
    private static final Map<PlacementLayer, Color> LAYER_COLOR_MAP
        = new HashMap<>();
    
    /**
     * A map with the color of the shapes on the PCB for each layer when the
     * layer is on the opposite side of the PCB from the viewer's point of
     * view.
     */
//    private static final Map<PlacementLayer, Color> LAYER_DIM_COLOR_MAP
//        = new HashMap<>();
    
    static {
        
        LAYER_SIDE_MAP.put(PlacementLayer.TOP_PASTE, DiptracePCBSide.TOP);
        LAYER_SIDE_MAP.put(PlacementLayer.TOP_ASSY, DiptracePCBSide.TOP);
        LAYER_SIDE_MAP.put(PlacementLayer.TOP_SILK, DiptracePCBSide.TOP);
        LAYER_SIDE_MAP.put(PlacementLayer.TOP_MASK, DiptracePCBSide.TOP);
        LAYER_SIDE_MAP.put(PlacementLayer.SIGNAL_PLANE, DiptracePCBSide.TOP);
        LAYER_SIDE_MAP.put(PlacementLayer.ROUTE_KEEPOUT, DiptracePCBSide.TOP);
        LAYER_SIDE_MAP.put(PlacementLayer.BOTTOM_PASTE, DiptracePCBSide.BOTTOM);
        LAYER_SIDE_MAP.put(PlacementLayer.BOTTOM_MASK, DiptracePCBSide.BOTTOM);
        LAYER_SIDE_MAP.put(PlacementLayer.BOTTOM_SILK, DiptracePCBSide.BOTTOM);
        LAYER_SIDE_MAP.put(PlacementLayer.BOTTOM_ASSY, DiptracePCBSide.BOTTOM);
        LAYER_SIDE_MAP.put(PlacementLayer.BOARD_CUTOUT, DiptracePCBSide.BOTH);
        LAYER_SIDE_MAP.put(PlacementLayer.PLACE_KEEPOUT, DiptracePCBSide.TOP);
        
        //CHECKSTYLE.OFF: LineLength - Long lines are bad, but these lines are
        // only a bunch of constants in a map.
        //CHECKSTYLE.OFF: MagicNumber - These numbers are constants, but
        // checkstyle doesn't look at it that way.
        LAYER_COLOR_MAP.put(PlacementLayer.TOP_PASTE, new Color(153, 132, 47));
        LAYER_COLOR_MAP.put(PlacementLayer.TOP_ASSY, new Color(138, 138, 138));
        LAYER_COLOR_MAP.put(PlacementLayer.TOP_SILK, new Color(0, 180, 0));
        LAYER_COLOR_MAP.put(PlacementLayer.TOP_MASK, new Color(46, 71, 86));
        LAYER_COLOR_MAP.put(PlacementLayer.SIGNAL_PLANE, new Color(255, 255, 170));
        LAYER_COLOR_MAP.put(PlacementLayer.ROUTE_KEEPOUT, new Color(80, 60, 60));
        LAYER_COLOR_MAP.put(PlacementLayer.BOTTOM_PASTE, new Color(153, 132, 47));
        LAYER_COLOR_MAP.put(PlacementLayer.BOTTOM_MASK, new Color(46, 71, 86));
        LAYER_COLOR_MAP.put(PlacementLayer.BOTTOM_SILK, new Color(53, 53, 255));
        LAYER_COLOR_MAP.put(PlacementLayer.BOTTOM_ASSY, new Color(138, 138, 138));
        LAYER_COLOR_MAP.put(PlacementLayer.BOARD_CUTOUT, new Color(128, 0, 188));
        LAYER_COLOR_MAP.put(PlacementLayer.PLACE_KEEPOUT, new Color(80, 80, 60));
/*
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.TOP_PASTE, new Color(31, 26, 9));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.TOP_ASSY, new Color(28, 28, 28));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.TOP_SILK, new Color(0, 36, 0));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.TOP_MASK, new Color(9, 14, 17));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.SIGNAL_PLANE, new Color(51, 51, 34));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.ROUTE_KEEPOUT, new Color(16, 12, 12));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.BOTTOM_PASTE, new Color(31, 26, 9));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.BOTTOM_MASK, new Color(9, 14, 17));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.BOTTOM_SILK, new Color(53, 53, 255));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.BOTTOM_ASSY, new Color(28, 28, 28));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.BOARD_CUTOUT, new Color(128, 0, 188));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.PLACE_KEEPOUT, new Color(16, 16, 12));
*/
        //CHECKSTYLE.ON: MagicNumber - These numbers are constants, but
        // checkstyle doesn't look at it that way.
        //CHECKSTYLE.ON: LineLength - Long lines are bad, but these lines are
        // only a bunch of constants in a map.
    }
    
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
     * Draw a shape.
     * @param graphics the graphics to drawPCB on
     * @param item the item to draw
     * @param layerInFocus the side that is in front of the viewer
     * @param layerToDraw the layer to draw now
     * @param sideTransparency the transparency for the other side
     */
    void drawShape(
        final Graphics2D graphics,
        final DiptraceItem item,
        final int layerInFocus,
        final int layerToDraw,
        final SideTransparency sideTransparency) {
        
        DiptraceShapeItem shapeItem = (DiptraceShapeItem) item;
        
        System.out.println("Shape: " + shapeItem.getString());
/*
        if ((layerInFocus != DiptracePCBSide.TOP) && (layerInFocus != DiptracePCBSide.BOTTOM)) {
            throw new IllegalArgumentException(
                String.format(
                    "Argument 'side' must be TOP or BOTTOM and not %s",
                    layerInFocus.name()));
        }
*/
        int layerNo;
        PlacementLayer placementLayer = shapeItem.getPlacementLayer();
        switch (placementLayer.getSide()) {
            case TOP:
                layerNo = 0;
//                layer = shapeItem.getLayerNo();
                break;
            case BOTTOM:
                layerNo = 1;
//                layer = shapeItem.getLayerNo();
                break;
            case BOTH:
                layerNo = layerInFocus;
                break;
            case UNKNOWN:
                layerNo = shapeItem.getLayerNo();
                break;
            default:
                throw new RuntimeException(
                    String.format(
                        "Side %s is unknown",
                        placementLayer.getSide().name()));
        }
        
//        DiptracePCBSide layerSide = LAYER_SIDE_MAP.get(placementLayer);
        
//        if (layerToDraw != layerSide) {
        if (layerToDraw != layerNo) {
            // We want to draw all items on one side before we draw all the
            // items on the other side.
            // The side that is farest away from us is drawn first.
            return;
        }
        
        Color fullColor;
        
        if (placementLayer == PlacementLayer.USER_NON_SIGNAL_LAYER) {
            try {
                fullColor
                    = fProject.getPCBNonSignalLayer(layerNo).getLayerColor();
//                    = fProject.getPCBNonSignalLayer(0).getLayerColor();
//                fullColor = fProject.getPCBLayer(layerNo).getLayerColor();
            } catch (DiptraceNotFoundException ex) {
                throw new RuntimeException(
                    String.format(
                        "The layer %d is not found",
                        layerNo,
                        ex));
            }
        } else {
            fullColor = LAYER_COLOR_MAP.get(placementLayer);
        }
        
        if (fullColor == null) {
            throw new RuntimeException(
                String.format(
                    "Color for placement layer %s is unknown",
                    placementLayer.name()));
        }
        
        Color dimColor
            = new Color(
                Math.round(fullColor.getRed()/5),
                Math.round(fullColor.getGreen()/5),
                Math.round(fullColor.getBlue()/5));
        
        Color color;
//        if (placementLayer == layerSide) {
        if (layerNo == layerInFocus) {
            color = fullColor;
        } else {
            switch (sideTransparency) {
                case NONE:
                    // Don't draw this shape at all
                    return;
                case PART:
                    color = dimColor;
                    break;
                case FULL:
                    color = fullColor;
                    break;
                default:
                    throw new RuntimeException(
                        String.format(
                            "Unknow transparency %s",
                            sideTransparency.name()));
            }
        }
        if (color == null) {
            throw new RuntimeException(
                String.format(
                    "Color is unknown for placement layer %s",
                    placementLayer.name()));
        }
        
        graphics.setColor(color);
        
        List<Point2D.Double> points;
        
        switch (shapeItem.getDrawingType()) {
            case NONE_1:
            case NONE_2:
                // Nothing to do.
                break;
            case LINE:
                points = shapeItem.getPoints();
                System.out.format(
                    "Line: %1.0f, %1.0f, %1.0f, %1.0f%n",
                    points.get(0).x, points.get(0).y,
                    points.get(1).x, points.get(1).y);
                graphics.draw(
                    new Line2D.Double(
                        points.get(0).x, points.get(0).y,
                        points.get(1).x, points.get(1).y));
                break;
            case RECTANGLE:
                points = shapeItem.getPoints();
                System.out.format(
                    "Rectangle: %1.0f, %1.0f, %1.0f, %1.0f%n",
                    points.get(0).x, points.get(0).y,
                    points.get(1).x, points.get(1).y);
                graphics.draw(new Rectangle2D.Double(
                    points.get(0).x,
                    points.get(0).y,
                    points.get(1).x - points.get(0).x,
                    points.get(1).y - points.get(0).y));
                break;
            case ELLIPSE:
//                graphics.draw(
//                    new Rectangle2D.Double(
//                        shapeItem.getPoint(0), shapeItem.getPoint(1),
//                        shapeItem.getPoint(2), shapeItem.getPoint(3)));
                break;
            case FILLED_RECTANGLE:
//                graphics.draw(
//                    new Rectangle2D.Double(
//                        shapeItem.getPoint(0), shapeItem.getPoint(1),
//                        shapeItem.getPoint(2), shapeItem.getPoint(3)));
                break;
            case FILLED_ELLIPSE:
//                graphics.draw(
//                    new Rectangle2D.Double(
//                        shapeItem.getPoint(0), shapeItem.getPoint(1),
//                        shapeItem.getPoint(2), shapeItem.getPoint(3)));
                break;
            case ARC:
//                graphics.draw(
//                    new Rectangle2D.Double(
//                        shapeItem.getPoint(0), shapeItem.getPoint(1),
//                        shapeItem.getPoint(2), shapeItem.getPoint(3)));
                break;
            case TEXT:
                String name = shapeItem.getName();
                points = shapeItem.getPoints();
                Font font = new Font(
                    shapeItem.getFontName(),
                    Font.PLAIN,
                    shapeItem.getFontSize());
                graphics.setFont(font);
                FontMetrics fontMetrics = graphics.getFontMetrics(font);
                Rectangle2D bounds
                    = fontMetrics.getStringBounds(name, graphics);
                graphics.drawString(
                    name,
                    (float) points.get(0).x,
                    (float) (points.get(0).y + bounds.getHeight()));
                break;
            case POLYLINE:
//                graphics.draw(
//                    new Rectangle2D.Double(
//                        shapeItem.getPoint(0), shapeItem.getPoint(1),
//                        shapeItem.getPoint(2), shapeItem.getPoint(3)));
                break;
            case FILLED_PLYGONE:
//                graphics.draw(
//                    new Rectangle2D.Double(
//                        shapeItem.getPoint(0), shapeItem.getPoint(1),
//                        shapeItem.getPoint(2), shapeItem.getPoint(3)));
                break;
            default:
                throw new RuntimeException(
                    String.format(
                        "DrawingType %s is unknown",
                        shapeItem.getDrawingType().name()));
        }
    }
    
    /**
     * Draw an item.
     * @param graphics the graphics to drawPCB on
     * @param item the item to draw
     * @param layerInFocus the side that is in front of the viewer
     * @param layerToDraw the layer to draw now
     * @param sideTransparency the transparency for the other side
     */
    void drawItem(
        final Graphics2D graphics,
        final DiptraceItem item,
        final int layerInFocus,
        final int layerToDraw,
        final SideTransparency sideTransparency) {
        
        if (item.getIdentifier().equals("Shape")) {
            drawShape(
                graphics, item, layerInFocus, layerToDraw, sideTransparency);
        }
        
        for (DiptraceItem subItem : item.getChildren()) {
            drawItem(
                graphics, subItem, layerInFocus, layerToDraw, sideTransparency);
        }
    }
    
    /**
     * Draw a pcb on a Java Graphics2D object.
     * @param graphics the graphics to drawPCB on
     * @param layerInFocus the side that is in front of the viewer
     * @param sideTransparency the transparency
     */
    public void drawPCB(
        final Graphics2D graphics,
        final int layerInFocus,
        final SideTransparency sideTransparency) {
        
        if (fProject == null) {
            return;
        }
/*
        DiptracePCBSide firstSide;
        DiptracePCBSide secondSide;
        
        switch (side) {
            case TOP:
                firstSide = DiptracePCBSide.BOTTOM;
                secondSide = DiptracePCBSide.TOP;
                break;
            case BOTTOM:
                firstSide = DiptracePCBSide.TOP;
                secondSide = DiptracePCBSide.BOTTOM;
                break;
            default:
                throw new IllegalArgumentException(
                    String.format(
                        "Argument 'side' must be TOP or BOTTOM and not %s",
                        side.name()));
        }
*/
        for (DiptracePCBLayer layer : fProject.getPCBLayers()) {
            
            int layerNo = layer.getLayerNo();
            if (layerInFocus != layerNo) {
                System.out.format(
                    "AA: layerInFocus: %d, layerNo: %d%n",
                    layerInFocus, layerNo);
                
                if (1 == 1) {
                drawItem(
                    graphics,
                    (DiptraceItem) fProject.getPCBRoot(),
                    layerInFocus,
                    layerNo,
                    sideTransparency);
                }
            }
        }
        
        System.out.format(
            "BB: layerInFocus: %d, layerNo: %d%n", layerInFocus, layerInFocus);
        
        // Draw the zero layer, which is the layer of things that means
        // "any layer".
/*
        if (1 == 1) {
        drawItem(
            graphics,
            (DiptraceItem) fProject.getPCBRoot(),
            layerInFocus,
            0,
            sideTransparency);
        }
*/
        
        if (1 == 1) {
        drawItem(
            graphics,
            (DiptraceItem) fProject.getPCBRoot(),
            layerInFocus,
            layerInFocus,
            sideTransparency);
        }
    }
    
}
