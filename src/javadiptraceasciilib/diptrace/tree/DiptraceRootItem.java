package javadiptraceasciilib.diptrace.tree;

import javadiptraceasciilib.diptrace.tokenizer.DiptraceTokenizer;
import java.io.IOException;

/**
 *
 */
public class DiptraceRootItem extends DiptraceItem {
    
    public DiptraceRootItem() {
        super("root");
    }
    
    /**
     * Parse the project
     * @param tokenizer the tokenizer that parses the Diptrace ascii file
     * @throws IOException when IO error occurs
     */
    @Override
    public void parse(final DiptraceTokenizer tokenizer) throws IOException {
        parseSubItems(tokenizer);
        
//        tokenizer.eatToken(DiptraceTokenType.RIGHT_PARENTHESES);
    }
    
    @Override
    public String toString() {
        return getIdentifier();
    }
    
}
