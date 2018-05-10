package javadiptraceasciilib;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * Draw a schematics or a pcb on a Java Graphics2D object.
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
     * Draw a shape.
     * @param graphics the graphics to drawPCB on
     * @param item the item to draw
     */
    void drawShape(final Graphics2D graphics, final DiptraceItem item) {
        
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
        
        List<Point2D.Double> points;
        
        switch (shapeItem.getDrawingType()) {
            case NONE_1:
            case NONE_2:
                // Nothing to do.
                break;
            case LINE:
                points = shapeItem.getPoints();
                graphics.setColor(Color.BLACK);
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
                graphics.setColor(Color.RED);
                points = shapeItem.getPoints();
                System.out.format(
                    "Rectangle: %1.0f, %1.0f, %1.0f, %1.0f%n",
                    points.get(0).x, points.get(0).y,
                    points.get(1).x, points.get(1).y);
                graphics.draw(new Rectangle2D.Double(
                    points.get(0).x,
                    points.get(0).y,
                    points.get(1).x-points.get(0).x,
                    points.get(1).y-points.get(0).y));
                break;
            case ELLIPSE:
                graphics.setColor(Color.BLUE);
//                graphics.draw(
//                    new Rectangle2D.Double(
//                        shapeItem.getPoint(0), shapeItem.getPoint(1),
//                        shapeItem.getPoint(2), shapeItem.getPoint(3)));
                break;
            case FILLED_RECTANGLE:
                graphics.setColor(Color.GREEN);
//                graphics.draw(
//                    new Rectangle2D.Double(
//                        shapeItem.getPoint(0), shapeItem.getPoint(1),
//                        shapeItem.getPoint(2), shapeItem.getPoint(3)));
                break;
            case FILLED_ELLIPSE:
                graphics.setColor(Color.CYAN);
//                graphics.draw(
//                    new Rectangle2D.Double(
//                        shapeItem.getPoint(0), shapeItem.getPoint(1),
//                        shapeItem.getPoint(2), shapeItem.getPoint(3)));
                break;
            case ARC:
                graphics.setColor(Color.ORANGE);
//                graphics.draw(
//                    new Rectangle2D.Double(
//                        shapeItem.getPoint(0), shapeItem.getPoint(1),
//                        shapeItem.getPoint(2), shapeItem.getPoint(3)));
                break;
            case TEXT:
                graphics.setColor(Color.PINK);
                points = shapeItem.getPoints();
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
                graphics.setColor(Color.YELLOW);
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
    void drawItem(final Graphics2D graphics, final DiptraceItem item) {
        
        if (item.getIdentifier().equals("Shape")) {
            drawShape(graphics, item);
        }
        
        for (DiptraceItem subItem : item.getChildren()) {
            drawItem(graphics, subItem);
        }
        
        
    }
    
    /**
     * Draw a schematics or pcb on a Java Graphics2D object.
     * @param graphics the graphics to drawPCB on
     */
    public void drawPCB(final Graphics2D graphics) {
        
        if (fProject == null) {
            return;
        }
        
        drawItem(graphics, fProject.getPCBRoot());
        
        if (1 == 0) {
            throw new RuntimeException("Test");
        }
//        graphics.drawOval(0, 0, 100, 100);
    }
    
}
