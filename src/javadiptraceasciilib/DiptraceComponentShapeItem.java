package javadiptraceasciilib;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * A Diptrace component shape item.
 */
class DiptraceComponentShapeItem extends DiptraceSuperShapeItem {

    /**
     * Initialize a DiptraceComponentShapeItem.
     * @param parent the parent
     * @param identifier the identifier
     */
    DiptraceComponentShapeItem(
        final DiptraceItem parent,
        final String identifier) {
        
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
        
        final int lockedAttrNo = 1;
        
        String locked
            = ((DiptraceStringAttribute) getAttributes().get(lockedAttrNo))
                .getString();
        
        return locked.equals("Y");
    }
    
    /**
     * Get the layer number of this shape.
     * @return the layer number
     */
    @Override
    int getLayerNo() {
        
        return ((DiptraceDoubleAttribute) getAttributes().get(0)).getInt();
    }
    
    /**
     * Get the layer type of this shape.
     * @return the drawing type
     */
    @Override
    PlacementLayer getPlacementLayer() {
        
        int layerNo
            = ((DiptraceDoubleAttribute) getAttributes().get(0)).getInt();
        
        return PlacementLayer.getTypeByAttrNo(layerNo);
    }
    
    /**
     * Get the points for this shape.
     * @return the points
     */
    @Override
    List<Point2D.Double> getPoints() {
        
        double width
            = ((DiptraceComponentItem) getParent().getParent()).getWidth();
        double height
            = ((DiptraceComponentItem) getParent().getParent()).getHeight();
        
        List<Point2D.Double> points = new ArrayList<>();
        
        if (1 == 0) {
            points.add(new Point2D.Double(-width / 2, height / 2));
            points.add(new Point2D.Double(width / 2, height / 2));
            return points;
        }
        
        final int numPointsInAttribute = 3;
        for (int i = 0; i < numPointsInAttribute; i++) {
            final int baseX = 3;
            final int baseY = 4;
            points.add(
                new Point2D.Double(
                    ((DiptraceDoubleAttribute) getAttributes()
                        .get(baseX + i * 2))
                            .getDouble() * width,
                    ((DiptraceDoubleAttribute) getAttributes()
                        .get(baseY + i * 2))
                            .getDouble() * height));
            
            System.out.format("%1.2f, %1.2f%n",
                    ((DiptraceDoubleAttribute) getAttributes()
                        .get(baseX + i * 2))
                            .getDouble(),
                    ((DiptraceDoubleAttribute) getAttributes()
                        .get(baseY + i * 2))
                            .getDouble());
        }
        
        return points;
    }
    
    /**
     * Get the name of this shape.
     * @return the name
     */
    @Override
    String getName() {
        final int nameAttrNo = 9;
        String name
            = ((DiptraceStringAttribute) getAttributes().get(nameAttrNo))
                .getString();
        return name;
    }
    
    /**
     * Get the font name.
     * @return the name of the font
     */
    @Override
    String getFontName() {
        final int fontNameAttrNo = 10;
        String fontName = ((DiptraceStringAttribute) getAttributes()
            .get(fontNameAttrNo))
                .getString();
        return fontName;
    }
    
    /**
     * Get the font size.
     * @return the size of the font
     */
    @Override
    int getFontSize() {
        final int fontSizeAttrNo = 12;
        int fontSize = ((DiptraceDoubleAttribute) getAttributes()
            .get(fontSizeAttrNo))
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
