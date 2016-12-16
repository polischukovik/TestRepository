package geometry;

import logic.WaypointFinder;

public class Point {
	private double x;
	private double y;
	public Point(double x, double y) {
		super();
		this.x = round(x,WaypointFinder.COORDINATE_PRECISION);
		this.y = round(y,WaypointFinder.COORDINATE_PRECISION);
	}
	
	/*
	 * d=sqlrt((x2−x1)^2+(y2−y1)^2)
	 */
	public double distanceTo(Point p){
		System.out.println(String.format("    Finding distance from %s to %s", this, p));		
		double d = round(Math.sqrt(Math.pow((p.getX() - this.x), 2) + Math.pow((p.getY() - this.y), 2)), WaypointFinder.COORDINATE_PRECISION);
		System.out.println(String.format("    Distance is sqlrt((x2−x1)^2+(y2−y1)^2) = %f", d));
		return d;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	
	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}	
	
}
