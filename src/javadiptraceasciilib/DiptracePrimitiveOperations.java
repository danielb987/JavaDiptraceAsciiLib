package javadiptraceasciilib;

import java.util.ArrayList;
import java.util.List;

/**
 * Do different operations on a DiptraceProject.
 */
final class DiptracePrimitiveOperations {
    
    /**
     * The project that the operations are done at.
     */
    private final DiptraceProject fProject;
    
    /**
     * Initialize a DiptraceOperations object.
     * @param project the project
     */
    DiptracePrimitiveOperations(final DiptraceProject project) {
        this.fProject = project;
    }
    
    /**
     * Get the DiptraceProject.
     * @return the project
     */
    DiptraceProject getProject() {
        return fProject;
    }
    
    /**
     * Get the component parts in the schematics by its part name.
     * @param parent the parent DiptraceItem
     * @param matchItem an object that tells if this item is a match
     * @return the part
     * @throws NotFoundException if the component part is not found
     */
    private List<DiptraceItem> getDiptraceItems(
        final DiptraceItem parent,
        final MatchDiptraceItem matchItem)
        throws NotFoundException {
        
        List<DiptraceItem> diptraceItems = new ArrayList<>();
        
        for (DiptraceItem child : parent.getSubItems()) {
            if (matchItem.match(child)) {
                diptraceItems.add(child);
            }
            
//            DiptraceGenericItem theItem = (DiptraceGenericItem) part;
            
//            if (partName.equals(theItem.getAttributes().get(1).getValue())) {
        }
        
        if (diptraceItems.isEmpty()) {
            throw new NotFoundException(String.format("No item is not found"));
        }
        
        return diptraceItems;
    }
    
    /**
     * Get the component parts in the schematics by its part name.
     * @param components the DiptraceItem with the components
     * @param partName the name of the part
     * @return the part
     * @throws NotFoundException if the component part is not found
     */
/*    private List<DiptraceItem> getComponentParts(
        final DiptraceItem components,
        final String partName)
        throws NotFoundException {
        
        List<DiptraceItem> diptraceItems = new ArrayList<>();
        
        for (DiptraceItem part : components.getSubItems()) {
            
            DiptraceGenericItem theItem = (DiptraceGenericItem) part;
            
            if (partName.equals(theItem.getAttributes().get(1).getValue())) {
                
//                DiptraceGenericItem numberItem
//                    = (DiptraceGenericItem) part.getSubItem("Number");
//                int number = numberItem.getAttributes().get(0).getIntValue();
//                System.out.format("Number: %d%n", number);
                diptraceItems.add(part);
//                return part;
            }
        }
        
        if (diptraceItems.isEmpty())
            throw new NotFoundException(
                        String.format(
                            "Component part %s is not found in schematics",
                            partName));
        
        return diptraceItems;
    }
*/
    /**
     * Get the component part in the schematics by its part name.
     * @param partName the name of the part
     * @return the part
     * @throws NotFoundException if the component part is not found
     */
    public List<DiptraceItem> getSchematicsComponentParts(final String partName)
        throws NotFoundException {
        
        return getDiptraceItems(
            fProject.getSchematicsComponents(),
            (DiptraceItem item) -> {
                DiptraceGenericItem genericItem
                    = (DiptraceGenericItem) item;
                return (partName.equals(
                    genericItem.getAttributes().get(1).getString()));
            });
    }
    
    /**
     * Get the component part in the schematics by its part name.
     * @param partName the name of the part
     * @return the part
     * @throws NotFoundException if the component part is not found
     */
    public DiptraceItem getPCBComponent(final String partName)
        throws NotFoundException {
        
//        return getComponentParts(fProject.getPCBComponents(), partName);
        List<DiptraceItem> list
            = getDiptraceItems(
                fProject.getSchematicsComponents(),
                (DiptraceItem item) -> {
                    DiptraceGenericItem genericItem
                        = (DiptraceGenericItem) item;
                    return (partName.equals(
                        genericItem.getAttributes().get(1).getString()));
                });
        
        return list.get(0);
    }
    
    /**
     * Duplicate an item with all its children and adds the new item to the
     * tree.
     * @param item the item to duplicate
     * @return the new item
     */
    public DiptraceItem duplicateItem(final DiptraceItem item) {
        DiptraceItem newItem = item.duplicate(item.getParent());
        
        item.getSubItems().add(newItem);
        
        return newItem;
    }
    
