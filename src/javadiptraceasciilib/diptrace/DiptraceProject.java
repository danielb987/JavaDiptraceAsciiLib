package javadiptraceasciilib.diptrace;

// import javadiptraceasciilib.diptrace.tokenizer.DiptraceTokenType;
import javadiptraceasciilib.diptrace.tokenizer.DiptraceTokenizer;
// import javadiptraceasciilib.diptrace.tokenizer.DiptraceToken;
import javadiptraceasciilib.diptrace.tree.DiptraceRootItem;
import java.io.IOException;

/**
 * Diptrace project
 */
public final class DiptraceProject {
    
    /**
     * Root of the diptrace project
     */
    private final DiptraceRootItem fRoot = new DiptraceRootItem();
    
    /**
     * Constructs a DiptraceProject
     */
    public DiptraceProject() {
    }
    
    /**
     * Get the root item.
     * @return the root item of the Diptrace item tree
     */
    public DiptraceRootItem getRoot() {
        return fRoot;
    }
    
    /**
     * Parse the project
     * @param tokenizer the tokenizer that parses the Diptrace ascii file
     * @throws IOException when IO error occurs
     */
    public void parse(final DiptraceTokenizer tokenizer) throws IOException {
        
//        try {
//        fRoot = new DiptraceRootItem();
        fRoot.parse(tokenizer);
//        }
//        catch (Throwable e) {
//        }
        
//        fRoot.printTree("");
/*        
        try {
            DiptraceToken token;
            while ((token = tokenizer.nextToken()) != null) {
//                System.out.format("Token: %s: %s\n",
//                    token.type.name(), token.getValue());
//                System.err.format("Token: %s: %s\n",
//                    token.type.name(), token.getValue());
                
                if (token.type == DiptraceTokenType.IDENTIFIER) {
                    
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
*/        
    }
    
}
