package javadiptraceasciilib;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Diptrace shape item.
 */
abstract class DiptraceSuperShapeItem extends DiptraceGenericItem {

    /**
     * Initialize a DiptraceShapeItem.
     * @param parent the parent
     * @param identifier the identifier
     */
    DiptraceSuperShapeItem(final DiptraceItem parent, final String identifier) {
        super(parent, identifier);
    }

    /**
     * Duplicate this item and all its children.
     * @param parent the parent of the new item
     * @return the copy of this item
     */
    @Override
    public DiptraceItem duplicate(final DiptraceItem parent) {
        
        DiptraceShapeItem newItem =
            new DiptraceShapeItem(parent, getIdentifier());
        
        newItem.setMayHaveSubItems(this.getMayHaveSubItems());
        
        List<DiptraceAttribute> newAttributes = newItem.getAttributes();
        for (DiptraceAttribute attribute : getAttributes()) {
            newAttributes.add(attribute.duplicate());
        }
        for (DiptraceItem subItem : getChildren()) {
            newItem.addSubItem(subItem.duplicate(newItem));
        }
        return newItem;
    }
    
    /**
     * Get the drawing type of this shape.
     * @return the drawing type
     */
    abstract DrawingType getDrawingType();
    
    /**
     * Get whenether this shape is locked.
     * @return true if locked
     */
    abstract boolean getLocked();
    
    /**
     * Get the layer number of this shape.
     * @return the layer number
     */
    abstract int getLayerNo();
    
    /**
     * Get the layer type of this shape.
     * @return the drawing type
     */
    abstract PlacementLayer getPlacementLayer();
    
    /**
     * Get the points for this shape.
     * @return the points
     */
    abstract List<Point2D.Double> getPoints();
    
    /**
     * Get the name of this shape.
     * @return the name
     */
    abstract String getName();
    
    /**
     * Get the font name.
     * @return the name of the font
     */
    abstract String getFontName();
    
    /**
     * Get the font size.
     * @return the size of the font
     */
    abstract int getFontSize();
    
    /**
     * Get a string representation of this object.
     * @return a string
     */
    abstract String getString();
    
    
    
    /**
     * Type of thing to paint.
     */
    enum DrawingType {
        //CHECKSTYLE.OFF: JavadocVariable - Self explaining enums
        // At this point, I don't know which feature has which number,
        // so I give the features numbers like -901. / Daniel
        NONE(-1, -901),     // I don't know what this is, but it's in the file.
        NONE_2(0, -902),
        LINE(1, 0),
        RECTANGLE(2, 2),
        ELLIPSE(3, -905),
        FILLED_RECTANGLE(4, 3),
        FILLED_ELLIPSE(5, -907),
        ARC(6, -908),
        TEXT(7, 6),
        POLYLINE(8, -910),
        FILLED_PLYGONE(9, -911);
        //CHECKSTYLE.ON: JavadocVariable - Self explaining enums
        
        /**
         * A map of the drawing types and their attribute number.
         */
        private static final Map<Integer, DrawingType> DRAWING_ATTR_TYPE_MAP
            = new HashMap<>();
        
        /**
         * A map of the drawing types and their item number.
         */
        private static final Map<Integer, DrawingType> DRAWING_ITEM_TYPE_MAP
            = new HashMap<>();
        
        static {
            
            for (DrawingType type : EnumSet.allOf(DrawingType.class)) {
                DRAWING_ATTR_TYPE_MAP.put(type.fAttrNo, type);
                DRAWING_ITEM_TYPE_MAP.put(type.fItemNo, type);
            }
        }
        
        /**
         * Get the drawing type by the attribute number.
         * @param typeNo the type number
         * @return the drawing type
         */
        static DrawingType getTypeByAttrNo(final int typeNo) {
            return DRAWING_ATTR_TYPE_MAP.get(typeNo);
        }
        
        /**
         * Get the drawing type by the item number.
         * @param typeNo the type number
         * @return the drawing type
         */
        static DrawingType getTypeByItemNo(final int typeNo) {
            return DRAWING_ITEM_TYPE_MAP.get(typeNo);
        }
        