    /**
     * Duplicate an item with all its children, gives it a new number and adds
     * the new item to the tree.
     * @param item the item to duplicate
     * @param newNumber the new number for this item
     * @param newHiddenIdentifier the new hidden identifier for this item
     * @param newName the new name of this item
     * @return the new item
     * @throws IllegalTokenValue if the token cannot be updated with the
     * desired value
     */
    public DiptraceItem duplicateComponent(
        final DiptraceItem item,
        final int newNumber,
        final int newHiddenIdentifier,
        final String newName)
        throws IllegalTokenValue {
        
        DiptraceGenericItem newItem
            = (DiptraceGenericItem) item.duplicate(item.getParent());
        
        ((DiptraceStringAttribute) newItem.getAttributes().get(1))
            .setString(newName);
        
        ((DiptraceIntegerAttribute)
            ((DiptraceGenericItem) newItem.getSubItem("Number"))
                .getAttributes()
                .get(0))
                    .setInt(newNumber);
        
        ((DiptraceIntegerAttribute)
            ((DiptraceGenericItem) newItem.getSubItem("HiddenId"))
                .getAttributes()
                .get(0))
                    .setInt(newHiddenIdentifier);
        
        ((DiptraceIntegerAttribute)
            ((DiptraceGenericItem) newItem.getSubItem("HiddenId"))
                .getAttributes()
                .get(0))
                    .setInt(newNumber);
        
        item.getParent().getSubItems().add(newItem);
        
        return newItem;
    }
    
    /**
     * Move an item to an absolute position.
     * @param item the item to move
     * @param x where to move the item along the x axis
     * @param y where to move the item along the y axis
     * @throws IllegalTokenValue if the token cannot be updated with the
     * desired value
     */
    public void moveItemAbsolute(
        final DiptraceItem item,
        final double x,
        final double y)
        throws IllegalTokenValue {
        
        ((DiptraceDoubleAttribute)
            ((DiptraceGenericItem) item.getSubItem("X"))
                .getAttributes()
                .get(0))
                    .setDouble(x);
        
        ((DiptraceDoubleAttribute)
            ((DiptraceGenericItem) item.getSubItem("Y"))
                .getAttributes()
                .get(0))
                    .setDouble(y);
    }
    
    /**
     * Move an item a distance.
     * @param item the item to move
     * @param x how long distance to move the item along the x axis
     * @param y how long distance to move the item along the y axis
     * @throws IllegalTokenValue if the token cannot be updated with the
     * desired value
     */
    public void moveItemRelative(
        final DiptraceItem item,
        final double x,
        final double y)
        throws IllegalTokenValue {
        
        DiptraceDoubleAttribute attrPosX
            = ((DiptraceDoubleAttribute)
                ((DiptraceGenericItem) item.getSubItem("X"))
                    .getAttributes()
                    .get(0));
        
        attrPosX.setDouble(attrPosX.getDouble() + x);
        
        DiptraceDoubleAttribute attrPosY
            = ((DiptraceDoubleAttribute)
                ((DiptraceGenericItem) item.getSubItem("Y"))
                    .getAttributes()
                    .get(0));
        
        attrPosY.setDouble(attrPosY.getDouble() + y);
    }
    
    /**
     * Rotate an item to an absolute angle.
     * @param item the item to rotate
     * @param angle the angle in degrees
     * @throws IllegalTokenValue if the token cannot be updated with the
     * desired value
     */
    public void rotateItemAbsolute(
        final DiptraceItem item,
        final int angle)
        throws IllegalTokenValue {
        
        ((DiptraceIntegerAttribute)
            ((DiptraceGenericItem) item.getSubItem("Angle"))
                .getAttributes()
                .get(0))
                    .setInt(angle);
    }
    
    /**
     * Rotate an item some degrees.
     * @param item the item to rotate
     * @param angle the angle in degrees
     * @throws IllegalTokenValue if the token cannot be updated with the
     * desired value
     */
    public void rotateItemRelative(
        final DiptraceItem item,
        final int angle)
        throws IllegalTokenValue {
        
        DiptraceIntegerAttribute attrAngle
            = ((DiptraceIntegerAttribute)
                ((DiptraceGenericItem) item.getSubItem("Angle"))
                    .getAttributes()
                    .get(0));
        
        attrAngle.setInt(attrAngle.getInt() + angle);
    }
    
    /**
     * Duplicate a component to a lot of other components of the same type
     * in both schematics and pcb.
     * @param number the number of the component to duplicate
     * @return a map of the new components with the component number as key
     */
//    public Map<Integer, DiptraceItem> duplicateComponents(final int number) {
//        Map<Integer, DiptraceItem> newComponents = new HashMap<>();
        
//        return newComponents;
//    }
    
    
    
    
    /**
     * An interface that answers whenether the item is a match.
     */
    private interface MatchDiptraceItem {
        
        /**
         * Is this item a match?
         * @param item the item
         * @return true if it's a match
         */
        boolean match(DiptraceItem item);
    }
    
    
}
