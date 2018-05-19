package javadiptraceasciilib;

/**
 * The 'Shapes' item in the PCB ascii file when part of a 'Component'.
 */
class DiptraceComponentShapesCollectionItem extends DiptraceGenericItem {
    
    /**
     * Initialize a DiptraceComponentShapesCollectionItem object.
     * @param parent the parent
     * @param identifier the identifier
     */
    DiptraceComponentShapesCollectionItem(
        final DiptraceItem parent,
        final String identifier) {
        
        super(parent, identifier);
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
