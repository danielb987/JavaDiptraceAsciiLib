package javadiptraceasciilib.diptrace.tree;

import javadiptraceasciilib.diptrace.tokenizer.DiptraceToken;
import javadiptraceasciilib.diptrace.tokenizer.DiptraceTokenType;
import javadiptraceasciilib.diptrace.tokenizer.DiptraceTokenizer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the base class for items in the DipTrace ascii file.
 */
public abstract class DiptraceItem {
    
    /**
     * The identifier of this item.
     */
    private final String fIdentifier;
    
    /**
     * The sub items of this item.
     */
    private final List<DiptraceItem> fSubItems = new ArrayList<>();
    
    /**
     * Initializes a DiptraceItem object with an identifier.
     * @param identifier the identifier
     */
    public DiptraceItem(final String identifier) {
        this.fIdentifier = identifier;
    }
    
    /**
     * Returns the identifier.
     * @return the identifier
     */
    public String getIdentifier() {
        return fIdentifier;
    }
    
    /**
     * Returns the sub items.
     * @return the sub items
     */
    public List<DiptraceItem> getSubItems() {
        return fSubItems;
    }
    
    /**
     * Parse the project.
     * @param tokenizer the tokenizer that parses the Diptrace ascii file
     * @throws IOException when IO error occurs
     */
    public abstract void parse(DiptraceTokenizer tokenizer)
        throws IOException;
    
    /**
     * Parse the sub items.
     * @param tokenizer the tokenizer that parses the document
     * @throws IOException on any I/O error
     */
    //CHECKSTYLE.OFF: InnerAssignment - Allow assignment in while loop
    public void parseSubItems(final DiptraceTokenizer tokenizer)
        throws IOException {
        
        DiptraceToken token;
        while (((token = tokenizer.previewNextToken()) != null)
            && (token.getType() == DiptraceTokenType.LEFT_PARENTHESES)) {
            
            // Eat the token
            tokenizer.nextToken();
            
            token = tokenizer.nextToken();
            if (token.getType() != DiptraceTokenType.IDENTIFIER) {
                throw new RuntimeException(
                    String.format("Token is not an identifier: Type: %s, %s%n",
                        token.getType().name(),
                        token.getValue()));
            }
            
            DiptraceItem item = getItemByIdentifier(token);
            item.parse(tokenizer);
            fSubItems.add(item);
            
//            System.err.format("DiptraceItem: %s\n", fIdentifier);
            
            if ((tokenizer.previewNextToken() == null)
                && (this instanceof DiptraceRootItem)) {
                return;
            }
            
            tokenizer.eatToken(DiptraceTokenType.RIGHT_PARENTHESES);
        }
    }
    //CHECKSTYLE.ON: InnerAssignment - Allow assignment in while loop
    
    /**
     * Creates and returns an instance of a class that inherits DiptraceItem.
     * It decides what type of class by the identifier of the token.
     * @param token the token
     * @return an instance of a sub class to DiptraceItem
     */
    private DiptraceItem getItemByIdentifier(final DiptraceToken token) {
        
        switch (token.getValue()) {
/*
            case "Source":
            case "Units":
            case "Scale":
            case "Xpos":
            case "Ypos":
            case "Schematic":
            case "Pages":
            case "Page":
            case "Zones":
            case "PageWidth":
            case "PageHeight":
            case "HorzZones":
            case "VertZones":
            case "ZoneStandard":
            case "ZoneFontName":
            case "ZoneFontSize":
            case "ZoneBorder":
            case "HorzZoneBorder":
            case "VertZoneBorder":
/*
            case "Ypos":
            case "Ypos":
            case "Ypos":
            case "Ypos":
            case "Ypos":
            case "Ypos":
            case "Ypos":
            case "Ypos":
*/
            case "Schematic":
            default:
                return new DiptraceGenericItem(token.getValue());
        }
    }
    
    
    /**
     * Prints the tree of this item and its sub items.
     * @param indent a string of spaces to indent the tree
     */
    public final void printTree(final String indent) {
        System.out.format("%s%s%n", indent, fIdentifier);
        for (DiptraceItem subItem : fSubItems) {
            subItem.printTree(indent + "   ");
        }
    }
    
    
    /**
     * Returns a string representation of this object.
     * @return a string
     */
    @Override
    public abstract String toString();
    
}
