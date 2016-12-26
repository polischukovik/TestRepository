package geometry;

import java.util.Comparator;
import java.util.List;

import calculator.App;
import logic.WaypointFinder;

public class Point {
	private double x;
	private double y;
	public Point(double x, double y) {
		super();
		this.x = round(x,App.COORDINATE_PRECISION);
		this.y = round(y,App.COORDINATE_PRECISION);
	}
	
	/*
	 * d=sqlrt((x2−x1)^2+(y2−y1)^2)
	 */
//	public double distanceTo(Point p){
//		App.log.info(this.getClass(), String.format("    Finding distance from %s to %s", this, p));		
//		double d = round(Math.sqrt(Math.pow((p.getX() - this.x), 2) + Math.pow((p.getY() - this.y), 2)), App.COORDINATE_PRECISION);
//		App.log.info(this.getClass(), String.format("    Distance is sqlrt((x2−x1)^2+(y2−y1)^2) = %f", d));
//		return d;
//	}
	
	public double distanceTo(Point p){
		App.log.info(this.getClass(), String.format("    Finding distance from %s to %s", this, p));		
		double d = round(Math.sqrt(Math.pow((p.getX() - this.x), 2) + Math.pow((p.getY() - this.y), 2)), App.COORDINATE_PRECISION);
		App.log.info(this.getClass(), String.format("    Distance is sqlrt((x2−x1)^2+(y2−y1)^2) = %f", d));
		return d;
	}
	
	public static Comparator<Point> getPointNameComparator(Line base){
		return new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				Point intersection = base.getInterctionWithLine(base.getPerprndicularAtPoint(o1));
				if(!intersection.equals(base.getInterctionWithLine(base.getPerprndicularAtPoint(o2)))){
					App.log.info(this.getClass(), "ERRRRRRRRRRRRRRRRRRRRRRRROOOOOOOOOOOOOOOOOOOOOOOOOOOOR");
				}
				double diff = o1.distanceTo(intersection) - o2.distanceTo(intersection);
				return diff < 0 ? -1 : diff > 0 ? 1 : 0;
			}
		};
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

	public int relatesTo(Point a, Segment base) {
		if(this.equals(a)){
			return 0;
		}
		
		if(this.distanceTo(base.getA()) + this.distanceTo(base.getB()) > 
			a.distanceTo(base.getA()) + a.distanceTo(base.getB())){
			return 1;
		}else{
			return -1;
		}
	}

}
