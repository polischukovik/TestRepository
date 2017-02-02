package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import geometry.Line;
import geometry.Point;
import geometry.Segment;
import graphics.CanvasObject;
import graphics.JGLine;
import graphics.JGPoint;
import graphics.JGPoligon;
import graphics.JGSegment;
import graphics.Map;

@SuppressWarnings("serial")
public class JACanvas extends JPanel {
	final static BasicStroke stroke = new BasicStroke(2.0f);
	private List<CanvasObject> objects = new ArrayList<>();
	private Map map;
	
	private Dimension canvasSize = null;
	private JLabel imageContainer;
	
	public JACanvas() {
		super();
		setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
		imageContainer = new JLabel();
		imageContainer.setVerticalAlignment(JLabel.CENTER);
		imageContainer.setHorizontalAlignment(JLabel.CENTER);

		this.add(imageContainer);
		
		setMap(new Map());
	}

	public void setMap(Map map){
		this.map = map;
		imageContainer.setIcon(map.getImage());
		render();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		canvasSize = getSize();
		
		for(CanvasObject o : objects){			
			o.show(g);
		}
	}
	
	public JGPoint createPoint(Point p, Color color){
		return new JGPoint(p, this, color);
	}
	
	public List<CanvasObject> createAllPoints(Collection<? extends Point> points, Color color){
		List<CanvasObject> result = new ArrayList<>();
		for(Point p : points){
			result.add(createPoint(p, color));
		}
		return result;
	}
	
	public CanvasObject createPoligon(List<Point> p, Color color){
		return new JGPoligon(p, this, color);
	}
	
	public CanvasObject createLine(Line l, Color color){
		return new JGLine(l, this, color);
	}
	
	public List<CanvasObject> createAllLines(Collection<? extends Line> lines, Color color){
		List<CanvasObject> result = new ArrayList<>();
		for(Line l : lines){
			result.add(createLine(l, color));
		}
		return result;
	}
	
	public CanvasObject createSegment(Segment s, Color color){
		return new JGSegment(s, this, color);
	}
	
	public List<CanvasObject> createAllSegments(Collection<? extends Segment> segments, Color color){
		List<CanvasObject> result = new ArrayList<>();
		for(Segment s : segments){
			result.add(createSegment(s, color));
		}
		return result;
	}	

	public CanvasObject createWaypointPath(List<Point> waypoints, Color color) {
		return new JGWaypointPath(waypoints, this, color);
	}
	
	public void addObject(CanvasObject obj){
		objects.add(obj);
	}
	
	public void addObject(List<CanvasObject> obj){
		objects.addAll(obj);
	}

	public void removeObjects(List<CanvasObject> o) {
		objects.removeAll(o);		
	}
	
	public void clear(){
		objects.clear();
	}
	
	public void render(){
		this.repaint(); 
	}
	
    
	
	public int getDisplayX(double x){
		return new java.lang.Double((x - map.getSW().getLatitude()) * canvasSize.getWidth() / (map.getNE().getLatitude() - (map.getSW().getLatitude()))).intValue();
	}
	
	public int getDisplayY(double y){
		return new java.lang.Double(canvasSize.getHeight() - (y - map.getSW().getLongitude()) * canvasSize.getHeight() / (map.getNE().getLongitude() - (map.getSW().getLongitude()))).intValue();
	}
	
	public double getMapX(int x){
		return ((x - 0)  * (map.getNE().getLatitude()) - (map.getSW().getLatitude()) ) / (canvasSize.getWidth() - 0);
	}
	
	public double getMapY(int y){
		return ((canvasSize.getHeight() - y - 0)  * (map.getNE().getLongitude()) - (map.getSW().getLongitude()) ) / (canvasSize.getHeight() - 0);
	}
	public Map getMap() {
		return map;
	}
	
}
