package graphics;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import geometry.Point;
import geometry.Polygon;
import tools.GoogleTools;

public class Map{
	final static String URL_PATTERN = "https://maps.googleapis.com/maps/api/staticmap?center=%s,%s&zoom=%d&size=%dx%d&scale=2&maptype=hybrid&format=jpg";

	private Point SW, NE;
	private ImageIcon image;
	private final String defaultImagePath = "blank.jpg";

	/**
	 * Creates blank map
	 */
	public Map() {
		this.SW = null;
		this.NE = null;
		image = createImageIcon(defaultImagePath, "blank");
	}
	
	/**
	 * Creates map to display polygon.
	 * Map contains SW and NE points and GoogleMap image
	 * @param polygon
	 */
	public Map(Polygon polygon, Dimension canvasSize) {
		super();
		Point center = polygon.getCenterOfMass();
		Dimention ovf = polygon.getOvf();
		
		double lat = center.getLatitude();
        double lon = center.getLongitude();
		int zoom = GoogleTools.getBoundsZoomLevel(ovf.getNE(), ovf.getSW(), (int)canvasSize.getWidth(), (int)canvasSize.getHeight()) -1;
		int size_w = (int)canvasSize.getWidth() / 2;
		int size_h = (int)canvasSize.getHeight() / 2;
		image = new ImageIcon( getMapImage(lat, lon, zoom, size_w, size_h));
	}
	
	private Image getMapImage(double lat, double lon, int zoom, int size_w, int size_h) {
		Image image = null;
        try {
        	URL url = new URL(String.format(URL_PATTERN, lat, lon, zoom, size_w, size_h));
            image = ImageIO.read(url);
        } catch (IOException e) {
        	e.printStackTrace();
        }
		return image;
	}

	public ImageIcon getImage() {
		return image;
	}
	
	/** Returns an ImageIcon, or null if the path was invalid. */
	private ImageIcon createImageIcon(String path, String description) {
	    java.net.URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL, description);
	    } else {
	        System.err.println("Couldn't load image: " + path);
	        return null;
	    }
	}

	public Point getSW() {
		return SW;
	}

	public void setSW(Point SW) {
		this.SW = SW;
	}

	public Point getNE() {
		return NE;
	}

	public void setNE(Point NE) {
		this.NE = NE;
	}
}
