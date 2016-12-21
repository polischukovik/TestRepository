package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import gui.JACanvas;

@SuppressWarnings("serial")
public class JGPoint extends Point implements JGDisplay {
	private Color color;
	private int size = 4;
	private JACanvas canvas;

	public JGPoint(geometry.Point p, JACanvas canvas, Color color) {
		super(new Point(canvas.getDisplayX(p.getX()), canvas.getDisplayY(p.getY())));
		this.color = color;
		this.canvas = canvas;
	}
	
	public void show(Graphics g){
		 Graphics2D g2 = (Graphics2D) g;
		 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		 g2.setPaint(color);
		 g2.fill(new Ellipse2D.Double(this.x - size/2, this.getY() - size/2, size, size));
		 
	}
	
	public static List<JGDisplay> toList(List<geometry.Point> list, JACanvas canvas, Color color){
		List<JGDisplay> result = new ArrayList<>();
		for(geometry.Point p : list){
			result.add(new JGPoint(p, canvas, color));
		}
		return result;		
	}
	
	public static java.awt.Point getDisplayCoordinates(Point p){
		return null;
	}

}
