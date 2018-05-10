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
 */
public final class DiptraceGraphics {
    
    public enum Side {
        TOP,
        BOTTOM,
        BOTH,
    }
    
    public enum SideTransparency {
        NONE,
        PART,
        FULL,
    }
    
    private static final Map<PlacementLayer, Side> LAYER_SIDE_MAP
        = new HashMap<>();
    
    private static final Map<PlacementLayer, Color> LAYER_FULL_COLOR_MAP
        = new HashMap<>();
    
    private static final Map<PlacementLayer, Color> LAYER_DIM_COLOR_MAP
        = new HashMap<>();
    
//    private static final Map<PlacementLayer, LayerColor> LAYER_COLOR_MAP
//        = new HashMap<>();
    
    static {
        
        LAYER_SIDE_MAP.put(PlacementLayer.TOP_PASTE, Side.TOP);
        LAYER_SIDE_MAP.put(PlacementLayer.TOP_ASSY, Side.TOP);
        LAYER_SIDE_MAP.put(PlacementLayer.TOP_SILK, Side.TOP);
        LAYER_SIDE_MAP.put(PlacementLayer.TOP_MASK, Side.TOP);
        LAYER_SIDE_MAP.put(PlacementLayer.SIGNAL_PLANE, Side.TOP);
        LAYER_SIDE_MAP.put(PlacementLayer.ROUTE_KEEPOUT, Side.TOP);
        LAYER_SIDE_MAP.put(PlacementLayer.BOTTOM_PASTE, Side.BOTTOM);
        LAYER_SIDE_MAP.put(PlacementLayer.BOTTOM_MASK, Side.BOTTOM);
        LAYER_SIDE_MAP.put(PlacementLayer.BOTTOM_SILK, Side.BOTTOM);
        LAYER_SIDE_MAP.put(PlacementLayer.BOTTOM_ASSY, Side.BOTTOM);
        LAYER_SIDE_MAP.put(PlacementLayer.BOARD_CUTOUT, Side.BOTH);
        LAYER_SIDE_MAP.put(PlacementLayer.PLACE_KEEPOUT, Side.TOP);
        
        LAYER_FULL_COLOR_MAP.put(PlacementLayer.TOP_PASTE, new Color(153, 132, 47));
        LAYER_FULL_COLOR_MAP.put(PlacementLayer.TOP_ASSY, new Color(138, 138, 138));
        LAYER_FULL_COLOR_MAP.put(PlacementLayer.TOP_SILK, new Color(0, 180, 0));
        LAYER_FULL_COLOR_MAP.put(PlacementLayer.TOP_MASK, new Color(46, 71, 86));
        LAYER_FULL_COLOR_MAP.put(PlacementLayer.SIGNAL_PLANE, new Color(255, 255, 170));
        LAYER_FULL_COLOR_MAP.put(PlacementLayer.ROUTE_KEEPOUT, new Color(80, 60, 60));
        LAYER_FULL_COLOR_MAP.put(PlacementLayer.BOTTOM_PASTE, new Color(153, 132, 47));
        LAYER_FULL_COLOR_MAP.put(PlacementLayer.BOTTOM_MASK, new Color(46, 71, 86));
        LAYER_FULL_COLOR_MAP.put(PlacementLayer.BOTTOM_SILK, new Color(53, 53, 255));
        LAYER_FULL_COLOR_MAP.put(PlacementLayer.BOTTOM_ASSY, new Color(138, 138, 138));
        LAYER_FULL_COLOR_MAP.put(PlacementLayer.BOARD_CUTOUT, new Color(128, 0, 188));
        LAYER_FULL_COLOR_MAP.put(PlacementLayer.PLACE_KEEPOUT, new Color(80, 80, 60));
        
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.TOP_PASTE, new Color(31, 26, 9));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.TOP_ASSY, new Color(28, 28, 28));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.TOP_SILK, new Color(0, 36, 0));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.TOP_MASK, new Color(9, 14, 17));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.SIGNAL_PLANE, new Color(255, 255, 170));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.ROUTE_KEEPOUT, new Color(80, 60, 60));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.BOTTOM_PASTE, new Color(31, 26, 9));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.BOTTOM_MASK, new Color(9, 14, 17));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.BOTTOM_SILK, new Color(53, 53, 255));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.BOTTOM_ASSY, new Color(28, 28, 28));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.BOARD_CUTOUT, new Color(128, 0, 188));
        LAYER_DIM_COLOR_MAP.put(PlacementLayer.PLACE_KEEPOUT, new Color(80, 80, 60));
