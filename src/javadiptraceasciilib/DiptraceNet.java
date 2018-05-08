package javadiptraceasciilib;

/**
 * Holds an item from both the schematics and pcb tree.
 */
public class DiptraceNet {
    
    /**
     * The DiptraceProject.
     */
    private final DiptraceProject fDiptraceProject;
    
    /**
     * The schematics net item.
     */
    private final DiptraceItem fSchematicsNet;
    
    /**
     * The pcb net item.
     */
    private final DiptraceItem fPCBNet;
    
    /**
     * Initialize a DiptraceNet object.
     * @param diptraceProject the diptrace project
     * @param schematicsNetItem the schematics net item
     * @param pcbNetItem the pcb net item
     */
    DiptraceNet(
        final DiptraceProject diptraceProject,
        final DiptraceItem schematicsNetItem,
        final DiptraceItem pcbNetItem) {
        
        this.fDiptraceProject = diptraceProject;
        this.fSchematicsNet = schematicsNetItem;
        this.fPCBNet = pcbNetItem;
    }
    
    /**
     * Get the schematics net item.
     * @return the item
     */
    public DiptraceItem getSchematicsNet() {
        return fSchematicsNet;
    }
    
    /**
     * Get the pcb net item.
     * @return the item
     */
    public DiptraceItem getPCBNet() {
        return fPCBNet;
    }
    
    /**
     * Duplicate a net.
     * @param newName the name that the new component is going to get
     * @return the new net
     * @throws DiptraceNetNameAlreadyExistsException thrown if the new name
     * already exists
     */
    public DiptraceNet duplicateNet(final String newName)
        throws DiptraceNetNameAlreadyExistsException {
        
        DiptraceOperations diptracePrimitiveOperations
            = fDiptraceProject.getDiptracePrimitiveOperations();
        
        if (diptracePrimitiveOperations.isNetNameInUse(newName)) {
            throw new DiptraceNetNameAlreadyExistsException(
                String.format("The name %s is already in use", newName));
        }
        
        // All new components need a new unique number.
        int newNetNumber
            = fDiptraceProject.getNewNetNumber();
        
        DiptraceItem newSchematicsNet
            = diptracePrimitiveOperations.duplicateDiptraceItem(
                    this.getSchematicsNet(),
                    newNetNumber,
                    newName);
        
        DiptraceItem newPCBNet
            = diptracePrimitiveOperations.duplicateDiptraceItem(
                this.getPCBNet(),
                newNetNumber,
                newName);
        
        return new DiptraceNet(fDiptraceProject, newSchematicsNet, newPCBNet);
    }
    
}
