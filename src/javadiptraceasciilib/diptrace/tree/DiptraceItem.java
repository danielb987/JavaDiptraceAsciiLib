/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
	
	public final String identifier;
	public final List<DiptraceItem> subItems = new ArrayList<>();
	
	public DiptraceItem(String identifier) {
		this.identifier = identifier;
	}
	
	public abstract void parse(final DiptraceTokenizer tokenizer) throws IOException;
	
	public void parseSubItems(final DiptraceTokenizer tokenizer) throws IOException {
		DiptraceToken token;
		while (((token = tokenizer.previewNextToken()) != null) && (token.type == DiptraceTokenType.LEFT_PARENTHESES)) {
			
//			System.out.format("%s: Token: %s, %s\n", identifier, token.type.name(), token.value);
//			System.err.format("Token: %s, %s\n", token.type.name(), token.value);
			
			// Eat the token
			tokenizer.nextToken();
			
			token = tokenizer.nextToken();
//			System.out.format("%s: Token: %s, %s\n", identifier, token.type.name(), token.value);
//			System.err.format("%s: Token: %s, %s\n", identifier, token.type.name(), token.value);
//			System.err.format("Token: %s, %s\n", token.type.name(), token.value);
			if (token.type != DiptraceTokenType.IDENTIFIER)
				throw new RuntimeException(String.format("Token is not an identifier: Type: %s, %s\n", token.type.name(), token.value));
			
			DiptraceItem item = getItemByIdentifier(token);
			item.parse(tokenizer);
			subItems.add(item);
			
//			System.err.format("DiptraceItem: %s\n", identifier);
			
			if ( (tokenizer.previewNextToken() == null) && (this instanceof DiptraceRootItem) )
				return;
			
			tokenizer.eatToken(DiptraceTokenType.RIGHT_PARENTHESES);
		}
	}
	
	
	private DiptraceItem getItemByIdentifier(DiptraceToken token) {
		
		switch (token.value) {
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
				return new DiptraceGenericItem(token.value);
		}
		
//		throw new RuntimeException(String.format("Unknown identifier: %s\n", token.value));
	}
	
	
	public void printTree(String indent) {
		System.out.format("%s%s\n", indent, identifier);
		for (DiptraceItem subItem : subItems)
			subItem.printTree(indent+"   ");
	}
	
	
	@Override
	public abstract String toString();
	
}
