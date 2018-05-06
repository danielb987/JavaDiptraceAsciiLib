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
     * Get a net by name.
     * @param name the name of the net
     * @return the net
     * @throws NotFoundException if the net is not found
     */
    public DiptraceNet getNetByName(final String name)
        throws NotFoundException {
        
        DiptraceItem schematicsNet
            = fDiptracePrimitiveOperations.getSchematicsNet(name);
        DiptraceItem pcbNet
            = fDiptracePrimitiveOperations.getPCBNet(name);
        
        DiptraceNet diptraceNet = new DiptraceNet(schematicsNet, pcbNet);
        
        return diptraceNet;
    }
    
    /**
     * Duplicate a component.
     * @param component the component to copy
     * @param newRefDes the RefDes that the new component is going to get
     * @return the new component
     */
    public DiptraceComponent duplicateComponent(
        final DiptraceComponent component,
        final String newRefDes) {
        
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
                = fDiptracePrimitiveOperations.duplicateDiptraceItem(
                    componentPart,
                    newComponentNumber,
                    newComponentHiddenIdentifier,
                    newRefDes);
            
            newSchematicsComponentParts.add(newSchematicsPartComponent);
        }
        
        DiptraceItem newPCBComponent
            = fDiptracePrimitiveOperations.duplicateDiptraceItem(
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
        final DiptraceComponent component, final double x, final double y) {
        
        for (DiptraceItem part : component.getSchematicsComponentParts()) {
            ((DiptraceDoubleAttribute)
                ((DiptraceGenericItem) part.getSubItem("X"))
                .getAttributes()
                .get(0))
                    .setDouble(x);
            
            ((DiptraceDoubleAttribute)
                ((DiptraceGenericItem) part.getSubItem("Y"))
                .getAttributes()
                .get(0))
                    .setDouble(y);
        }
    }
    
    /**
     * Move a component to an relative position on the schematics.
     * @param component The component to move
     * @param x the x distance
     * @param y the y distance
     */
    public void moveComponentRelativeOnSchematics(
        final DiptraceComponent component, final double x, final double y) {
        
        for (DiptraceItem part : component.getSchematicsComponentParts()) {
            DiptraceDoubleAttribute attrPosX
                = ((DiptraceDoubleAttribute)
                    ((DiptraceGenericItem) part.getSubItem("X"))
                        .getAttributes()
                        .get(0));

            attrPosX.setDouble(attrPosX.getDouble() + x);

            DiptraceDoubleAttribute attrPosY
                = ((DiptraceDoubleAttribute)
                    ((DiptraceGenericItem) part.getSubItem("Y"))
                    .getAttributes()
                    .get(0));

            attrPosY.setDouble(attrPosY.getDouble() + y);
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
        
        DiptraceItem item = component.getPCBComponent();
        
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
     * Move a component to an absolute position on the pcb.
     * @param component The component to move
     * @param x the x distance
     * @param y the y distance
     */
    public void moveComponentRelativeOnPCB(
        final DiptraceComponent component, final double x, final double y) {
        
        DiptraceItem item = component.getPCBComponent();
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
