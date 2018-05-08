package javadiptraceasciilib;

import java.util.ArrayList;
import java.util.List;

/**
 * A Diptrace component.
 */
public final class DiptraceComponent {
    
    /**
     * The DiptraceProject.
     */
    private final DiptraceProject fDiptraceProject;
    
    /**
     * A list of the schematics component parts of this component.
     */
    private final List<DiptraceItem> fSchematicsComponentParts;
    
    /**
     * The pcb component of this component.
     */
    private final DiptraceItem fPCBComponent;
    
    /**
     * Initialize a DiptraceComponent object.
     * @param diptraceProject the diptrace project
     * @param schematicsComponentPart the schematics component part
     * @param pcbComponent the pcb component
     */
    DiptraceComponent(
        final DiptraceProject diptraceProject,
        final List<DiptraceItem> schematicsComponentPart,
        final DiptraceItem pcbComponent) {
        
        this.fDiptraceProject = diptraceProject;
        this.fSchematicsComponentParts = schematicsComponentPart;
        this.fPCBComponent = pcbComponent;
    }
    
    /**
     * Get the schematics component parts.
     * @return the parts
     */
    List<DiptraceItem> getSchematicsComponentParts() {
        return fSchematicsComponentParts;
    }
    
    /**
     * Get the pcb component.
     * @return the component
     */
    DiptraceItem getPCBComponent() {
        return fPCBComponent;
    }
    
    /**
     * Duplicate a component.
     * @param newRefDes the RefDes that the new component is going to get
     * @return the new component
     * @throws DiptraceRefDesAlreadyExistsException thrown if the new refdes
     * already exists
     */
    public DiptraceComponent duplicateComponent(
        final String newRefDes) throws DiptraceRefDesAlreadyExistsException {
        
        DiptracePrimitiveOperations diptracePrimitiveOperations
            = fDiptraceProject.getDiptracePrimitiveOperations();
        
        if (diptracePrimitiveOperations.isRefDesInUse(newRefDes)) {
            throw new DiptraceRefDesAlreadyExistsException(
                String.format("The RefDes %s is already in use", newRefDes));
        }
        
        // All new components need a new unique number.
        int newComponentNumber
            = fDiptraceProject.getNewComponentNumber();
        
        // All new components need a new unique hidden number.
        int newComponentHiddenIdentifier
            = fDiptraceProject.getNewComponentHiddenIdentifier();
        
        List<DiptraceItem> newSchematicsComponentParts = new ArrayList<>();
        
        for (DiptraceItem componentPart
            : this.getSchematicsComponentParts()) {
            
            DiptraceItem newSchematicsPartComponent
                = diptracePrimitiveOperations.duplicateDiptraceItem(
                    componentPart,
                    newComponentNumber,
                    newComponentHiddenIdentifier,
                    newRefDes);
            
            newSchematicsComponentParts.add(newSchematicsPartComponent);
        }
        
        DiptraceItem newPCBComponent
            = diptracePrimitiveOperations.duplicateDiptraceItem(
                this.getPCBComponent(),
                newComponentNumber,
                newComponentHiddenIdentifier,
                newRefDes);
        
        return new DiptraceComponent(
            fDiptraceProject,
            newSchematicsComponentParts,
            newPCBComponent);
    }
    
    /**
     * Move to an absolute position on the schematics.
     * @param x the x position
     * @param y the y position
     */
    public void moveAbsoluteOnSchematics(final double x, final double y) {
        
        for (DiptraceItem part : this.getSchematicsComponentParts()) {
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
     * Move to an relative position on the schematics.
     * @param x the x distance
     * @param y the y distance
     */
    public void moveRelativeOnSchematics(final double x, final double y) {
        
        for (DiptraceItem part : this.getSchematicsComponentParts()) {
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
     * Move to an absolute position on the pcb.
     * @param x the x position
     * @param y the y position
     */
    public void moveAbsoluteOnPCB(final double x, final double y) {
        
        DiptraceItem item = this.getPCBComponent();
        
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
     * @param x the x distance
     * @param y the y distance
     */
    public void moveRelativeOnPCB(final double x, final double y) {
        
        DiptraceItem item = this.getPCBComponent();
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
    
}
