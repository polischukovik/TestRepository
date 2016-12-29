package graphics;

import geometry.Point;

public class Map{
	private Point start, end;

	public Map(Point start, Point end) {
		super();
		this.start = start;
		this.end = end;
	}

	public Point getStart() {
		return start;
	}

	public void setStart(Point start) {
		this.start = start;
	}

	public Point getEnd() {
		return end;
	}

	public void setEnd(Point end) {
		this.end = end;
	}
	
	final static int GLOBE_WIDTH = 256; // a constant in Google's map projection
	final static int ZOOM_MAX = 21;

	public static int getBoundsZoomLevel(Point northeast,Point southwest,
	                                     int width, int height) {
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

}
