package javadiptraceasciilib;

import java.awt.Graphics2D;
import javadiptraceasciilib.old.diptrace.DiptraceProject;

/**
 * Draw a schematics or a pcb on a Java Graphics2D object.
 */
public final class DiptraceGraphics {
    
    /**
     * The Diptrace project.
     */
    private final DiptraceProject fProject;
    
    /**
     * Initialize a DiptraceGraphics object.
     * @param project the DiptraceProject
     */
    public DiptraceGraphics(final DiptraceProject project) {
        
        this.fProject = project;
    }
    
    /**
     * Draw a schematics or pcb on a Java Graphics2D object.
     * @param graphics the graphics to draw on
     */
    public void draw(final Graphics2D graphics) {
        
        if (fProject == null) {
            return;
        }
        
        if (1==0) {
            throw new RuntimeException("Test");
        }
//        graphics.drawOval(0, 0, 100, 100);
    }
    
}
