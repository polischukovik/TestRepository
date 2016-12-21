package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import geometry.Line;
import geometry.Point;
import geometry.Segment;
import gui.JACanvas;

@SuppressWarnings("serial")
public class JGLine  extends Line2D.Float implements JGDisplay {
	private Color color;
	private int size = 2;
	private JACanvas canvas;
	private Line line;
	
	public JGLine(Line line, JACanvas canvas, Color color) {
		super();
		this.line = line;
		this.color = color;
		this.canvas = canvas;
	}
	
	public void show(Graphics g){
		Segment s = line.getSegment(new Point(canvas.getMapX(0),canvas.getMapY(0)), new Point(canvas.getMapX(canvas.getWidth()), canvas.getMapY(canvas.getY()))); 
		if(s == null) return;
		this.setLine(canvas.getDisplayX(s.getA().getX())
				, canvas.getDisplayY(s.getA().getY())
				, canvas.getDisplayX(s.getB().getX())
				, canvas.getDisplayY(s.getB().getY()));
	
		 Graphics2D g2 = (Graphics2D) g;
		 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		 
		 g2.setStroke(new BasicStroke(size));
		 g2.setPaint(color);
		 g2.draw(this);		 
	}

}
