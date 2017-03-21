package tools;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import geometry.Point;
import geometry.Polygon;
import graphics.Dimention;

public class GoogleTools {
	final static int GLOBE_WIDTH = 256; // a constant in Google's map projection
	final static int ZOOM_MAX = 21;
	final static String URL_PATTERN = "https://maps.googleapis.com/maps/api/staticmap?center=%s,%s&zoom=%d&size=%dx%d&scale=2&maptype=hybrid&format=jpg";


	public static int getBoundsZoomLevel(Point northeast,Point southwest, int width, int height) {
	    double latFraction = (latRad(northeast.getLatitude()) - latRad(southwest.getLatitude())) / Math.PI;
	    double lngDiff = northeast.getLongitude() - southwest.getLongitude();
	    double lngFraction = ((lngDiff < 0) ? (lngDiff + 360) : lngDiff) / 360;
	    double latZoom = zoom(height, GLOBE_WIDTH, latFraction);
	    double lngZoom = zoom(width, GLOBE_WIDTH, lngFraction);
	    double zoom = Math.min(Math.min(latZoom, lngZoom),ZOOM_MAX);
	    return (int)(zoom);
	}
	private static double latRad(double lat) {
	    double sin = Math.sin(lat * Math.PI / 180);
	    double radX2 = Math.log((1 + sin) / (1 - sin)) / 2;
	    return Math.max(Math.min(radX2, Math.PI), -Math.PI) / 2;
	}
	private static double zoom(double mapPx, double worldPx, double fraction) {
	    final double LN2 = .693147180559945309417;
	    return (Math.log(mapPx / worldPx / fraction) / LN2);
	}
	
	private static ImageIcon getMapImage(double lat, double lon, int zoom, int size_w, int size_h) {
		Image image = null;
        try {
        	URL url = new URL(String.format(URL_PATTERN, lat, lon, zoom, size_w, size_h));
            image = ImageIO.read(url);
        } catch (IOException e) {
        	e.printStackTrace();
        }
		return new ImageIcon(image);
	}
	
	/** Returns an ImageIcon, or null if the path was invalid. */
	private static ImageIcon createImageIcon(String path, String description) {
	    java.net.URL imgURL = GoogleTools.class.getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL, description);
	    } else {
	        System.err.println("Couldn't load image: " + path);
	    	return new ImageIcon();
	    }
	}
	
	/**
	 * Returns image of map which fits given polygon on canvas size. Or returns default image 
	 * @param polygon
	 * @param canvasSize
	 * @param defaultPath
	 * @return
	 */
	public static ImageIcon getMapImage(Polygon polygon, Dimension canvasSize, String defaultPath) {
		if(polygon == null || canvasSize == null) {
			return createImageIcon(defaultPath, "Default image");
		}
		
		Point center = Point.getCenterOfMass(polygon.toArray(new Point[0]));
		Dimention ovf = polygon.getDimention();
		
		double lat = center.getLatitude();
        double lon = center.getLongitude();
		int zoom = GoogleTools.getBoundsZoomLevel(ovf.getNE(), ovf.getSW(), (int)canvasSize.getWidth(), (int)canvasSize.getHeight()) -1;
		int size_w = 620;
		int size_h = 620;
		
		//ImageIcon result = GoogleTools.getMapImage(lat, lon, zoom, size_w, size_h);
		ImageIcon result = null;
		
		if(result == null){
			return createImageIcon(defaultPath, "Default image");
		}
		return result;
	}
}
