package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;

import geometry.Line;
import geometry.Path;
import geometry.Point;
import geometry.Polygon;
import geometry.Segment;
import graphics.CanvasObject;
import graphics.Dimention;
import graphics.JGLine;
import graphics.JGPath;
import graphics.JGPoint;
import graphics.JGPolygon;
import graphics.JGSegment;
import graphics.Map;
import tools.GoogleTools;

@SuppressWarnings("serial")
public class JACanvas extends JPanel {
	final static BasicStroke stroke = new BasicStroke(2.0f);
	private List<CanvasObject> objects = new ArrayList<>();
	private Map map = null;
	
	private Dimension canvasSize = null;
	private BufferedImage defaultImage;
	
	private int imageTopLeftX = 0;
	private int imageTopLeftY = 0;
	
	public int zoom = 0;
	
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
		this.setBackground(Color.LIGHT_GRAY);

		defaultImage = GoogleTools.getMapImage(null, null, 0, "./resources/img/blank.png");
		render();
	}

	public void setMapForArea(Dimention ovf){
		zoom = GoogleTools.getBoundsZoomLevel(ovf.getNE(), ovf.getSW(), (int)canvasSize.getWidth(), (int)canvasSize.getHeight()) -1;
		this.map = new Map(ovf, this.getSize(), zoom);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		canvasSize = getSize();
		
		if(map == null){			
			g.drawImage(defaultImage, 0, 0, this);
		}
		if(map != null && map.getImage() != null){
			BufferedImage img = map.getImage(); 
			imageTopLeftX = (int) (canvasSize.getWidth() - img.getWidth())/2;
			imageTopLeftY = (int) (canvasSize.getHeight() - img.getHeight())/2;
        	g.drawImage(img, imageTopLeftX, imageTopLeftY, this);
        }		
		
		if(map != null){
			for(CanvasObject o : objects){			
				o.show(g);
			}
		}	
	}
	
	private CanvasObject createCanvasElement(Object obj, Color color){
		CanvasElements co;
		try{
			co = CanvasElements.valueOf(obj.getClass().getSimpleName());
		}catch(IllegalArgumentException e){
			throw new IllegalArgumentException(String.format("Element of type %s could not be displayed", obj.getClass().getSimpleName()));
		}
		
		switch (co) {
		case Point:
			return new JGPoint((Point) obj, this, color);
		case Line:
			return new JGLine((Line) obj, this, color);
		case Segment:
			return new JGSegment((Segment) obj, this, color);
		case Polygon:
			return new JGPolygon((Polygon) obj, this, color);
		case Path:
			return new JGPath((Path) obj, this, color);
		default:
			return null;
		}		
	}
	
	public void createElement(Object obj, Color color){
		objects.add(createCanvasElement(obj, color));
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

	public void removeElement(Object obj, Color color) {
		objects.remove(createCanvasElement(obj, color));		
	}
	
	public void removeAllElements(Collection<?> collection, Color color) {
		for(Object obj : collection){
			try {
				removeElement(obj, color);
			} catch (Exception e) {
				// log error here
			}
		}
	}
	
	public void clear(){
		objects.clear();
	}
	
	public void render(){
		this.repaint(); 
	}    
	
	public int getDisplayX(double longitude){		
		double Pmin = imageTopLeftX;
		double Pmax = map.getImage().getWidth() - imageTopLeftX;
		double Cmin = map.getSW().getLongitude();
		double Cmax = map.getNE().getLongitude();
		//System.out.println(String.format("Pmin %f Pmax %f Cmin %f Cmax %f longitude %f",Pmin, Pmax, Cmin, Cmax, longitude));
		
		return proportion(Pmin, Pmax, Cmin, Cmax, longitude);
	}
	
	public int getDisplayY(double latitude){
		double Pmin = imageTopLeftY;
		double Pmax = map.getImage().getHeight() - imageTopLeftY;
		double Cmin = map.getSW().getLatitude();
		double Cmax = map.getNE().getLatitude();
		
		return map.getImage().getHeight() - proportion(Pmin, Pmax, Cmin, Cmax, latitude);
	}
	
	private int proportion(double Pmin, double Pmax, double Cmin, double Cmax, double coordinate){
		return new java.lang.Double((Pmax - Pmin) * (coordinate - Cmin) / (Cmax - Cmin) + Pmin).intValue();
	}
	
	public Map getMap() {
		return map;
	}
	
}
