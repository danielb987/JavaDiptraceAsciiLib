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
    DiptraceShapeItem(DiptraceItem parent, String identifier) {
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
     * Get the drawing type of this shape.
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
            for (int i = 0; i < 3; i++) {
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
     * Get a string representation of this object.
     * @return a string
     */
    public String getString() {
//        StringBuilder sb = new StringBuilder(getIdentifier());
        StringBuilder sb = new StringBuilder();
        
        sb.append(getDrawingType().name());
        sb.append(" ").append(getLocked() ? 'Y' : 'N');
        sb.append(" ").append(getPlacementLayer().name());
//        sb.append(" ").append(getAttributes().get(1));
//        for (DiptraceAttribute attribute : fAttributes) {
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
        
        return sb.toString();
    }
    
    
    
    /**
     * Type of thing to draw.
     */
    static enum DrawingType {
        //CHECKSTYLE.OFF: JavadocVariable - Self explaining enums
        // At this point, I don't know which feature has which number,
        // so I give the features numbers like -901. / Daniel
        NONE_1(-1, -901),
        NONE_2(0, -902),
        LINE(1, 0),
        RECTANGLE(2, 2),
        ELLIPSE(3, -905),
        FILLED_RECTANGLE(4, -906),
        FILLED_ELLIPSE(5, -907),
        ARC(6, -908),
        TEXT(7, 6),
        POLYLINE(8, -910),
        FILLED_PLYGONE(9, -911),
        ;
        //CHECKSTYLE.ON: JavadocVariable - Self explaining enums
        
        /**
         * A map of the drawing types and their attribute number.
         */
        private static final Map<Integer, DrawingType> fDrawingAttrTypeMap
            = new HashMap<>();
        
        /**
         * A map of the drawing types and their item number.
         */
        private static final Map<Integer, DrawingType> fDrawingItemTypeMap
            = new HashMap<>();
        
        static
        {
            for (DrawingType type : EnumSet.allOf(DrawingType.class))
            {
                fDrawingAttrTypeMap.put(type.fAttrNo, type);
                fDrawingItemTypeMap.put(type.fItemNo, type);
            }
        }
        
        /**
         * Get the drawing type by the attribute number.
         */
        static DrawingType getTypeByAttrNo(int typeNo) {
            return fDrawingAttrTypeMap.get(typeNo);
        }
        
        /**
         * Get the drawing type by the item number.
         */
        static DrawingType getTypeByItemNo(int typeNo) {
            return fDrawingItemTypeMap.get(typeNo);
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
    static enum PlacementLayer {
        //CHECKSTYLE.OFF: JavadocVariable - Self explaining enums
        NO_LAYER(-1, -1),
        TOP_SILK(0, 1),
        TOP_ASSY(1, 0),
        TOP_MASK(2, 6),
        TOP_PASTE(3, 7),
        BOTTOM_PASTE(4, 8),
        BOTTOM_MASK(5, 9),
        BOTTOM_ASSY(6, 5),
        BOTTOM_SILK(7, 4),
        SIGNAL_PLANE(8, 3),
        ROUTE_KEEPOUT(9, 2),
        PLACE_KEEPOUT(10, 11),
//        BOTTOM_SIGNAL(11, ),
        BOARD_CUTOUT(12, 10),
        ;
        //CHECKSTYLE.ON: JavadocVariable - Self explaining enums
        
        /**
         * A map of the layers and their attribute number.
         */
        private static final Map<Integer, PlacementLayer>
            fPlacementLayerMapAttributes = new HashMap<>();
        
        /**
         * A map of the markings and their item number.
         */
        private static final Map<Integer, PlacementLayer>
            fPlacementLayerMapItems = new HashMap<>();
        
        static
        {
            for (PlacementLayer layerNo : EnumSet.allOf(PlacementLayer.class))
            {
                fPlacementLayerMapAttributes.put(layerNo.fAttrNo, layerNo);
                fPlacementLayerMapItems.put(layerNo.fItemNo, layerNo);
            }
        }
        
        /**
         * Get the layer by the attribute number.
         */
        static PlacementLayer getTypeByAttrNo(final int layerAttrNo) {
            
            return fPlacementLayerMapAttributes.get(layerAttrNo);
        }
        
        /**
         * Get the layer by the item number.
         */
        static PlacementLayer getTypeByItemNo(final int layerItemNo) {
            
            return fPlacementLayerMapItems.get(layerItemNo);
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
         * Initialize a PlacementLayer object.
         * @param attrNo the attribute number
         * @param itemNo the item number
         */
        PlacementLayer(final int attrNo, final int itemNo) {
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
         * Get the item number.
         * @return the number
         */
        int getItemNo() {
            return fItemNo;
        }
        
    }
    
    
    
    /**
     * The different markings.
     */
    static enum MarkingType {
        //CHECKSTYLE.OFF: JavadocVariable - Self explaining enums
        TEXT(0),
        NAME(1),
        REFDES(2),
        VALUE(3),
        ;
        //CHECKSTYLE.ON: JavadocVariable - Self explaining enums
        
        /**
         * A map of the markings and their number.
         */
        private static final Map<Integer, MarkingType> fMarkingTypeMap
            = new HashMap<>();
        
        static
        {
            for (MarkingType type : EnumSet.allOf(MarkingType.class))
            {
                // Yes, use some appropriate locale in production code :)
                fMarkingTypeMap.put(type.fTypeNo, type);
            }
        }
        
        /**
         * Get the marking type by the number.
         */
        static MarkingType getType(int typeNo) {
            return fMarkingTypeMap.get(typeNo);
        }
        
        /**
         * The marking type number.
         */
        private final int fTypeNo;
        
        /**
         * Initialize a MarkingType object.
         * @param attrNo the attribute number
         * @param itemNo the item number
         */
        MarkingType(final int type) {
            fTypeNo = type;
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