        /**
         * The drawing type attribute number.
         */
        private final int fAttrNo;
        
        /**
         * The drawing type item number.
         */
        private final int fItemNo;
        
        /**
         * Initialize a DrawingType object.
         * @param attrNo the attribute number
         * @param itemNo the item number
         */
        DrawingType(final int attrNo, final int itemNo) {
            fAttrNo = attrNo;
            fItemNo = itemNo;
        }
        
        /**
         * Get the attribute number.
         * @return the number
         */
        int getAttrNo() {
            return fAttrNo;
        }
        
        /**
         * Get the attribute number.
         * @return the number
         */
        int getItemNo() {
            return fItemNo;
        }
        
    }
    
    
    
    
    
    
    /**
     * The different markings.
     */
    enum MarkingType {
        //CHECKSTYLE.OFF: JavadocVariable - Self explaining enums
        TEXT(0),
        NAME(1),
        REFDES(2),
        VALUE(3);
        //CHECKSTYLE.ON: JavadocVariable - Self explaining enums
        
        /**
         * A map of the markings and their number.
         */
        private static final Map<Integer, MarkingType> MARKING_TYPE_MAP
            = new HashMap<>();
        
        static {
            
            for (MarkingType type : EnumSet.allOf(MarkingType.class)) {
                // Yes, use some appropriate locale in production code :)
                MARKING_TYPE_MAP.put(type.fTypeNo, type);
            }
        }
        
        /**
         * Get the marking type by the number.
         * @param typeNo the type number
         * @return the marking type
         */
        static MarkingType getType(final int typeNo) {
            return MARKING_TYPE_MAP.get(typeNo);
        }
        
        /**
         * The marking type number.
         */
        private final int fTypeNo;
        
        /**
         * Initialize a MarkingType object.
         * @param typeNo the type number
         */
        MarkingType(final int typeNo) {
            fTypeNo = typeNo;
        }
        
        /**
         * Get the type number.
         * @return the number
         */
        int getType() {
            return fTypeNo;
        }
        
    }
    
