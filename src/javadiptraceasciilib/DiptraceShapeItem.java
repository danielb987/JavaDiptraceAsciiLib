package javadiptraceasciilib;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * A Diptrace shape item.
 */
class DiptraceShapeItem extends DiptraceSuperShapeItem {

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
    @Override
    DrawingType getDrawingType() {
        
        int typeNo;
        if (getAttributes().isEmpty()) {
            DiptraceGenericItem item
                = ((DiptraceGenericItem) getSubItem("ShapeType"));
            typeNo
                = ((DiptraceDoubleAttribute) item.getAttributes().get(0))
                    .getInt();
//            typeNo = 6;
//            System.out.format("AA: Type: %d%n", typeNo);
//            System.out.format(
//                "Type: %d, name: %s%n",
//                typeNo,
//                DrawingType.getTypeByItemNo(typeNo).name());
            return DrawingType.getTypeByItemNo(typeNo);
        } else {
            typeNo
                = ((DiptraceDoubleAttribute) getAttributes().get(0)).getInt();
//            System.out.format("BB: Type: %d%n", typeNo);
            return DrawingType.getTypeByAttrNo(typeNo);
        }
    }
    
    /**
     * Get whenether this shape is locked.
     * @return true if locked
     */
    @Override
    boolean getLocked() {
        
        String locked;
        if (getAttributes().isEmpty()) {
            DiptraceGenericItem item
                = ((DiptraceGenericItem) getSubItem("Locked"));
            locked
                = ((DiptraceStringAttribute) item.getAttributes().get(0))
                    .getString();
        } else {
            final int lockedAttrNo = 1;
            locked
                = ((DiptraceStringAttribute) getAttributes().get(lockedAttrNo))
                    .getString();
        }
        return locked.equals("Y");
    }
    
    /**
     * Get the layer number of this shape.
     * @return the layer number
     */
    @Override
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
    @Override
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
    @Override
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
    
    /**
     * Get the name of this shape.
     * @return the name
     */
    @Override
    String getName() {
        DiptraceGenericItem item
            = ((DiptraceGenericItem) getSubItem("Name"));

        String name = ((DiptraceStringAttribute) item.getAttributes().get(0))
            .getString();
        return name;
    }
    
    /**
     * Get the font name.
     * @return the name of the font
     */
    @Override
    String getFontName() {
        DiptraceGenericItem item
            = ((DiptraceGenericItem) getSubItem("FontName"));
        String fontName
            = ((DiptraceStringAttribute) item.getAttributes().get(0))
                .getString();
        return fontName;
    }
    
    /**
     * Get the font size.
     * @return the size of the font
     */
    @Override
    int getFontSize() {
        int fontSize;
        DiptraceGenericItem item
            = ((DiptraceGenericItem) getSubItem("FontSize"));
        fontSize
            = ((DiptraceDoubleAttribute) item.getAttributes().get(0))
                .getInt();
        return fontSize;
    }
    
    /**
     * Get a string representation of this object.
     * @return a string
     */
    @Override
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
    
}
