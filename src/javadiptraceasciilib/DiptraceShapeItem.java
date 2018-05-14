package javadiptraceasciilib;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Diptrace shape item.
 */
class DiptraceShapeItem extends DiptraceGenericItem {

    /**
     * Initialize a DiptraceShapeItem.
     * @param parent the parent
     * @param identifier the identifier
     */
    DiptraceShapeItem(final DiptraceItem parent, final String identifier) {
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
    DrawingType getDrawingType() {
        
        int typeNo;
        if (getAttributes().isEmpty()) {
            DiptraceGenericItem item
                = ((DiptraceGenericItem) getSubItem("ShapeType"));
            typeNo
                = ((DiptraceDoubleAttribute) item.getAttributes().get(0))
                    .getInt();
//            typeNo = 6;
            System.out.format("Type: %d%n", typeNo);
            System.out.format(
                "Type: %d, name: %s%n",
                typeNo,
                DrawingType.getTypeByItemNo(typeNo).name());
            return DrawingType.getTypeByItemNo(typeNo);
        } else {
            typeNo
                = ((DiptraceDoubleAttribute) getAttributes().get(0)).getInt();
            return DrawingType.getTypeByAttrNo(typeNo);
        }
    }
    
    /**
     * Get whenether this shape is locked.
     * @return true if locked
     */
    boolean getLocked() {
        
        String locked;
        if (getAttributes().isEmpty()) {
            DiptraceGenericItem item
                = ((DiptraceGenericItem) getSubItem("Locked"));
            locked
                = ((DiptraceStringAttribute) item.getAttributes().get(0))
                    .getString();
        } else {
            locked
                = ((DiptraceStringAttribute) getAttributes().get(1))
                    .getString();
        }
        return locked.equals("Y");
    }
    
    /**
     * Get the layer number of this shape.
     * @return the layer number
     */
    int getLayerNo() {
        
        int layerNo;
        
        if (getAttributes().isEmpty()) {
            DiptraceGenericItem item
                = ((DiptraceGenericItem) getSubItem("Layer"));
            layerNo
                = ((DiptraceDoubleAttribute) item.getAttributes().get(0))
                    .getInt();
        } else {
            layerNo
                = ((DiptraceDoubleAttribute) getAttributes().get(0)).getInt();
        }
        return layerNo;
    }
    
    /**
     * Get the layer type of this shape.
     * @return the drawing type
     */
    PlacementLayer getPlacementLayer() {
        
        int layerNo;
        
        if (getAttributes().isEmpty()) {
            DiptraceGenericItem item
                = ((DiptraceGenericItem) getSubItem("Type"));
            layerNo
                = ((DiptraceDoubleAttribute) item.getAttributes().get(0))
                    .getInt();
            return PlacementLayer.getTypeByItemNo(layerNo);
        } else {
            layerNo
                = ((DiptraceDoubleAttribute) getAttributes().get(0)).getInt();
            return PlacementLayer.getTypeByAttrNo(layerNo);
        }
    }
    
    /**
     * Get the points for this shape.
     * @return the points
     */
    List<Point2D.Double> getPoints() {
        
        List<Point2D.Double> points = new ArrayList<>();
        
        if (getAttributes().isEmpty()) {
            DiptraceGenericItem item
                = ((DiptraceGenericItem) getSubItem("Points"));
            
            for (DiptraceItem subItem : item.getChildren()) {
                DiptraceGenericItem subGenericItem
                    = (DiptraceGenericItem) subItem;
                double posX
                    = ((DiptraceDoubleAttribute) subGenericItem.getAttributes()
                        .get(0))
                            .getDouble();
                double posY
                    = ((DiptraceDoubleAttribute) subGenericItem.getAttributes()
                        .get(1))
                            .getDouble();
                points.add(new Point2D.Double(posX, posY));
            }
        } else {
            final int numPointsInAttribute = 3;
            for (int i = 0; i < numPointsInAttribute; i++) {
                final int baseX = 3;
                final int baseY = 4;
                points.add(
                    new Point2D.Double(
                        ((DiptraceDoubleAttribute) getAttributes()
                            .get(baseX + i))
                                .getDouble(),
                        ((DiptraceDoubleAttribute) getAttributes()
                            .get(baseY + i))
                                .getDouble()));
            }
        }
        
        return points;
    }
    
//    double getPoint(final int pointNo) {
//        return pointNo;
//        return
//            ((DiptraceDoubleAttribute) this.getAttributes().get(3 + pointNo))
//                .getDouble();
//    }
    
    /**
     * Get the name of this shape.
     * @return the name
     */
    String getName() {
        DiptraceGenericItem item = ((DiptraceGenericItem) getSubItem("Name"));
        String name
            = ((DiptraceStringAttribute) item.getAttributes().get(0))
                .getString();
        return name;
    }
    
    /**
     * Get the font name.
     * @return the name of the font
     */
    String getFontName() {
        DiptraceGenericItem item
            = ((DiptraceGenericItem) getSubItem("FontName"));
        String name
            = ((DiptraceStringAttribute) item.getAttributes().get(0))
                .getString();
        return name;
    }
    
    /**
     * Get the font size.
     * @return the size of the font
     */
    int getFontSize() {
        DiptraceGenericItem item
            = ((DiptraceGenericItem) getSubItem("FontSize"));
        int fontSize
            = ((DiptraceDoubleAttribute) item.getAttributes().get(0)).getInt();
        return fontSize;
    }
    
    /**
     * Get a string representation of this object.
     * @return a string
     */
    String getString() {
        
//        StringBuilder sb = new StringBuilder(getIdentifier());
        StringBuilder sb = new StringBuilder();
        
        String layerName;
        PlacementLayer layer = getPlacementLayer();
        if (layer != null) {
            layerName = getPlacementLayer().name();
        } else {
            layerName = "Shape has no placement layer";
        }
        
        sb.append(getDrawingType().name());
        sb.append(" ");
        if (getLocked()) {
            sb.append('Y');
        } else {
            sb.append('N');
        }
        sb.append(" ").append(layerName);
//        sb.append(" ").append(getAttributes().get(1));
//        for (DiptraceAttribute attribute : fAttributes) {
        for (Point2D.Double point : getPoints()) {
            sb.append(String.format(" (%1.0f, %1.0f)", point.x, point.y));
        }
/*
        for (int i=3; i < getAttributes().size(); i++) {
            DiptraceAttribute attribute = getAttributes().get(i);
            sb.append(" ").append(attribute.getString());
//            sb.append(" ");
//            if (attribute.getAttrNo() == DiptraceTokenType.STRING) {
//                sb.append("\"").append(attribute.getValue()).append("\"");
//            } else {
//                sb.append(attribute.getValue());
//            }
        }
*/
        return sb.toString();
    }
    
    
    
    /**
     * Type of thing to draw.
     */
    enum DrawingType {
        //CHECKSTYLE.OFF: JavadocVariable - Self explaining enums
        // At this point, I don't know which feature has which number,
        // so I give the features numbers like -901. / Daniel
        NONE_1(-1, -901),
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
     * The layers.
     */
    enum PlacementLayer {
        //CHECKSTYLE.OFF: JavadocVariable - Self explaining enums
        NO_LAYER(-1, -1, DiptracePCBSide.UNKNOWN),
        TOP_SILK(0, 1, DiptracePCBSide.TOP),
        TOP_ASSY(1, 0, DiptracePCBSide.TOP),
        TOP_MASK(2, 6, DiptracePCBSide.TOP),
        TOP_PASTE(3, 7, DiptracePCBSide.TOP),
        BOTTOM_PASTE(4, 8, DiptracePCBSide.BOTTOM),
        BOTTOM_MASK(5, 9, DiptracePCBSide.BOTTOM),
        BOTTOM_ASSY(6, 5, DiptracePCBSide.BOTTOM),
        BOTTOM_SILK(7, 4, DiptracePCBSide.BOTTOM),
        SIGNAL_PLANE(8, 3, DiptracePCBSide.UNKNOWN),
        ROUTE_KEEPOUT(9, 2, DiptracePCBSide.UNKNOWN),
        PLACE_KEEPOUT(10, 11, DiptracePCBSide.UNKNOWN),
//        BOTTOM_SIGNAL(11, ),
        BOARD_CUTOUT(12, 10, DiptracePCBSide.BOTH),
        USER_NON_SIGNAL_LAYER(-901, 15, DiptracePCBSide.UNKNOWN);
        //CHECKSTYLE.ON: JavadocVariable - Self explaining enums
        
        /**
         * A map of the layers and their attribute number.
         */
        private static final Map<Integer, PlacementLayer>
            PLACEMENT_LAYER_MAP_ATTRIBUTES = new HashMap<>();
        
        /**
         * A map of the markings and their item number.
         */
        private static final Map<Integer, PlacementLayer>
            PLACEMENT_LAYER_MAP_ITEMS = new HashMap<>();
        
        static {
            
            for (PlacementLayer layerNo : EnumSet.allOf(PlacementLayer.class)) {
                PLACEMENT_LAYER_MAP_ATTRIBUTES.put(layerNo.fAttrNo, layerNo);
                PLACEMENT_LAYER_MAP_ITEMS.put(layerNo.fItemNo, layerNo);
            }
        }
        
        /**
         * Get the layer by the attribute number.
         * @param layerAttrNo the layer number
         * @return the layer
         */
        static PlacementLayer getTypeByAttrNo(final int layerAttrNo) {
            
            return PLACEMENT_LAYER_MAP_ATTRIBUTES.get(layerAttrNo);
        }
        
        /**
         * Get the layer by the item number.
         * @param layerItemNo the layer number
         * @return the layer
         */
        static PlacementLayer getTypeByItemNo(final int layerItemNo) {
            
            return PLACEMENT_LAYER_MAP_ITEMS.get(layerItemNo);
        }
        
        /**
         * The layer attribute number.
         */
        private final int fAttrNo;
        
        /**
         * The layer item number.
         */
        private final int fItemNo;
        
        /**
         * The side of the PCB there this shape will be.
         */
        private final DiptracePCBSide fSide;
        
        /**
         * Initialize a PlacementLayer object.
         * @param attrNo the attribute number
         * @param itemNo the item number
         * @param side the side that this layer is on
         */
        PlacementLayer(
            final int attrNo,
            final int itemNo,
            final DiptracePCBSide side) {
            
            fAttrNo = attrNo;
            fItemNo = itemNo;
            fSide = side;
        }
        
        /**
         * Get the attribute number.
         * @return the number
         */
        int getAttrNo() {
            return fAttrNo;
        }
        
        /**
         * Get the item number.
         * @return the number
         */
        int getItemNo() {
            return fItemNo;
        }
        
        /**
         * Get the side of the PCB.
         * @return the side
         */
        DiptracePCBSide getSide() {
            return fSide;
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
    
}
