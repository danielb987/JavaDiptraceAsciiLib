
package javadiptraceasciilib;

import java.awt.Color;
import java.awt.Font;
// import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 *
 */
class DiptraceComponentItem extends DiptraceGenericItem {

    /**
     * Initialize a DiptraceComponentItem.
     * @param parent the parent
     * @param identifier the identifier
     */
    DiptraceComponentItem(final DiptraceItem parent, final String identifier) {
        super(parent, identifier);
    }
    
    /**
     * Duplicate this item and all its children.
     * @param parent the parent of the new item
     * @return the copy of this item
     */
    @Override
    public DiptraceItem duplicate(final DiptraceItem parent) {
        
        DiptraceComponentItem newItem =
            new DiptraceComponentItem(parent, getIdentifier());
        
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
     * Get the X coordinate.
     * @return the x coordinate
     */
    String getRefDes() {
        
        final int refDesAttr = 1;
        return getAttributes().get(refDesAttr).getString();
    }
    
    /**
     * Get the Y coordinate.
     * @return the y coordinate
     */
    double getX() {
        
        DiptraceGenericItem item = ((DiptraceGenericItem) getSubItem("X"));
        System.out.format(
            "This: %s, %d%n", this.getIdentifier(), this.getChildren().size());
        System.out.format("Item: %s%n", item);
        return ((DiptraceDoubleAttribute) item.getAttributes().get(0))
            .getDouble();
    }
    
    /**
     * Get the Y coordinate.
     * @return the y coordinate
     */
    double getY() {
        
        DiptraceGenericItem item = ((DiptraceGenericItem) getSubItem("Y"));
        return ((DiptraceDoubleAttribute) item.getAttributes().get(0))
            .getDouble();
    }
    
    /**
     * Get the width of the component.
     * @return the width
     */
    double getWidth() {
        
        DiptraceGenericItem item = ((DiptraceGenericItem) getSubItem("Width"));
        System.out.format(
            "This: %s, %d%n", this.getIdentifier(), this.getChildren().size());
        System.out.format("Item: %s%n", item);
        return ((DiptraceDoubleAttribute) item.getAttributes().get(0))
            .getDouble();
    }
    
    /**
     * Get the height of the component.
     * @return the width
     */
    double getHeight() {
        
        DiptraceGenericItem item = ((DiptraceGenericItem) getSubItem("Height"));
        System.out.format(
            "This: %s, %d%n", this.getIdentifier(), this.getChildren().size());
        System.out.format("Item: %s%n", item);
        return ((DiptraceDoubleAttribute) item.getAttributes().get(0))
            .getDouble();
    }
    
    /**
     * Paint this item.
     * Note that this method may change the transform for its children, and
     * therefore the caller must restore the transform after calling this
     * method on this object and this objects children.
     * @param graphics the graphics to drawPCB on
     * @param layerInFocus the side that is in front of the viewer
     * @param layerToDraw the layer to paint now
     * @param sideTransparency the transparency for the other side
     */
    //CHECKSTYLE.OFF: MethodLength - Yes, this method is way to long.
    // It should be fixed.
    @Override
    void paint(
        final Graphics2D graphics,
        final int layerInFocus,
        final int layerToDraw,
        final SideTransparency sideTransparency) {
        
        graphics.translate(getX(), getY());
        
        System.out.format(
            "AAAA: Attr(0): %s%n", getAttributes().get(1).getString());
        
        double width = getWidth();
        double height = getHeight();
        double x0 = 0 - width / 2;
        double y0 = 0 - height / 2;
//        double x1 = width;
//        double y1 = height;
        
        String refDes = getRefDes();
        double refDesX = 0;
        double refDesY = 0 - height / 2;
//                System.out.format(
//                    "Font: %s, size: %d. Text: %s%n",
//                    getFontName(),
//                    getFontSize(), name);
        
        graphics.setColor(Color.red);
        
//        Font font = new Font(
//            getFontName(),
//            Font.PLAIN,
//            getFontSize());
        final int tempFontSize = 4;
        Font font = new Font(
            "Arial",
            Font.PLAIN,
            tempFontSize);
        graphics.setFont(font);
//        FontMetrics fontMetrics = graphics.getFontMetrics(font);
//        Rectangle2D bounds
//            = fontMetrics.getStringBounds(refDes, graphics);
        graphics.drawString(
            refDes,
            (float) refDesX,
            (float) (refDesY));
//            (float) (refDesY - bounds.getHeight()/2));
//            (float) (refDesY + bounds.getHeight()));
        
        graphics.draw(new Rectangle2D.Double(
            x0,
            y0,
            width,
            height));
        
        graphics.draw(
            new Line2D.Double(
                x0,
                y0,
                x0 + width,
                y0 + height));
        graphics.draw(
            new Line2D.Double(
                x0,
                y0 + height,
                x0 + width,
                y0));
    }
    
    
    /**
     * Creates and returns an instance of a class that inherits DiptraceItem.
     * It decides what type of class by the identifier of the token.
     * @param token the token
     * @return an instance of a sub class to DiptraceItem
     */
    @Override
    protected DiptraceItem createItemByIdentifier(final DiptraceToken token) {
        
        if ("Shape".equals(token.getValue())) {
            return new DiptraceComponentShapeItem(this, token.getValue());
        } else {
            return super.createItemByIdentifier(token);
        }
    }
    
    
}
