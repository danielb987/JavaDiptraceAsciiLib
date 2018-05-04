package javadiptraceasciilib;

import java.util.ArrayList;
import java.util.List;

/**
 * Extended operations on a DiptraceProject which includes both schematics
 * and pcb.
 */
public class DiptraceOperations {
    
    /**
     * Primitive operations object.
     */
    private final DiptracePrimitiveOperations fDiptracePrimitiveOperations;
    
    /**
     * Initialize a DiptracePrimitiveOperations object.
     * @param project the project
     */
    public DiptraceOperations(final DiptraceProject project) {
        fDiptracePrimitiveOperations = new DiptracePrimitiveOperations(project);
    }
    
    /**
     * Get a component by RefDes.
     * @param refDes the RefDes of the component
     * @return the component
     * @throws NotFoundException if component not found
     */
    public DiptraceComponent getComponentByRefDes(final String refDes)
        throws NotFoundException {
        
        List<DiptraceItem> schematicsComponentPart
            = fDiptracePrimitiveOperations.getSchematicsComponentParts(refDes);
        DiptraceItem pcbComponent
            = fDiptracePrimitiveOperations.getPCBComponent(refDes);
        
        DiptraceComponent diptraceComponent
            = new DiptraceComponent(schematicsComponentPart, pcbComponent);
        
        return diptraceComponent;
    }
    
    /**
     * Duplicate a component.
     * @param component the component to copy
     * @param newRefDes the RefDes that the new component is going to get
     * @return the new component
     * @throws IllegalTokenValue if a token cannot be
     * given the desired value
     */
    public DiptraceComponent duplicateComponent(
        final DiptraceComponent component,
        final String newRefDes)
        throws IllegalTokenValue {
        
        DiptraceProject diptraceProject
            = fDiptracePrimitiveOperations.getProject();
        
        // All new components need a new unique number.
        int newComponentNumber
            = diptraceProject.getNewComponentNumber();
        
        // All new components need a new unique hidden number.
        int newComponentHiddenIdentifier
            = diptraceProject.getNewComponentHiddenIdentifier();
        
        List<DiptraceItem> newSchematicsComponentParts = new ArrayList<>();
        
        for (DiptraceItem componentPart
            : component.getSchematicsComponentParts()) {
            
            DiptraceItem newSchematicsPartComponent
                = fDiptracePrimitiveOperations.duplicateComponent(
                    componentPart,
                    newComponentNumber,
                    newComponentHiddenIdentifier,
                    newRefDes);
            
            newSchematicsComponentParts.add(newSchematicsPartComponent);
        }
        
        DiptraceItem newPCBComponent
            = fDiptracePrimitiveOperations.duplicateComponent(
                component.getPCBComponent(),
                newComponentNumber,
                newComponentHiddenIdentifier,
                newRefDes);
        
        return new DiptraceComponent(
            newSchematicsComponentParts, newPCBComponent);
    }
    
    /**
     * Move a component to an absolute position on the schematics.
     * @param component The component to move
     * @param x the x position
     * @param y the y position
     */
    public void moveComponentAbsoluteOnSchematics(
        final DiptraceComponent component, final double x, final double y) throws IllegalTokenValue {
        
        for (DiptraceItem part : component.getSchematicsComponentParts()) {
            ((DiptraceGenericItem) part.getSubItem("X"))
                .getParameters()
                .get(0)
                .setDoubleValue(x);
            
            ((DiptraceGenericItem) part.getSubItem("Y"))
                .getParameters()
                .get(0)
                .setDoubleValue(y);
        }
    }
    
    /**
     * Move a component to an relative position on the schematics.
     * @param component The component to move
     * @param x the x distance
     * @param y the y distance
     */
    public void moveComponentRelativeOnSchematics(
        final DiptraceComponent component, final double x, final double y) throws IllegalTokenValue {
        
        for (DiptraceItem part : component.getSchematicsComponentParts()) {
            DiptraceToken tokenPosX
                = ((DiptraceGenericItem) part.getSubItem("X"))
                    .getParameters()
                    .get(0);

            tokenPosX.setDoubleValue(tokenPosX.getDoubleValue() + x);

            DiptraceToken tokenPosY
                = ((DiptraceGenericItem) part.getSubItem("Y"))
                    .getParameters()
                    .get(0);

            tokenPosY.setDoubleValue(tokenPosY.getDoubleValue() + y);
        }
    }
    
    /**
     * Move a component to an absolute position on the pcb.
     * @param component The component to move
     * @param x the x position
     * @param y the y position
     */
    public void moveComponentAbsoluteOnPCB(
        final DiptraceComponent component, final double x, final double y) {
        
    }
    
    /**
     * Move a component to an absolute position on the pcb.
     * @param component The component to move
     * @param x the x distance
     * @param y the y distance
     */
    public void moveComponentRelativeOnPCB(
        final DiptraceComponent component, final double x, final double y) {
        
    }
    
/*
    public DiptraceSchematicsAndPCBItem getComponentByRefDes(String refDes)
        throws NotFoundException {
        
        DiptraceItem schematicsParts
            = getSchematicsComponentPart("D1");
        DiptraceItem schematicsPartR1
            = getSchematicsComponentPart("R1");
        
        return null;
    }
*/
}
