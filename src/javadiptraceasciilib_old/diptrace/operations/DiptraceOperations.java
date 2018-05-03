
package javadiptraceasciilib_old.diptrace.operations;

import javadiptraceasciilib_old.diptrace.DiptraceProject;
import javadiptraceasciilib_old.diptrace.exceptions.IllegalTokenValue;
import javadiptraceasciilib_old.diptrace.exceptions.NotFoundException;
import javadiptraceasciilib_old.diptrace.tokenizer.DiptraceToken;
import javadiptraceasciilib_old.diptrace.tree.DiptraceGenericItem;
import javadiptraceasciilib_old.diptrace.tree.DiptraceItem;

/**
 * Do different operations on a DiptraceProject.
 */
public class DiptraceOperations {
    
    /**
     * The project that the operations are done at.
     */
    private final DiptraceProject fProject;
    
    /**
     * Initialize a DiptraceOperations object.
     * @param project the project
     */
    public DiptraceOperations(final DiptraceProject project) {
        this.fProject = project;
    }
    
    /**
     * Get the component part in the schematics by its part name.
     * @param components the DiptraceItem with the components
     * @param partName the name of the part
     * @return the part
     * @throws NotFoundException if the component part is not found
     */
    private DiptraceItem getComponentPart(
        final DiptraceItem components,
        final String partName)
        throws NotFoundException {
        
        for (DiptraceItem part : components.getSubItems()) {
            
            DiptraceGenericItem theItem = (DiptraceGenericItem) part;
            
            if (partName.equals(theItem.getParameters().get(1).getValue())) {
                
//                DiptraceGenericItem numberItem
//                    = (DiptraceGenericItem) part.getSubItem("Number");
//                int number = numberItem.getParameters().get(0).getIntValue();
//                System.out.format("Number: %d%n", number);
                return part;
            }
        }
        
        throw new NotFoundException(
                    String.format(
                        "Component part %s is not found in schematics",
                        partName));
    }
    
    /**
     * Get the component part in the schematics by its part name.
     * @param partName the name of the part
     * @return the part
     * @throws NotFoundException if the component part is not found
     */
    public DiptraceItem getSchematicsComponentPart(final String partName)
        throws NotFoundException {
        
        return getComponentPart(fProject.getSchematicsComponents(), partName);
    }
    
    /**
     * Get the component part in the schematics by its part name.
     * @param partName the name of the part
     * @return the part
     * @throws NotFoundException if the component part is not found
     */
    public DiptraceItem getPCBComponentPart(final String partName)
        throws NotFoundException {
        
        return getComponentPart(fProject.getPCBComponents(), partName);
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
        
        newItem.getParameters().get(1).setValue(newName);
        
        ((DiptraceGenericItem) newItem.getSubItem("Number"))
            .getParameters()
            .get(0)
            .setIntValue(newNumber);
        
        ((DiptraceGenericItem) newItem.getSubItem("HiddenId"))
            .getParameters()
            .get(0)
            .setIntValue(newHiddenIdentifier);
        
        ((DiptraceGenericItem) newItem.getSubItem("HiddenId"))
            .getParameters()
            .get(0)
            .setIntValue(newNumber);
        
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
        
        ((DiptraceGenericItem) item.getSubItem("X"))
            .getParameters()
            .get(0)
            .setDoubleValue(x);
        
        ((DiptraceGenericItem) item.getSubItem("Y"))
            .getParameters()
            .get(0)
            .setDoubleValue(y);
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
        
        DiptraceToken tokenPosX
            = ((DiptraceGenericItem) item.getSubItem("X"))
                .getParameters()
                .get(0);
        
        tokenPosX.setDoubleValue(tokenPosX.getDoubleValue() + x);
        
        DiptraceToken tokenPosY
            = ((DiptraceGenericItem) item.getSubItem("Y"))
                .getParameters()
                .get(0);
        
        tokenPosY.setDoubleValue(tokenPosY.getDoubleValue() + y);
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
        
        ((DiptraceGenericItem) item.getSubItem("Angle"))
            .getParameters()
            .get(0)
            .setIntValue(angle);
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
        
        DiptraceToken tokenAngle
            = ((DiptraceGenericItem) item.getSubItem("Angle"))
                .getParameters()
                .get(0);
        
        tokenAngle.setIntValue(tokenAngle.getIntValue() + angle);
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
    
    
}