/*
        LAYER_COLOR_MAP.put(PlacementLayer.TOP_PASTE, new LayerColor(new Color(153, 132, 47), new Color(153, 132, 47), new Color(153, 132, 47)));
        LAYER_COLOR_MAP.put(PlacementLayer.TOP_ASSY, new LayerColor(new Color(138, 138, 138), new Color(138, 138, 138), new Color(138, 138, 138)));
        LAYER_COLOR_MAP.put(PlacementLayer.TOP_SILK, new LayerColor(new Color(0, 180, 0), new Color(0, 180, 0), new Color(0, 180, 0)));
        LAYER_COLOR_MAP.put(PlacementLayer.TOP_MASK, new LayerColor(new Color(46, 71, 86), new Color(46, 71, 86), new Color(46, 71, 86)));
        LAYER_COLOR_MAP.put(PlacementLayer.SIGNAL_PLANE, new LayerColor(new Color(255, 255, 170), new Color(255, 255, 170), new Color(255, 255, 170)));
        LAYER_COLOR_MAP.put(PlacementLayer.ROUTE_KEEPOUT, new LayerColor(new Color(80, 60, 60), new Color(80, 60, 60), new Color(80, 60, 60)));
        LAYER_COLOR_MAP.put(PlacementLayer.BOTTOM_PASTE, new LayerColor(new Color(153, 132, 47), new Color(153, 132, 47), new Color(153, 132, 47)));
        LAYER_COLOR_MAP.put(PlacementLayer.BOTTOM_MASK, new LayerColor(new Color(46, 71, 86), new Color(46, 71, 86), new Color(46, 71, 86)));
        LAYER_COLOR_MAP.put(PlacementLayer.BOTTOM_SILK, new LayerColor(new Color(53, 53, 255), new Color(53, 53, 255), new Color(53, 53, 255)));
        LAYER_COLOR_MAP.put(PlacementLayer.BOTTOM_ASSY, new LayerColor(new Color(138, 138, 138), new Color(138, 138, 138), new Color(138, 138, 138)));
        LAYER_COLOR_MAP.put(PlacementLayer.BOARD_CUTOUT, new LayerColor(new Color(128, 0, 188), new Color(128, 0, 188), new Color(128, 0, 188)));
        LAYER_COLOR_MAP.put(PlacementLayer.PLACE_KEEPOUT, new LayerColor(new Color(80, 80, 60), new Color(80, 80, 60), new Color(80, 80, 60)));
*/
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
     */
    void drawShape(
        final Graphics2D graphics,
        final DiptraceItem item,
        final Side side,
        final Side sideToDraw,
        final SideTransparency sideTransparency) {
        
        DiptraceShapeItem shapeItem = (DiptraceShapeItem) item;
        
//        if (shapeItem.getAttributes().isEmpty()) {
//            System.out.println("shapeItem has no attributes");
//            return;
//        }
//        if (shapeItem.getPlacementLayer() == PlacementLayer.TOP_ASSY) {
//            return;
//        }
        
        System.out.println("Shape: " + shapeItem.getString());
        
//        DiptraceShapeItem.DrawingType t = shapeItem.getDrawingType();
/*
        switch (shapeItem.getPlacementLayer()) {
            case TOP_PASTE:
                graphics.setColor(Color.PINK);
                break;
            case TOP_ASSY:
                graphics.setColor(new Color(138, 138, 138));
                break;
            case TOP_SILK:
                graphics.setColor(Color.GREEN);
                break;
            case TOP_MASK:
                graphics.setColor(Color.PINK);
                break;
            case SIGNAL_PLANE:
                graphics.setColor(Color.PINK);
                break;
            case ROUTE_KEEPOUT:
                graphics.setColor(Color.PINK);
                break;
            case BOTTOM_PASTE:
                graphics.setColor(Color.PINK);
                break;
            case BOTTOM_MASK:
                graphics.setColor(Color.PINK);
                break;
            case BOTTOM_SILK:
                graphics.setColor(Color.PINK);
                break;
            case BOTTOM_ASSY:
                graphics.setColor(Color.PINK);
                break;
            case BOARD_CUTOUT:
                graphics.setColor(Color.PINK);
                break;
            case PLACE_KEEPOUT:
                graphics.setColor(Color.PINK);
                break;
            default:
                throw new RuntimeException(String.format("Unknow layer %s", shapeItem.getPlacementLayer()));
        }
*/
//        System.out.print(shapeItem.getPlacementLayer()+": ");
//        System.out.println(LAYER_COLOR_MAP.get(shapeItem.getPlacementLayer()));
        
        if ((side != Side.TOP) && (side != Side.BOTTOM)) {
            throw new IllegalArgumentException(
                String.format(
                    "Argument 'side' must be TOP or BOTTOM and not %s",
                    side.name()));
        }
        
        PlacementLayer layer = shapeItem.getPlacementLayer();
        
        Side layerSide = LAYER_SIDE_MAP.get(layer);
        
        if (sideToDraw != layerSide) {
            // We want to draw all items on one side before we draw all the
            // items on the other side.
            // The side that is farest away from us is drawn first.
            return;
        }
        
        Color color;
        if (side == layerSide) {
            color = LAYER_FULL_COLOR_MAP.get(layer);
        } else {
            switch (sideTransparency) {
                case NONE:
                    // Don't draw this shape at all
                    return;
                case PART:
                    color = LAYER_DIM_COLOR_MAP.get(layer);
                    break;
                case FULL:
                    color = LAYER_FULL_COLOR_MAP.get(layer);
                    break;
                default:
                    throw new RuntimeException(
                        String.format(
                            "Unknow transparency %s",
                            sideTransparency.name()));
            }
        }
        graphics.setColor(color);
/*
        LayerColor layerColor = LAYER_COLOR_MAP.get(layer);
        if (layerColor == null) {
            throw new RuntimeException(
                String.format(
                    "Layer %s has no defined color",
                    shapeItem.getPlacementLayer()));
        }
        switch (sideTransparency) {
            case NONE:
                if (side == )
                graphics.setColor(layerColor.fBothSidesColor);
                break;
            case PART:
                graphics.setColor(layerColor.fBothSidesColor);
                break;
            case FULL:
                graphics.setColor(layerColor.fBothSidesColor);
                break;
            default:
                throw new RuntimeException(
                    String.format(
                        "Unknow transparency %s",
                        sideTransparency.name()));
        }
*/
        List<Point2D.Double> points;
        
        switch (shapeItem.getDrawingType()) {
            case NONE_1:
            case NONE_2:
                // Nothing to do.
                break;
            case LINE:
                points = shapeItem.getPoints();
//                graphics.setColor(Color.BLACK);
                System.out.format(
                    "Line: %1.0f, %1.0f, %1.0f, %1.0f%n",
                    points.get(0).x, points.get(0).y,
                    points.get(1).x, points.get(1).y);
                graphics.draw(
                    new Line2D.Double(
                        points.get(0).x, points.get(0).y,
                        points.get(1).x, points.get(1).y));
//                    new Line2D.Double(
//                        shapeItem.getPoint(0), shapeItem.getPoint(1),
//                        shapeItem.getPoint(2), shapeItem.getPoint(3)));
                break;
            case RECTANGLE:
//                graphics.setColor(Color.RED);
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
//                graphics.setColor(Color.BLUE);
//                graphics.draw(
//                    new Rectangle2D.Double(
//                        shapeItem.getPoint(0), shapeItem.getPoint(1),
//                        shapeItem.getPoint(2), shapeItem.getPoint(3)));
                break;
            case FILLED_RECTANGLE:
//                graphics.setColor(Color.GREEN);
//                graphics.draw(
//                    new Rectangle2D.Double(
//                        shapeItem.getPoint(0), shapeItem.getPoint(1),
//                        shapeItem.getPoint(2), shapeItem.getPoint(3)));
                break;
            case FILLED_ELLIPSE:
//                graphics.setColor(Color.CYAN);
//                graphics.draw(
//                    new Rectangle2D.Double(
//                        shapeItem.getPoint(0), shapeItem.getPoint(1),
//                        shapeItem.getPoint(2), shapeItem.getPoint(3)));
                break;
            case ARC:
//                graphics.setColor(Color.ORANGE);
//                graphics.draw(
//                    new Rectangle2D.Double(
//                        shapeItem.getPoint(0), shapeItem.getPoint(1),
//                        shapeItem.getPoint(2), shapeItem.getPoint(3)));
                break;
            case TEXT:
                String name = shapeItem.getName();
                points = shapeItem.getPoints();
//                Font font = graphics.getFont();
//                Font font = new Font(Font.SANS_SERIF, Font.BOLD, 28);
                Font font = new Font(shapeItem.getFontName(), Font.PLAIN, shapeItem.getFontSize());
                graphics.setFont(font);
                FontMetrics fontMetrics = graphics.getFontMetrics(font);
                Rectangle2D bounds = fontMetrics.getStringBounds(name, graphics);
//                graphics.setColor(Color.PINK);
                graphics.drawString(name, (float) points.get(0).x, (float) (points.get(0).y + bounds.getHeight()));
//                points = shapeItem.getPoints();
//                graphics.draw(
//                    new Rectangle2D.Double(
//                        shapeItem.getPoint(0), shapeItem.getPoint(1),
//                        shapeItem.getPoint(2), shapeItem.getPoint(3)));
                break;
            case POLYLINE:
//                graphics.draw(
//                    new Rectangle2D.Double(
//                        shapeItem.getPoint(0), shapeItem.getPoint(1),
//                        shapeItem.getPoint(2), shapeItem.getPoint(3)));
                break;
            case FILLED_PLYGONE:
//                graphics.setColor(Color.YELLOW);
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
     */
    void drawItem(
        final Graphics2D graphics,
        final DiptraceItem item,
        final Side side,
        final Side sideToDraw,
        final SideTransparency sideTransparency) {
        
        if (item.getIdentifier().equals("Shape")) {
            drawShape(graphics, item, side, sideToDraw, sideTransparency);
        }
        
        for (DiptraceItem subItem : item.getChildren()) {
            drawItem(graphics, subItem, side, sideToDraw, sideTransparency);
        }
    }
    
    /**
     * Draw a schematics or pcb on a Java Graphics2D object.
     * @param graphics the graphics to drawPCB on
     * @param side the side to show up
     * @param sideTransparency the transparency
     */
    public void drawPCB(final Graphics2D graphics, final Side side, final SideTransparency sideTransparency) {
        
        if (fProject == null) {
            return;
        }
        
        Side firstSide;
        Side secondSide;
        
        switch (side) {
            case TOP:
                firstSide = Side.BOTTOM;
                secondSide = Side.TOP;
                break;
            case BOTTOM:
                firstSide = Side.TOP;
                secondSide = Side.BOTTOM;
                break;
            default:
                throw new IllegalArgumentException(
                    String.format(
                        "Argument 'side' must be TOP or BOTTOM and not %s",
                        side.name()));
        }
        
        drawItem(
            graphics,
            (DiptraceItem) fProject.getPCBRoot(),
            side,
            firstSide,
            sideTransparency);
        
        drawItem(
            graphics,
            (DiptraceItem) fProject.getPCBRoot(),
            side,
            secondSide,
            sideTransparency);
    }
    
}
