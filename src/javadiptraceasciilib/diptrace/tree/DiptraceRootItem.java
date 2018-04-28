package javadiptraceasciilib.diptrace.tree;

import javadiptraceasciilib.diptrace.tokenizer.DiptraceTokenizer;
import java.io.IOException;

/**
 * This class is the root item of the tree in the DipTrace ascii file. This
 * root is not included in the DipTrace ascii file but exists in this project
 * to simplify handling of the item tree.
 */
public final class DiptraceRootItem extends DiptraceItem {
    
    public DiptraceRootItem() {
        super("root");
    }
    
    /**
     * Parse the project.
     * @param tokenizer the tokenizer that parses the Diptrace ascii file
     * @throws IOException when IO error occurs
     */
    @Override
    public void parse(final DiptraceTokenizer tokenizer) throws IOException {
        parseSubItems(tokenizer);
        
//        tokenizer.eatToken(DiptraceTokenType.RIGHT_PARENTHESES);
    }
    
    /**
     * Returns a string representation of this object.
     * @return a string
     */
    @Override
    public String toString() {
        return getIdentifier();
    }
    
}
