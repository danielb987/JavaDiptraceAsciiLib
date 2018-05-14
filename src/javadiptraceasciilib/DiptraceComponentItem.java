
package javadiptraceasciilib;

import java.awt.Graphics2D;
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
    DiptraceComponentItem(DiptraceItem parent, String identifier) {
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
    //CHECKSTYLE.OFF: MethodLength - Yes, this method is way to long.
    // It should be fixed.
    @Override
    void paint(
        final Graphics2D graphics,
        final int layerInFocus,
        final int layerToDraw,
        final SideTransparency sideTransparency) {
        
        
    }
    
}
