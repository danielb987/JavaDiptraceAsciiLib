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
 * @author daniel
 */
public class DiptraceGenericItem extends DiptraceItem {
	
	final List<DiptraceToken> parameters = new ArrayList<>();
	
	
	public DiptraceGenericItem(String identifier) {
		super(identifier);
	}
	
	
	@Override
	public void parse(DiptraceTokenizer tokenizer) throws IOException {
		
		DiptraceToken token;
		while (((token = tokenizer.previewNextToken()) != null) && (token.type != DiptraceTokenType.LEFT_PARENTHESES) && (token.type != DiptraceTokenType.RIGHT_PARENTHESES)) {
			token = tokenizer.nextToken();
			parameters.add(token);
//			System.err.format("Token: %s, %s\n", token.type.name(), token.value);
		}
		
		if (token.type == DiptraceTokenType.LEFT_PARENTHESES)
			parseSubItems(tokenizer);

//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//		parseSubItems(tokenizer);
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(identifier);
		
		for (DiptraceToken parameter : parameters) {
			sb.append(" ");
			if (parameter.type == DiptraceTokenType.STRING)
				sb.append("\"").append(parameter.value).append("\"");
			else
				sb.append(parameter.value);
		}
		
		return sb.toString();
	}
	
}
