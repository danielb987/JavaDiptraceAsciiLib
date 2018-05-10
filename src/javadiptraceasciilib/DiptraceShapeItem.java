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
            System.out.format("Type: %d, name: %s%n", typeNo, DrawingType.getItemTypeByNo(typeNo).name());
            return DrawingType.getItemTypeByNo(typeNo);
        } else {
            typeNo
                = ((DiptraceDoubleAttribute) getAttributes().get(0)).getInt();
            return DrawingType.getAttrTypeByNo(typeNo);
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
                = ((DiptraceStringAttribute) item.getAttributes().get(0)).getString();
        } else {
            locked
                = ((DiptraceStringAttribute) getAttributes().get(1)).getString();
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
                DiptraceGenericItem subGenericItem = (DiptraceGenericItem) subItem;
                // Attributet kan ibland vara en double och ibland vara en integer !!!!!!!!!!!!!!!0
                double posX
                    = ((DiptraceDoubleAttribute) subGenericItem.getAttributes().get(0))
                        .getDouble();
                double posY
                    = ((DiptraceDoubleAttribute) subGenericItem.getAttributes().get(1))
                        .getDouble();
                points.add(new Point2D.Double(posX, posY));
//                points.add(new Point2D.Double(10.3, 32.12));
            }
//            layerNo
//                = ((DiptraceDoubleAttribute) item.getAttributes().get(0))
//                    .getInt();
        } else {
            for (int i = 0; i < 3; i++) {
                points.add(new Point2D.Double(10.3, 32.12));
//                points.add(
//                    new Point2D.Double((DiptraceDoubleAttribute) getAttributes().get(3 + i)).getDouble(), ((DiptraceDoubleAttribute) getAttributes().get(3 + i + 1)).getDouble());
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
//            if (attribute.getAttrType() == DiptraceTokenType.STRING) {
//                sb.append("\"").append(attribute.getValue()).append("\"");
//            } else {
//                sb.append(attribute.getValue());
//            }
        }
        
        return sb.toString();
    }
    
    
    
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
        
        private static final Map<Integer, DrawingType> fDrawingAttrTypeMap
            = new HashMap<>();
        
        private static final Map<Integer, DrawingType> fDrawingItemTypeMap
            = new HashMap<>();
        
        static
        {
            for (DrawingType type : EnumSet.allOf(DrawingType.class))
            {
                fDrawingAttrTypeMap.put(type.fAttrTypeNo, type);
                fDrawingItemTypeMap.put(type.fItemTypeNo, type);
            }
        }
        
        static DrawingType getAttrTypeByNo(int typeNo) {
            return fDrawingAttrTypeMap.get(typeNo);
        }
        
        static DrawingType getItemTypeByNo(int typeNo) {
            return fDrawingItemTypeMap.get(typeNo);
        }
        
        private final int fAttrTypeNo;
        private final int fItemTypeNo;
        
        DrawingType(final int attrType, final int itemType) {
            fAttrTypeNo = attrType;
            fItemTypeNo = itemType;
        }
        
        int getAttrType() {
            return fAttrTypeNo;
        }
        
        int getItemType() {
            return fItemTypeNo;
        }
        
    }
    
    
    
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
        
        private static final Map<Integer, PlacementLayer>
            fPlacementLayerMapAttributes = new HashMap<>();
        
        private static final Map<Integer, PlacementLayer>
            fPlacementLayerMapItems = new HashMap<>();
        
        static
        {
            for (PlacementLayer layerNo : EnumSet.allOf(PlacementLayer.class))
            {
                fPlacementLayerMapAttributes.put(layerNo.fTypeAttrNo, layerNo);
                fPlacementLayerMapItems.put(layerNo.fTypeItemNo, layerNo);
            }
        }
        
        static PlacementLayer getTypeByAttrNo(final int layerAttrNo) {
            
            return fPlacementLayerMapAttributes.get(layerAttrNo);
        }
        
        static PlacementLayer getTypeByItemNo(final int layerItemNo) {
            
            return fPlacementLayerMapItems.get(layerItemNo);
        }
        
        private final int fTypeAttrNo;
        private final int fTypeItemNo;
        
        PlacementLayer(final int layerAttrNo, final int layerItemNo) {
            fTypeAttrNo = layerAttrNo;
            fTypeItemNo = layerItemNo;
        }
        
        int getLayerAttrNo() {
            return fTypeAttrNo;
        }
        
        int getLayerItemNo() {
            return fTypeItemNo;
        }
        
    }
    
    
    
    static enum MarkingType {
        //CHECKSTYLE.OFF: JavadocVariable - Self explaining enums
        TEXT(0),
        NAME(1),
        REFDES(2),
        VALUE(3),
        ;
        //CHECKSTYLE.ON: JavadocVariable - Self explaining enums
        
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
        
        static MarkingType getType(int typeNo) {
            return fMarkingTypeMap.get(typeNo);
        }
        
        private final int fTypeNo;
        
        MarkingType(final int type) {
            fTypeNo = type;
        }
        
        int getType() {
            return fTypeNo;
        }
        
    }
    
}
