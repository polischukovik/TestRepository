package geometry;

import logic.WaypointFinder;

public class Line {
	private double k;
	private double b;
	public Line(double k, double b) {
		super();
		this.k = k;
		this.b = b;
	}
	
	public Line getPerprndicularAtPoint(Point p){
		System.out.println(String.format("\n  Obtaining perpendicular for %s at %s: ", this, p));
		double kx = -1/this.k;
		double bx = -1/this.k * p.getX() + p.getY();
		Line res = new Line(kx, bx);
		System.out.println(String.format("  Perpendicular is %s: ", res));
		return res;
	}
	
	/*
	 * Точка повинна належати обом прямим
	 * Отже маємо системк рівнянь
	 *
	 *|y=k1x + b1
	 *|y=k2x + b
	 *
	 *k1x+b1 = k2x + b2
	 *k1x -k2x = b2 - b1
	 *x(k1-k2) = b2 - b1
	 *x = (b2 - b1) / (k1 - k2)
	 *y = k1 * (b2 - b1) / (k1 - k2) + b1
	 */
	public Point getInterctionWithLine(Line l){
		System.out.print(String.format("\nObtaining intersectionpoint for lines %s and %s:\t", this, l));
		if(this.getK() == l.getK()){
			System.out.println(String.format("Parallel"));
			return null; //Parallel
		}
		double x = Point.round((l.getB() - this.b) / (this.k - l.getK()), WaypointFinder.COORDINATE_PRECISION);
		double y = Point.round((this.k * (l.getB() - this.b) / (this.k - this.getK()) + this.b), WaypointFinder.COORDINATE_PRECISION);
		Point p = new Point(x, y);
		System.out.println(String.format("%s", p));
		return p;	
				
	}
	
	public double getK() {
		return k;
	}
	public void setK(double k) {
		this.k = k;
	}
	public double getB() {
		return b;
	}
	public void setB(double b) {
		this.b = b;
	}
	@Override
	public String toString() {
		return "Line [k=" + k + ", b=" + b + "]";
	}
	
}
