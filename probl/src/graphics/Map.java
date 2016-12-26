package graphics;

import java.math.BigDecimal;
import java.util.List;

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
	
    public static double distance(double lat1, double lng1, double lat2, double lng2, boolean miles)  
	{  
	    double pi80 = Math.PI / 180;  
	    lat1 *= pi80;  
	    lng1 *= pi80;  
	    lat2 *= pi80;  
	    lng2 *= pi80;
	
	    double r = 6372.797; // mean radius of Earth in km  
	    double dlat = lat2 - lat1;  
	    double dlng = lng2 - lng1;  
	    double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dlng / 2) * Math.sin(dlng / 2);  
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));  
	    double km = r * c;
	
	    return (miles ? (km * 0.621371192) : km);  
	}
}
