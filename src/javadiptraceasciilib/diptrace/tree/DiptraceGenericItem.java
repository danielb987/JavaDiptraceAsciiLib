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
public class DiptraceGenericItem extends DiptraceItem {
    
    private final List<DiptraceToken> fParameters = new ArrayList<>();
    
    
    public DiptraceGenericItem(final String identifier) {
        super(identifier);
    }
    
    public List<DiptraceToken> getParameters() {
        return fParameters;
    }
    
    /**
     * Parse the project
     * @param tokenizer the tokenizer that parses the Diptrace ascii file
     * @throws IOException when IO error occurs
     */
    @Override
    public void parse(final DiptraceTokenizer tokenizer) throws IOException {
        
        DiptraceToken token;
        while (((token = tokenizer.previewNextToken()) != null)
            && (token.type != DiptraceTokenType.LEFT_PARENTHESES)
            && (token.type != DiptraceTokenType.RIGHT_PARENTHESES)) {
            
            token = tokenizer.nextToken();
            fParameters.add(token);
        }
        
        if (token.type == DiptraceTokenType.LEFT_PARENTHESES)
            parseSubItems(tokenizer);
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getIdentifier());
        
        for (DiptraceToken parameter : fParameters) {
            sb.append(" ");
            if (parameter.type == DiptraceTokenType.STRING)
                sb.append("\"").append(parameter.value).append("\"");
            else
                sb.append(parameter.value);
        }
        
        return sb.toString();
    }
    
}
