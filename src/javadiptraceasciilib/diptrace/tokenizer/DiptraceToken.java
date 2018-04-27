/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javadiptraceasciilib.diptrace.tokenizer;

/**
 *
 * @author daniel
 */
public class DiptraceToken {
	
	public final DiptraceTokenType type;
	public final String value;
	public final int intValue;
	public final double floatValue;
	
	public DiptraceToken(final DiptraceTokenType type) {
		this.type = type;
		this.value = null;
		this.intValue = 0;
		this.floatValue = 0;
	}
	
	public DiptraceToken(final DiptraceTokenType type, final String value) {
		this.type = type;
		this.value = value;
		this.intValue = 0;
		this.floatValue = 0;
	}
	
	public DiptraceToken(final DiptraceTokenType type, final String value, final int intValue) {
		this.type = type;
		this.value = value;
		this.intValue = intValue;
		this.floatValue = 0;
	}
	
	public DiptraceToken(final DiptraceTokenType type, final String value, final double floatValue) {
		this.type = type;
		this.value = value;
		this.intValue = 0;
		this.floatValue = floatValue;
	}
	
	public String getValue() {
		if (value != null)
			return value;
		else
			return "";
	}
	
}
