package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import geometry.Point;

public class JGPoint extends Point implements JGDisplay {
	private Color color;
	private int size = 4;

	public JGPoint(double x, double y, Color color) {
		super(x, y);
		this.color = color;
	}
	
	public void show(Graphics g){
		 Graphics2D g2 = (Graphics2D) g;
		 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		 g2.setPaint(color);
		 g2.fill(new Ellipse2D.Double(this.getX() - size/2, this.getY() - size/2, size, size));
		 
	}

}
