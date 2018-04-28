package javadiptraceasciilib.diptrace;

// import javadiptraceasciilib.diptrace.tokenizer.DiptraceTokenType;
import javadiptraceasciilib.diptrace.tokenizer.DiptraceTokenizer;
// import javadiptraceasciilib.diptrace.tokenizer.DiptraceToken;
import javadiptraceasciilib.diptrace.tree.DiptraceRootItem;
import java.io.IOException;

/**
 * This class is holds a diptrace project. It can have both the schematics
 * and the pcb.
 */
public final class DiptraceProject {
    
    /**
     * Root of the diptrace schematics item tree.
     */
    private final DiptraceRootItem fSchematicsRoot = new DiptraceRootItem();
    
    /**
     * Root of the diptrace pcb item tree.
     */
    private final DiptraceRootItem fPCBRoot = new DiptraceRootItem();
    
    /**
     * Constructs a DiptraceProject.
     */
    public DiptraceProject() {
    }
    
    /**
     * Get the root item of the schematics.
     * @return the root item of the Diptrace schematics item tree
     */
    public DiptraceRootItem getSchematicsRoot() {
        return fSchematicsRoot;
    }
    
    /**
     * Get the root item of the schematics.
     * @return the root item of the Diptrace schematics item tree
     */
    public DiptraceRootItem getPCBRoot() {
        return fPCBRoot;
    }
    
    /**
     * Parse a schematics file.
     * @param tokenizer the tokenizer that parses the Diptrace ascii file
     * @throws IOException when IO error occurs
     */
    public void parseSchematics(final DiptraceTokenizer tokenizer) throws IOException {
        fSchematicsRoot.parse(tokenizer);
    }
    
    /**
     * Parse a pcb file.
     * @param tokenizer the tokenizer that parses the Diptrace ascii file
     * @throws IOException when IO error occurs
     */
    public void parsePCB(final DiptraceTokenizer tokenizer) throws IOException {
        fPCBRoot.parse(tokenizer);
    }
    
}
