package javadiptraceasciilib.diptrace.tree;

import javadiptraceasciilib.diptrace.tokenizer.DiptraceToken;
import javadiptraceasciilib.diptrace.tokenizer.DiptraceTokenType;
import javadiptraceasciilib.diptrace.tokenizer.DiptraceTokenizer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class DiptraceItem {
    
    private final String fIdentifier;
    private final List<DiptraceItem> fSubItems = new ArrayList<>();
    
    public DiptraceItem(final String identifier) {
        this.fIdentifier = identifier;
    }
    
    public String getIdentifier() {
        return fIdentifier;
    }
    
    public List<DiptraceItem> getSubItems() {
        return fSubItems;
    }
    
    /**
     * Parse the project
     * @param tokenizer the tokenizer that parses the Diptrace ascii file
     * @throws IOException when IO error occurs
     */
    public abstract void parse(final DiptraceTokenizer tokenizer)
        throws IOException;
    
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
                    String.format("Token is not an identifier: Type: %s, %s\n",
                        token.getType().name(),
                        token.getValue()));
            }
            
            DiptraceItem item = getItemByIdentifier(token);
            item.parse(tokenizer);
            fSubItems.add(item);
            
//            System.err.format("DiptraceItem: %s\n", fIdentifier);
            
            if ( (tokenizer.previewNextToken() == null)
                && (this instanceof DiptraceRootItem) ) {
                return;
            }
            
            tokenizer.eatToken(DiptraceTokenType.RIGHT_PARENTHESES);
        }
    }
    
    
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
            default:
                return new DiptraceGenericItem(token.getValue());
        }
    }
    
    
    public void printTree(final String indent) {
        System.out.format("%s%s\n", indent, fIdentifier);
        for (DiptraceItem subItem : fSubItems) {
            subItem.printTree(indent+"   ");
        }
    }
    
    
    @Override
    public abstract String toString();
    
}
