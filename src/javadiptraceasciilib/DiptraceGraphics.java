
package javadiptraceasciilib;

import java.awt.Graphics2D;
import javadiptraceasciilib.old.diptrace.DiptraceProject;

/**
 *
 */
public class DiptraceGraphics {
    
    private final DiptraceProject fProject;
    
    public DiptraceGraphics(DiptraceProject project) {
        
        this.fProject = project;
    }
    
    public void draw(Graphics2D graphics) {
        
        if (fProject == null)
            return;
        
        graphics.drawOval(0, 0, 100, 100);
    }
    
}
