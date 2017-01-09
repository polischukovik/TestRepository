package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import geometry.Line;
import geometry.Segment;
import gui.JACanvas;

@SuppressWarnings("serial")
public class JGLine  extends Line2D.Float implements JGDisplay {
	private Color color;
	private int size = 1;
	private JACanvas canvas;
	private Line line;
	
	public JGLine(Line line, JACanvas canvas, Color color) {
		super();
		this.line = line;
		this.color = color;
		this.canvas = canvas;
	}
	
	public void show(Graphics g){
		Segment s = line.getSegmentForBox(canvas.getMap().getSW(), canvas.getMap().getNE());
		if(s == null) return;
		
		this.setLine(canvas.getDisplayX(s.getA().getLatitude())
				, canvas.getDisplayY(s.getA().getLongitude())
				, canvas.getDisplayX(s.getB().getLatitude())
				, canvas.getDisplayY(s.getB().getLongitude()));
	
		 Graphics2D g2 = (Graphics2D) g;
		 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		 
		 g2.setStroke(new BasicStroke(size));
		 g2.setPaint(color);
		 g2.draw(this);		 
	}

}
