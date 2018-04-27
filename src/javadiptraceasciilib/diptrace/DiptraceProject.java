/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javadiptraceasciilib.diptrace;

import javadiptraceasciilib.diptrace.tokenizer.DiptraceTokenType;
import javadiptraceasciilib.diptrace.tokenizer.DiptraceTokenizer;
import javadiptraceasciilib.diptrace.tokenizer.DiptraceToken;
import javadiptraceasciilib.diptrace.tree.DiptraceRootItem;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */
public class DiptraceProject {
	
	public final DiptraceRootItem root = new DiptraceRootItem();
	
	public DiptraceProject() {
	}
	
	public void parse(final DiptraceTokenizer tokenizer) throws IOException {
		
//		try {
//		root = new DiptraceRootItem();
		root.parse(tokenizer);
//		}
//		catch (Throwable e) {
//		}
		
//		root.printTree("");
/*		
		try {
			DiptraceToken token;
			while ((token = tokenizer.nextToken()) != null) {
//				System.out.format("Token: %s: %s\n", token.type.name(), token.getValue());
//				System.err.format("Token: %s: %s\n", token.type.name(), token.getValue());
				
				if (token.type == DiptraceTokenType.IDENTIFIER) {
					
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
*/		
	}
	
}