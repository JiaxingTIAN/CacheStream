package jiaxing.tian.bu;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import javax.swing.*;


public class DrawLine extends JPanel {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int scheme;
    final int PAD = 25;	//set the border width regarding to panel
 
    float[] frac = null;
    int[] 	cache = null;
    int[] 	aux = null;
    public DrawLine(float [] cachefrac, int in){    	
    	frac = cachefrac;
    	this.scheme = in+1;
    	//cache = maxcache;
    	//aux = maxaux;
    }
    
    public void setData(float[] input){
    	frac = input;   	
    }
    
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;	
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        // Draw y axis.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
        // Draw x axis.
        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
        // Draw labels.
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();
        // y label.
        String s = "y";
        float sy = PAD + ((h - 2*PAD) - s.length()*sh)/2 + lm.getAscent();
        for(int i = 0; i < s.length(); i++) {
            String letter = String.valueOf(s.charAt(i));
            float sw = (float)font.getStringBounds(letter, frc).getWidth();
            float sx = (PAD - sw)/2;
            g2.drawString(letter, sx, sy);
            sy += sh;
        }
        // x label.
        g2.setPaint(Color.green.darker());
        s = "x = P value with 0.2 interval";
        sy = h - PAD + (PAD - sh)/2 + lm.getAscent();
        float sw = (float)font.getStringBounds(s, frc).getWidth();
        float sx = (w - sw)/2;
        g2.drawString(s, sx, sy);
        // Draw lines.
        double xInc = (double)(w - 2*PAD)/(frac.length-1);
        double scale = (double)(h - 2*PAD)/getMax();
        g2.setPaint(Color.blue);
        for(int i = 0; i < frac.length-1; i++) {
            double x1 = PAD + i*xInc;
            double y1 = h - PAD - scale*frac[i];
            double x2 = PAD + (i+1)*xInc;
            double y2 = h - PAD - scale*frac[i+1];
            g2.draw(new Line2D.Double(x1, y1, x2, y2));
        }
        // Mark data points.
        g2.setPaint(Color.red);
        for(int i = 0; i < frac.length; i++) {
            double x = PAD + i*xInc;
            double y = h - PAD - scale*frac[i];
            g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
        }
        g2.drawString(""+getMax(), PAD, PAD);
        
        if(scheme == 1){
        	g2.drawString("cache-frac = p", PAD+100, PAD-10);
        }
        if(scheme == 2){
        	g2.drawString("cache-frac = p^2", PAD+100, PAD-10);
        }
        if(scheme == 3){
        	g2.drawString("cache-frac = p^3", PAD+100, PAD-10);
        }
        if(scheme == 4){
        	g2.drawString("cache-frac = p-p^2", PAD+100, PAD-10);
        }
        if(scheme == 5){
        	g2.drawString("cache-frac = 0", PAD+100, PAD-10);
        }
    }
 
    private float getMax() {
        float max = -Integer.MAX_VALUE;
        for(int i = 0; i < frac.length; i++) {
            if(frac[i] > max)
                max = frac[i];
        }
        return max;
    }
 
}