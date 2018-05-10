package javadiptraceasciilib;

import java.util.List;

/**
 * A node in a Diptrace tree.
 */
public interface DiptraceTreeNode {
    
    /**
     * Get all the children of this node.
     * @return a list of the children
     */
    List<? extends DiptraceTreeNode> getChildren();
    
}
