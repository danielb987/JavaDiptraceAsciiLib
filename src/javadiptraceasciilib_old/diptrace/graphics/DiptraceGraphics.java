
package javadiptraceasciilib_old.diptrace.graphics;

import java.awt.Graphics2D;
import javadiptraceasciilib_old.diptrace.DiptraceProject;

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
