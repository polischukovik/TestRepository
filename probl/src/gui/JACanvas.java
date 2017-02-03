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
import geometry.Path;
import geometry.Point;
import geometry.Polygon;
import geometry.Segment;
import graphics.CanvasObject;
import graphics.JGLine;
import graphics.JGPath;
import graphics.JGPoint;
import graphics.JGPolygon;
import graphics.JGSegment;
import graphics.Map;

@SuppressWarnings("serial")
public class JACanvas extends JPanel {
	final static BasicStroke stroke = new BasicStroke(2.0f);
	private List<CanvasObject> objects = new ArrayList<>();
	private Map map = null;
	
	private Dimension canvasSize = null;
	private JLabel imageContainer;
	
	public static enum CanvasElements{
		Point,
		Line,
		Segment,
		Polygon,
		Path
	}; 
	
	public JACanvas() {
		super();
		setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
		imageContainer = new JLabel();
		imageContainer.setVerticalAlignment(JLabel.CENTER);
		imageContainer.setHorizontalAlignment(JLabel.CENTER);

		this.add(imageContainer);
		
		imageContainer.setIcon(Map.getDefaultImage());
	}

	public void setMap(Map map){
		this.map = map;
		imageContainer.setIcon(map.getImage());
		render();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(map == null){
			throw new IllegalStateException("Canvas is not initialized");
		}		
		canvasSize = getSize();
		
		for(CanvasObject o : objects){			
			o.show(g);
		}
	}
	
	public void createElement(Object obj, Color color){
		CanvasElements co = CanvasElements.valueOf(obj.getClass().getSimpleName());
		switch (co) {
		case Point:
			objects.add( new JGPoint((Point) obj, this, color));
		case Line:
			objects.add( new JGLine((Line) obj, this, color));
		case Segment:
			objects.add( new JGSegment((Segment) obj, this, color));
		case Polygon:
			objects.add( new JGPolygon((Polygon) obj, this, color));
		case Path:
			objects.add( new JGPath((Path) obj, this, color));
		default:
			throw new IllegalArgumentException(String.format("Element of type %s could not be displayed", obj.getClass().getSimpleName()));
		}
	}
	
	public void createAllElements(Collection<?> collection, Color color){
		for(Object obj : collection){
			try {
				createElement(obj, color);
			} catch (Exception e) {
				// log error here
			}			
		}
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
