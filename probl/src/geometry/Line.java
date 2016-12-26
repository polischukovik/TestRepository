package geometry;

import java.util.ArrayList;
import java.util.List;

import calculator.App;
import logic.WaypointFinder;

public class Line {

	private double A;
	private double B;
	private double C;	
	
	private Line(double A, double B, double C) {
		super();
		this.A = A;
		this.B = B;
		this.C = C;
	}
	
	/*
	 * http://www.pm298.ru/reshenie/peter.php
	 * 
	 * A2=-B1
	 * B2=A1
	 * C2=-A2*x-B2*y
	 */
	public Line getPerprndicularAtPoint(Point p){
		App.log.info(this.getClass(), String.format("\n  Obtaining perpendicular for %s at %s: ", this, p));
		double Al = Point.round( this.B , App.COORDINATE_PRECISION);
		double Bl = Point.round( -1.0 * this.A , App.COORDINATE_PRECISION);
		double Cl = Point.round( (-1.0) * Al * p.getX() - Bl * p.getY() , App.COORDINATE_PRECISION);
		Line res = new Line(Al, Bl, Cl);
		App.log.info(this.getClass(), String.format("  Perpendicular is %s", res));
		return res;
	}
	
	/*
	 * Точка повинна належати обом прямим
	 * http://e-maxx.ru/algo/lines_intersection
	 * 
	 * Отже маємо системк рівнянь
	 * Если две прямые не параллельны, то они пересекаются. Чтобы найти точку пересечения, достаточно решить систему:
	 * A1x + B1y + C1 = 0
     * A2x + B2y + C2 = 0
     * 
     * Формула Крамера для систми ріаняь...
     * 
	 */
	public Point getInterctionWithLine(Line l){
		App.log.info(this.getClass(), String.format("\n  Obtaining intersectionpoint for lines:"));
		App.log.info(this.getClass(), String.format("  \\/%s", this));
		App.log.info(this.getClass(), String.format("  /\\%s", l));
		if((this.A * l.getB() - l.getA() * this.B) == 0){
			App.log.info(this.getClass(), String.format("null"));
			return null; //Parallel
		}
		double x = Point.round( -1.0 * (this.C * l.getB() - l.getC() * this.B) / (this.A * l.getB() - l.getA() * this.B), App.COORDINATE_PRECISION);
		double y = Point.round( -1.0 * (this.A * l.getC() - l.getA() * this.C) / (this.A * l.getB() - l.getA() * this.B), App.COORDINATE_PRECISION);
		Point p = new Point(x, y);
		App.log.info(this.getClass(), String.format("   =%s", p));
		return p;			
	}
	
	public Segment getSegmentForBox(Point a, Point b){
		
		Double x1,y1,x2,y2;
		
		x1 = a.getX();
		y1 = -1 * A/B * x1 - C/B;
		
		x2 = b.getX();
		y2 = -1 * A/B * x2 - C/B;
		
//		if(y1.isNaN() || y1.isInfinite() || y1 > b.getY() || y1 < a.getY()){
		if(y1.isNaN() || y1.isInfinite()){
			y1 = b.getY();
			x1 = -1 * B/A * y1 - C/A;
			
			if(x1 > b.getX() || x1 < a.getX()){
				return null;
			}		
		}
//		if( y2.isNaN() || y2.isInfinite() || y2 > b.getY() || y2 < a.getY()){
		if( y2.isNaN() || y2.isInfinite()){
			y2 = a.getY();
			x2 = -1 * B/A * y2 - C/A;
			
			if( x2 > b.getX()  || x2 < a.getX()){
				return null;
			}
		}
				
		return new Segment(new Point(x1,y1), new Point(x2,y2));
	}
	
	public Point getProjection(Point p){
		return this.getPerprndicularAtPoint(p).getInterctionWithLine(this);
	}
	
	/**
	 * Calculate determination area of the list of Point on given Line<br>
	 * Basically searches a projection of each point of the list on the Line and calculates the outer points
	 * @param list - the List of the points for which determination area is calculated
	 * @return An instance of a Segment containing the most outer points of projection on Line
	 */
	public Segment getProjection(List<Point> list){
		Segment result = null;
		double distance, max = 0;
		List<Point> proj = new ArrayList<>();
		for(Point p : list){
			proj.add(this.getProjection(p));
		}
		for(Point p : proj){
			for(Point d : proj){
				distance = p.distanceTo(d);
				if(distance > max){
					max = distance;
					result = new Segment(p, d);
				}
			}
		}
		return result;
	}
	
	/**
	 * Generates an instance of Horizontal line that contains Point(0,0)
	 * A=0, B=1, C=0
	 * 
	 * @return an instance of the Line
	 */
	public static Line getHorizontal(){
		return new Line(0, 1, 0);
	}
	
	/**
	 * Generates an instance of Vertical line that contains Point(0,0)
	 * A=1, B=0, C=0
	 * 
	 * @return an instance of the Line
	 */
	public static Line getVertical(){
		return new Line(1, 0, 0);
	}

	public double getA() {
		return A;
	}

	public void setA(double a) {
		A = a;
	}

	public double getB() {
		return B;
	}

	public void setB(double b) {
		B = b;
	}

	public double getC() {
		return C;
	}

	public void setC(double c) {
		C = c;
	}

	@Override
	public String toString() {
		return "Line [A=" + A + ", B=" + B + ", C=" + C + "]";
	}

	/*
	 * Отримання лінійної функції для відрізка *  
	 * (y-y1)/(y2-y1)=(x-x1)/(x2-x1)
	 * (y1-y2)*x + (x2-x1)*y +(x1y2-x2y1)=0
	 * Ax+By+C=0
	 * y=-A/B*x - C/B
	 * A=y1-y2
	 * B=x2-x1
	 * C=x1y2 - x2y1
	 */
	public static Line getLine(Segment segment) {
		App.log.info(segment.getClass(), String.format("  Geting line for segment %s", segment));
		double A,B,C;
		A=segment.getA().getY()-segment.getB().getY();
		App.log.info(segment.getClass(), String.format("  A = y1 - y2 = %f - %f = %f", segment.getA().getY(), segment.getB().getY(), A));
		
		B=segment.getB().getX()-segment.getA().getX();
		App.log.info(segment.getClass(), String.format("  B = x2 - x1 = %f - %f = %f", segment.getB().getX(), segment.getA().getX(), B));
		
		C=segment.getA().getX()*segment.getB().getY() - segment.getB().getX()*segment.getA().getY();
		App.log.info(segment.getClass(), String.format("  C = x1y2 - x2y1 = %f * %f - %f * %f = %f", segment.getA().getX(), segment.getB().getY(), segment.getB().getX(), segment.getA().getY(), C));
		return new Line(A,B,C);
	}
	
}
