package graphics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Collection;

import geometry.Point;

@SuppressWarnings("serial")
public class JGPoligon extends ArrayList<Point> implements JGDisplay {
	private Color color;
	
	public JGPoligon(Collection<? extends Point> c, Color color) {
		super(c);
		this.color = color;
	}

	@Override
	public void show(Graphics g) {
		 Graphics2D g2 = (Graphics2D) g;
		 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		 g2.setPaint(color);
		 
		 Polygon poligon = new Polygon();
		 for(Point p : this){
			 poligon.addPoint(new Double(p.getX()).intValue(), new Double(p.getY()).intValue());
		 }
		 g2.fill(poligon);
		
	}
	

}
