package javadiptraceasciilib;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a generic class for items in the DipTrace ascii file.
 */
public class DiptraceGenericItem extends DiptraceItem {
    
    /**
     * The list of parameters.
     */
    private final List<DiptraceAttribute> fAttributes = new ArrayList<>();
    
    
    /**
     * Initializes a DiptraceGenericItem with an identifier.
     * @param parent the parent
     * @param identifier the identifier
     */
    public DiptraceGenericItem(
        final DiptraceItem parent,
        final String identifier) {
        
        super(parent, identifier);
    }
    
    /**
     * Duplicate this item and all its children.
     * @param parent the parent of the new item
     * @return the copy of this item
     */
    @Override
    public DiptraceItem duplicate(final DiptraceItem parent) {
        DiptraceGenericItem newItem =
            new DiptraceGenericItem(parent, getIdentifier());
        
        newItem.setMayHaveSubItems(this.getMayHaveSubItems());
        
        for (DiptraceAttribute attribute : fAttributes) {
            newItem.fAttributes.add(attribute.duplicate());
        }
        for (DiptraceItem subItem : getSubItems()) {
            newItem.addSubItem(subItem.duplicate(newItem));
        }
        return newItem;
    }
    
    /**
     * Returns the list of parameters.
     * @return the parameters
     */
    public final List<DiptraceAttribute> getAttributes() {
        return fAttributes;
    }
    
    /**
     * Parse the project.
     * @param tokenizer the tokenizer that parses the Diptrace ascii file
     * @throws IOException when IO error occurs
     */
    //CHECKSTYLE.OFF: InnerAssignment - Allow assignment in while loop
    @Override
    void parse(final DiptraceTokenizer tokenizer) throws IOException {
        
        DiptraceToken token;
        while (((token = tokenizer.previewNextToken()) != null)
            && (token.getType() != DiptraceTokenType.LEFT_PARENTHESES)
            && (token.getType() != DiptraceTokenType.RIGHT_PARENTHESES)) {
            
            token = tokenizer.nextToken();
            
            switch (token.getType()) {
                case IDENTIFIER:
                case NON_QUOTED_STRING:
                    fAttributes.add(new DiptraceStringAttribute(token.getValue(), false));
                    break;
                    
                case STRING:
                    fAttributes.add(new DiptraceStringAttribute(token.getValue(), true));
                    break;
                    
                case INTEGER:
                    fAttributes.add(new DiptraceStringAttribute(token.getValue(), true));
                    break;
                    
                case DOUBLE:
                    fAttributes.add(new DiptraceStringAttribute(token.getValue(), true));
                    break;
                    
                case PERCENT:
                    fAttributes.add(new DiptraceStringAttribute(token.getValue(), true));
                    break;
                    
//                    fAttributes.add(token);
            }
        }
        
        if ((token != null)
            && (token.getType() == DiptraceTokenType.LEFT_PARENTHESES)) {
            
            setMayHaveSubItems(true);
            parseSubItems(tokenizer);
        } else {
            
            if (tokenizer.previewNextToken().getType()
                == DiptraceTokenType.RIGHT_PARENTHESES) {
                
                setMayHaveSubItems(
                    tokenizer.previewNextToken().getPrecededWithNewline());
            }
        }
    }
    //CHECKSTYLE.ON: InnerAssignment - Allow assignment in while loop
    
    /**
     * Write the item.
     * @param writer the writer that writes to the Diptrace ascii file
     * @param indent a string of spaces to indent the tree in the ascii file
     * @throws IOException when IO error occurs
     */
    @Override
    public void write(final Writer writer, final String indent)
        throws IOException {
        
        writer.append(indent).append("(").append(getIdentifier());
        
        for (DiptraceAttribute attribute : fAttributes) {
            writer.append(" ").append(attribute.getString());
//            if (attribute.getType() == DiptraceTokenType.STRING) {
//                writer.append(" \"").append(attribute.getValue()).append("\"");
//            } else {
//                writer.append(" ").append(attribute.getValue());
//            }
        }
        
        if (writeSubItems(writer, indent, IsTopLevel.SUB_LEVEL)) {
            writer.append(indent);
        } else {
            if (getMayHaveSubItems()) {
                writer.append(System.lineSeparator()).append(indent);
            }
        }
        
        writer.append(")");
        writer.append(System.lineSeparator());
    }
    
    
    /**
     * Returns a string representation of this object.
     * @return a string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getIdentifier());
        
        for (DiptraceAttribute attribute : fAttributes) {
            sb.append(" ").append(attribute.getString());
//            sb.append(" ");
//            if (attribute.getType() == DiptraceTokenType.STRING) {
//                sb.append("\"").append(attribute.getValue()).append("\"");
//            } else {
//                sb.append(attribute.getValue());
//            }
        }
        
        return sb.toString();
    }
    
}
