package javadiptraceasciilib;

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
    
/*
    public DiptraceComponentPin getComponentPin(
        final DiptraceComponent component,
        final int pinNo) {
        
        DiptraceComponentPin componentPin = new DiptraceComponentPin();
        return componentPin;
    }
*/
    
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