    /**
     * Paint this item.
     * Note that this method may change the transform for its children, and
     * therefore the caller must restore the transform after calling this
     * method on this object and this objects children.
     * @param graphics the graphics to drawPCB on
     * @param item the item to paint
     * @param layerInFocus the side that is in front of the viewer
     * @param layerToDraw the layer to paint now
     * @param sideTransparency the transparency for the other side
     */
    @Override
    void paint(
        final Graphics2D graphics,
        final int layerInFocus,
        final int layerToDraw,
        final SideTransparency sideTransparency) {
        
//        DiptraceShapeItem shapeItem = (DiptraceShapeItem) item;
        
        System.out.println("Shape: " + getString());
        
        if (getDrawingType() == DrawingType.NONE) {
            // I don't know what this is.
            return;
        }
        
        int layerNo;
        PlacementLayer placementLayer = getPlacementLayer();
        DiptracePCBNonSignalLayer pcbNonSignalLayer = null;
        
        Color fullColor;
        
        if (placementLayer == PlacementLayer.USER_NON_SIGNAL_LAYER) {
            try {
                pcbNonSignalLayer
                    = getProject().getPCBNonSignalLayer(getLayerNo());
                
                layerNo = pcbNonSignalLayer.getLayerSide();
                if (layerNo == 0) {
                    // layerNo == 0 means "no layer"
                    layerNo = layerInFocus;
                } else {
                    layerNo--;
                }
            } catch (DiptraceNotFoundException ex) {
                throw new RuntimeException(
                    String.format(
                        "The layer %d is not found",
                        getLayerNo()),
                    ex);
            }
            
            fullColor = pcbNonSignalLayer.getLayerColor();
        } else {
            switch (placementLayer.getSide()) {
                case TOP:
                    layerNo = 0;
    //                layer = getLayerNo();
                    break;
                case BOTTOM:
                    layerNo = 1;
    //                layer = getLayerNo();
                    break;
                case BOTH:
                    layerNo = layerInFocus;
                    break;
                case UNKNOWN:
                    layerNo = getLayerNo();
                    break;
                default:
                    throw new RuntimeException(
                        String.format(
                            "Side %s is unknown",
                            placementLayer.getSide().name()));
            }
            
            fullColor = LAYER_COLOR_MAP.get(placementLayer);
            
            if (fullColor == null) {
//                if (1==1)
//                    return;
                System.out.format("Shape: %s%n", getString());
                throw new RuntimeException(
                    String.format(
                        "Color for placement layer %s is unknown",
                        placementLayer.name()));
            }
        }
        
        if (layerToDraw != layerNo) {
//            System.out.format("layerNo: %d%n", layerNo);
            // We want to paint all items on the sides that are not in focus
            // before we paint all the items on the side that is in focus.
            return;
        }
        
        final int dimValue = 5;
        Color dimColor
            = new Color(
                fullColor.getRed() / dimValue,
                fullColor.getGreen() / dimValue,
                fullColor.getBlue() / dimValue);
        
        Color color;
        if (layerNo == layerInFocus) {
            color = fullColor;
        } else {
            switch (sideTransparency) {
                case NONE:
                    // Don't paint this shape at all
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
        
        graphics.setColor(color);
        
        List<Point2D.Double> points;
        
        switch (getDrawingType()) {
            case NONE:
            case NONE_2:
                // Nothing to do.
                break;
            case LINE:
                points = getPoints();
                graphics.draw(
                    new Line2D.Double(
                        points.get(0).x, points.get(0).y,
                        points.get(1).x, points.get(1).y));
                break;
            case RECTANGLE:
                points = getPoints();
                graphics.draw(new Rectangle2D.Double(
                    points.get(0).x,
                    points.get(0).y,
                    points.get(1).x - points.get(0).x,
                    points.get(1).y - points.get(0).y));
/*
                graphics.draw(
                    new Line2D.Double(
                        points.get(0).x,
                        points.get(0).y,
                        points.get(1).x,
                        points.get(1).y));
                graphics.draw(
                    new Line2D.Double(
                        points.get(0).x,
                        points.get(1).y,
                        points.get(1).x,
                        points.get(0).y));
*/
                break;
            case ELLIPSE:
//                graphics.paint(
//                    new Rectangle2D.Double(
//                        getPoint(0), getPoint(1),
//                        getPoint(2), getPoint(3)));
                break;
            case FILLED_RECTANGLE:
                points = getPoints();
                graphics.fill(new Rectangle2D.Double(
                    points.get(0).x,
                    points.get(0).y,
                    points.get(1).x - points.get(0).x,
                    points.get(1).y - points.get(0).y));
                break;
            case FILLED_ELLIPSE:
//                graphics.paint(
//                    new Rectangle2D.Double(
//                        getPoint(0), getPoint(1),
//                        getPoint(2), getPoint(3)));
                break;
            case ARC:
//                graphics.paint(
//                    new Rectangle2D.Double(
//                        getPoint(0), getPoint(1),
//                        getPoint(2), getPoint(3)));
                break;
            case TEXT:
//                if (1==1)
//                    return;
                String name = getName();
                points = getPoints();
//                System.out.format(
//                    "Font: %s, size: %d. Text: %s%n",
//                    getFontName(),
//                    getFontSize(), name);
                Font font = new Font(
                    getFontName(),
                    Font.PLAIN,
                    getFontSize());
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
//                graphics.paint(
//                    new Rectangle2D.Double(
//                        getPoint(0), getPoint(1),
//                        getPoint(2), getPoint(3)));
                break;
            case FILLED_PLYGONE:
//                graphics.paint(
//                    new Rectangle2D.Double(
//                        getPoint(0), getPoint(1),
//                        getPoint(2), getPoint(3)));
                break;
            default:
                throw new RuntimeException(
                    String.format(
                        "DrawingType %s is unknown",
                        getDrawingType().name()));
        }
    }
    //CHECKSTYLE.ON: MethodLength - Yes, this method is way to long.
    // It should be fixed.
    
}
