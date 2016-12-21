package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import geometry.Line;
import geometry.Point;
import geometry.Segment;
import gui.JACanvas;



public class JGSegment  extends Line2D.Float implements JGDisplay {
	private Color color;
	private int size = 2;
	
	public JGSegment(Line2D.Float l, Color color) {
		super(l.getP1(), l.getP2());
		this.color = color;
	}
	
	public JGSegment(Segment s, Color color) {
		super(
		(float) s.getA().getX(),
		(float) s.getA().getY(),
		(float) s.getB().getX(),
		(float) s.getA().getY());
		this.color = color;
	}
	
	public void show(Graphics g){
		 Graphics2D g2 = (Graphics2D) g;
		 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		 g2.setPaint(color);
		 g2.draw(this);		 
	}
	
	public static List<JGDisplay> toList(List<Line> list, JACanvas canvas, Color color){
		List<JGDisplay> result = new ArrayList<>();
		for(Line l : list){
			Segment s = l.getSegment(new Point(canvas.getMapX(0),canvas.getMapY(0)), new Point(canvas.getMapX(canvas.getWidth()), canvas.getMapY(canvas.getY())));
			JGSegment j = new JGSegment(s, color);
			result.add(j);
		}
		return result;		
	}

}
