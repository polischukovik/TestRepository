package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;

import geometry.Line;
import geometry.Path;
import geometry.GeoPoint;
import geometry.Polygon;
import geometry.Segment;
import graphics.CanvasObject;
import graphics.Dimention;
import graphics.JGLine;
import graphics.JGPath;
import graphics.JGPoint;
import graphics.JGPolygon;
import graphics.JGSegment;
import logginig.Logger;
import tools.GoogleTools;

@SuppressWarnings("serial")
public class JACanvas extends JPanel {
	Logger logger = Logger.getLogger(JACanvas.class);
	
	final static BasicStroke stroke = new BasicStroke(2.0f);
	private List<CanvasObject> objects = new ArrayList<>();
	private BufferedImage defaultImage;
	
	int imageTopLeftX = 0;
	int imageTopLeftY = 0;
	public JADisplay display;
	
	public static enum CanvasElements{
		GeoPoint,
		Line,
		Segment,
		Polygon,
		Path
	}; 
	
	public JACanvas(JADisplay display) {
		super();
		this.display = display;
		setLayout(new BorderLayout());
		this.setBackground(Color.LIGHT_GRAY);

		defaultImage = GoogleTools.getMapImage(null, null, 0, "./resources/img/blank.png");
		render();
	}

	public void setMapForArea(Dimention ovf){
			}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if(display.map == null){			
			g.drawImage(defaultImage, 0, 0, this);
		}
		if(display.map != null && display.map.getImage() != null){
			BufferedImage img = display.map.getImage(); 
			double displayWidth = getSize().getWidth();
			double displayHeight = getSize().getHeight();
			imageTopLeftX = (int) (displayWidth / 2 - img.getWidth() / 2);
			imageTopLeftY = (int) (displayHeight / 2 - img.getHeight() / 2);
        	g.drawImage(img, imageTopLeftX, imageTopLeftY, this);
        }		
		
		if(display.map != null){
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
		case GeoPoint:
			return new JGPoint((GeoPoint) obj, this, color);
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
	
	public CanvasObject createElement(Object obj, Color color){
		CanvasObject element = createCanvasElement(obj, color);
		objects.add(element);
		return element;
	}
	
	public void removeAllElements(Collection<CanvasObject> collection) {
		if(collection == null) return;
		objects.removeAll(collection);
	}
	
	public void clear(){
		objects.clear();
	}
	
	public void render(){
		this.repaint(); 
	}    
	
	public int getDisplayX(double longitude){		
		double Pmin = imageTopLeftX;
		double Pmax = display.map.getImage().getWidth() + imageTopLeftX;
		double Cmin = display.map.getSW().getLongitude();
		double Cmax = display.map.getNE().getLongitude();
		
		return proportion(Pmin, Pmax, Cmin, Cmax, longitude);
	}
	
	public int getDisplayY(double latitude){
		double Pmin = imageTopLeftY;
		double Pmax = display.map.getImage().getHeight() + imageTopLeftY;
		double Cmin = display.map.getNE().getLatitude();
		double Cmax = display.map.getSW().getLatitude();
		//logger.info(String.format("Pmin %f Pmax %f Cmin %f Cmax %f longitude %f",Pmin, Pmax, Cmin, Cmax, latitude));
		return proportion(Pmin, Pmax, Cmin, Cmax, latitude);
	}
	
	private int proportion(double Pmin, double Pmax, double Cmin, double Cmax, double coordinate){
		return new java.lang.Double((Pmax - Pmin) * (coordinate - Cmin) / (Cmax - Cmin) + Pmin).intValue();
	}
	
}
