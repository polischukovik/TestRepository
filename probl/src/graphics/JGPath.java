package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.util.List;

import geometry.Path;
import geometry.Point;
import graphics.strokes.CompositeStroke;
import graphics.strokes.CompoundStroke;
import graphics.strokes.ShapeStroke;
import gui.JACanvas;

@SuppressWarnings("serial")
public class JGPath extends Path2D.Float implements CanvasObject {

	private JACanvas canvas;
	private Path waypoints;
	private Color color;
	private int size = 5;

	public JGPath(Path waypoints, JACanvas jaCanvas, Color color) {
		super();
		this.waypoints = waypoints;
		this.canvas = jaCanvas;
		this.color = color;
	}

	@Override
	public void show(Graphics g) {
		this.reset();
		if(waypoints == null && waypoints.getWaypoints().size() < 2){
			return;
		}
		this.moveTo(canvas.getDisplayX(waypoints.getWaypoints().get(0).getLatitude()), canvas.getDisplayY(waypoints.getWaypoints().get(0).getLongitude()));
		for(int i=1; i < waypoints.getWaypoints().size(); i++){
			this.lineTo(canvas.getDisplayX(waypoints.getWaypoints().get(i).getLatitude()), canvas.getDisplayY(waypoints.getWaypoints().get(i).getLongitude()));			
		 }
		 Graphics2D g2 = (Graphics2D) g;
		 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		 
		 ShapeStroke arrowStroke = new ShapeStroke(new Shape[] {(Shape) createArrow(10, 30)}, 300);
		 g2.setPaint(Color.BLACK);
		 g2.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		 g2.draw(this);
			
		 g2.setPaint(color);
		 g2.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		 g2.draw(this);
			
		 g2.setPaint(Color.BLACK);
		 g2.setStroke(arrowStroke);
		 g2.draw(this);
		
	}
	
	private Path2D.Double createArrow(int base, int length) {        
        Path2D.Double path = new Path2D.Double();
        path.moveTo(0, -base/2);
        path.lineTo(0, base/2);
        path.lineTo(length, 0);
        path.lineTo(0, -base/2);        
        return path;
    }	

}
