package javadiptraceasciilib;

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
        for (DiptraceItem subItem : getSubItems()) {
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
                = ((DiptraceGenericItem) getSubItem("Layer"));
            typeNo
                = ((DiptraceIntegerAttribute) item.getAttributes().get(0))
                    .getInt();
        } else {
            typeNo
                = ((DiptraceIntegerAttribute) getAttributes().get(0)).getInt();
        }
        return DrawingType.getTypeByNo(typeNo);
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
                = ((DiptraceGenericItem) getSubItem("Layer"));
            layerNo
                = ((DiptraceIntegerAttribute) item.getAttributes().get(0))
                    .getInt();
            return PlacementLayer.getTypeByItemNo(layerNo);
        } else {
            layerNo
                = ((DiptraceIntegerAttribute) getAttributes().get(0)).getInt();
            return PlacementLayer.getTypeByAttrNo(layerNo);
        }
    }
    
    double getPoint(final int pointNo) {
        return
            ((DiptraceDoubleAttribute) this.getAttributes().get(3 + pointNo))
                .getDouble();
    }
    
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
//            if (attribute.getType() == DiptraceTokenType.STRING) {
//                sb.append("\"").append(attribute.getValue()).append("\"");
//            } else {
//                sb.append(attribute.getValue());
//            }
        }
        
        return sb.toString();
    }
    
    
    
    static enum DrawingType {
        //CHECKSTYLE.OFF: JavadocVariable - Self explaining enums
        NONE_1(-1),
        NONE_2(0),
        LINE(1),
        RECTANGLE(2),
        ELLIPSE(3),
        FILLED_RECTANGLE(4),
        FILLED_ELLIPSE(5),
        ARC(6),
        TEXT(7),
        POLYLINE(8),
        FILLED_PLYGONE(9),
        ;
        //CHECKSTYLE.ON: JavadocVariable - Self explaining enums
        
        private static final Map<Integer, DrawingType> fDrawingTypeMap
            = new HashMap<>();
        
        static
        {
            for (DrawingType type : EnumSet.allOf(DrawingType.class))
            {
                // Yes, use some appropriate locale in production code :)
                fDrawingTypeMap.put(type.fTypeNo, type);
            }
        }
        
        static DrawingType getTypeByNo(int typeNo) {
            return fDrawingTypeMap.get(typeNo);
        }
        
        private final int fTypeNo;
        
        DrawingType(final int type) {
            fTypeNo = type;
        }
        
        int getType() {
            return fTypeNo;
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
//        BOTTOM_KEEPOUT(10, ),
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
            
            return fPlacementLayerMapAttributes.get(layerItemNo);
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
