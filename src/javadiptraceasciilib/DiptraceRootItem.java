package javadiptraceasciilib;

import java.io.IOException;
import java.io.Writer;

/**
 * This class is the root item of the tree in the DipTrace ascii file. This
 * root is not included in the DipTrace ascii file but exists in this project
 * to simplify handling of the item tree.
 */
final class DiptraceRootItem extends DiptraceItem {
    
    
    /**
     * Initializes the DiptraceRootItem object.
     */
    DiptraceRootItem() {
        super(null, "root");
    }
    
    /**
     * Duplicate this item. This method always throws a RuntimeException since
     * the root must not be duplicated.
     * @param parent the parent of the new item
     * @return this method always throws a RuntimeException
     */
    @Override
    public DiptraceItem duplicate(final DiptraceItem parent) {
        throw new RuntimeException("The root item must not be duplicated.");
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
     * Write the item.
     * @param writer the writer that writes to the Diptrace ascii file
     * @param indent a string of spaces to indent the tree in the ascii file
     * @throws IOException when IO error occurs
     */
    @Override
    public void write(final Writer writer, final String indent)
        throws IOException {
        
        this.writeSubItems(writer, indent);
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
