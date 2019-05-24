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
        parent.getChildren().add(newItem);
        return newItem;
    }
    
    /**
     * Get the drawing type of this shape.
     * @return the drawing type
     */
    @Override
    DrawingType getDrawingType() {
        
        DiptraceGenericItem item;
        int typeNo;
        
        switch (getTreeType()) {
            case SCHEMATICS:
                try {
                    item = ((DiptraceGenericItem) getSubItem("Type"));
                    typeNo
                        = ((DiptraceDoubleAttribute) item.getAttributes().get(0))
                            .getInt();
                    return DrawingType.getTypeByAttrNo(typeNo);
                } catch (NullPointerException ex) {
                    return DrawingType.NONE;
                }
                
            case PCB:
                item = ((DiptraceGenericItem) getSubItem("ShapeType"));
                typeNo
                    = ((DiptraceDoubleAttribute) item.getAttributes().get(0))
                        .getInt();
                return DrawingType.getTypeByItemNo(typeNo);
                
            default:
                throw new RuntimeException(
                    String.format(
                        "Invalid drawing type: '%s'", getTreeType().name()));
        }
        
//        typeNo = 6;
//        System.out.format("AA: Type: %d%n", typeNo);
//        System.out.format(
//            "Type: %d, name: %s%n",
//            typeNo,
//            DrawingType.getTypeByItemNo(typeNo).name());
    }
    
    /**
     * Get whenether this shape is locked.
     * @return true if locked
     */
    @Override
    boolean getLocked() {
        
        DiptraceGenericItem item
            = ((DiptraceGenericItem) getSubItem("Locked"));
        
        String locked
            = ((DiptraceStringAttribute) item.getAttributes().get(0))
                .getString();
        
        return locked.equals("Y");
    }
    
    /**
     * Get the layer number of this shape.
     * @return the layer number
     */
    @Override
    int getLayerNo() {
        
        DiptraceGenericItem item
            = ((DiptraceGenericItem) getSubItem("Layer"));
        
        int layerNo
            = ((DiptraceDoubleAttribute) item.getAttributes().get(0))
                .getInt();
        
        return layerNo;
    }
    
    /**
     * Get the layer type of this shape.
     * @return the drawing type
     */
    @Override
    PlacementLayer getPlacementLayer() {
        
        DiptraceGenericItem item
            = ((DiptraceGenericItem) getSubItem("Type"));
        
        int layerNo
            = ((DiptraceDoubleAttribute) item.getAttributes().get(0))
                .getInt();
        
        return PlacementLayer.getTypeByItemNo(layerNo);
    }
    
    /**
     * Get the points for this shape.
     * @return the points
     */
    @Override
    public List<Point2D.Double> getPoints() {
        
        List<Point2D.Double> points = new ArrayList<>();
        
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
        
        return points;
    }
    
    @Override
    public void setPoint(int index, Point2D.Double point) {
        
        DiptraceGenericItem item
            = ((DiptraceGenericItem) (getSubItem("Points")).getChildren().get(index));

        ((DiptraceDoubleAttribute) item.getAttributes()
            .get(0))
                .setDouble(point.x);
        ((DiptraceDoubleAttribute) item.getAttributes()
            .get(1))
                .setDouble(point.y);
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
        
        DiptraceGenericItem item
            = ((DiptraceGenericItem) getSubItem("FontSize"));
        
        int fontSize
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
