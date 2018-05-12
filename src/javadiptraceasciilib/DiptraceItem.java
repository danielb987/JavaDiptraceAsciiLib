package javadiptraceasciilib;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is the base class for items in the DipTrace ascii file.
 */
abstract class DiptraceItem implements DiptraceTreeNode {
    
    /**
     * The parent of this item.
     */
    private final DiptraceItem fParent;
    
    /**
     * The identifier of this item.
     */
    private final String fIdentifier;
    
    /**
     * The sub items of this item.
     */
    private final List<DiptraceItem> fSubItems = new ArrayList<>();
    
    /**
     * May this item have sub items?
     */
    private boolean fMayHaveSubItems;
    
    /**
     * A map with the sub items there the sub item identifier is the key
     * to the map.
     */
    private final Map<String, DiptraceItem> fSubItemsMap = new HashMap<>();
    
    /**
     * Initializes a DiptraceItem object with an identifier.
     * @param parent the parent of this item
     * @param identifier the identifier
     */
    DiptraceItem(final DiptraceItem parent, final String identifier) {
        this.fParent = parent;
        this.fIdentifier = identifier;
    }
    
    /**
     * Duplicate this item and all its children.
     * @param parent the parent of the new item
     * @return the copy of this item
     */
    abstract DiptraceItem duplicate(DiptraceItem parent);
    
    /**
     * Returns the parent.
     * @return the parent
     */
    final DiptraceItem getParent() {
        return fParent;
    }
    
    /**
     * Returns the identifier.
     * @return the identifier
     */
    final String getIdentifier() {
        return fIdentifier;
    }
    
    /**
     * Returns the sub items.
     * @return the sub items
     */
    @Override
    public final List<DiptraceItem> getChildren() {
        return fSubItems;
    }
    
    /**
     * Get a sub item by the item's identifier.
     * @param identifier the items identifier
     * @return a Diptrace item
     */
    final DiptraceItem getSubItem(final String identifier) {
        
        return fSubItemsMap.get(identifier);
    }
    
    /**
     * Adds a sub item to this item.
     * @param item the item to add
     */
    final void addSubItem(final DiptraceItem item) {
        
        fSubItems.add(item);
        fSubItemsMap.put(item.fIdentifier, item);
    }
    
    /**
     * Get whenether this item have sub items.
     * @return true if this item may have sub items
     */
    final boolean getMayHaveSubItems() {
        return fMayHaveSubItems;
    }
    
    /**
     * Set whenether this item have sub items.
     * @param mayHaveSubItems true if this item may have sub items
     */
    final void setMayHaveSubItems(final boolean mayHaveSubItems) {
        fMayHaveSubItems = mayHaveSubItems;
    }
    
    /**
     * Parse the item.
     * @param tokenizer the tokenizer that parses the Diptrace ascii file
     * @throws IOException when IO error occurs
     */
    abstract void parse(DiptraceTokenizer tokenizer)
        throws IOException;
    
    /**
     * Parse the sub items.
     * @param tokenizer the tokenizer that parses the document
     * @throws IOException on any I/O error
     */
    //CHECKSTYLE.OFF: InnerAssignment - Allow assignment in while loop
    protected final void parseSubItems(final DiptraceTokenizer tokenizer)
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
            
            DiptraceItem item = createItemByIdentifier(token);
            item.parse(tokenizer);
            fSubItems.add(item);
            fSubItemsMap.put(item.fIdentifier, item);
            
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
     * Write the item.
     * @param writer the writer that writes to the Diptrace ascii file
     * @param indent a string of spaces to indent the tree in the ascii file
     * @throws IOException when IO error occurs
     */
    abstract void write(Writer writer, String indent)
        throws IOException;
    
    /**
     * Write the sub items.
     * @param writer the writer that writes to the Diptrace ascii file
     * @param indent a string of spaces to indent the tree in the ascii file
     * @return true if item has sub items
     * @throws IOException when IO error occurs
     */
    protected final boolean writeSubItems(
        final Writer writer,
        final String indent)
        throws IOException {
        
        if (!fSubItems.isEmpty()) {
            String newIndent;
            
            if (this.getParent() != null) {
                writer.append(System.lineSeparator());
                newIndent = indent + "  ";
            } else {
                newIndent = indent;
            }
            
            if (!fSubItems.isEmpty()) {
                for (DiptraceItem subItem : fSubItems) {
                    subItem.write(writer, newIndent);
                }
            } else {
                writer.append(System.lineSeparator());
            }
            
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Creates and returns an instance of a class that inherits DiptraceItem.
     * It decides what type of class by the identifier of the token.
     * @param token the token
     * @return an instance of a sub class to DiptraceItem
     */
    private DiptraceItem createItemByIdentifier(final DiptraceToken token) {
        
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
            case "Shape":
                return new DiptraceShapeItem(this, token.getValue());
                
            default:
                return new DiptraceGenericItem(this, token.getValue());
        }
    }
    
    
    /**
     * Prints the tree of this item and its sub items.
     * @param indent a string of spaces to indent the tree
     */
    public final void printTree(final String indent) {
        System.out.format("%s%s%n", indent, fIdentifier);
        String newIndent = indent + "   ";
        for (DiptraceItem subItem : fSubItems) {
            subItem.printTree(newIndent);
        }
    }
    
    /**
     * Get the total number of children including myself.
     * @return the number of children
     */
    final int numChildren() {
        int count = 1;
        for (DiptraceItem subItem : fSubItems) {
            count += subItem.numChildren();
        }
        return count;
    }
    
    /**
     * Returns a string representation of this object.
     * @return a string
     */
    @Override
    public abstract String toString();
    
}
