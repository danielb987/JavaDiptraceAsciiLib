/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

	@Override
	public void parse(DiptraceTokenizer tokenizer) throws IOException {
		parseSubItems(tokenizer);
		
//		tokenizer.eatToken(DiptraceTokenType.RIGHT_PARENTHESES);
	}
	
	@Override
	public String toString() {
		return getIdentifier();
	}
	
}
