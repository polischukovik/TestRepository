package geometry;

import java.util.ArrayList;
import java.util.List;

import calculator.App;
import logic.WaypointFinder;

public class Segment {
	private Point a;
	private Point b;
	private double length;
	
	public Segment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Segment(Point a, Point b) {
		super();
		this.a = a;
		this.b = b;
		length = Point.round(a.distanceTo(b), WaypointFinder.COORDINATE_PRECISION + 2);
		App.log.info(this.getClass(), String.format("  Segment created: %s", this));
	}
	
	/*
	 * 1) Поділити відрізок з координатами x1,x2 на n частин
	 */
	public List<Point> devideSegment(int n){
		App.log.info(this.getClass(), String.format("\nDeviding segment %s into %d parts", this, n));
		List<Point> subSegment = new ArrayList<>();
		for(int i = 1; i <= n-1; i++){
			App.log.info(this.getClass(), String.format("  Calculating devition point %d", i));
			double R = 1.0*i / (n - i);
			App.log.info(this.getClass(), String.format("    Ratio is %d/%d = %f", i, n, R));
			double xM = (this.a.getX() + R * this.b.getX()) / (1 + R); 
			double yM = (this.a.getY() + R * this.b.getY()) / (1 + R);
			
			Point p = new Point(xM, yM);
			App.log.info(this.getClass(), String.format("    Got point %s", p));
			subSegment.add(p);
			}
		return subSegment;
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
	public Line getLine(){
		App.log.info(this.getClass(), String.format("  Geting line for segment %s", this));
		double A,B,C;
		A=this.a.getY()-this.b.getY();
		App.log.info(this.getClass(), String.format("  A = y1 - y2 = %f - %f = %f", this.a.getY(), this.b.getY(), A));
		
		B=this.b.getX()-this.a.getX();
		App.log.info(this.getClass(), String.format("  B = x2 - x1 = %f - %f = %f", this.b.getX(), this.a.getX(), B));
		
		C=this.a.getX()*this.b.getY() - this.b.getX()*this.a.getY();
		App.log.info(this.getClass(), String.format("  C = x1y2 - x2y1 = %f * %f - %f * %f = %f", this.a.getX(), this.b.getY(), this.b.getX(), this.a.getY(), C));
		
		return new Line(A, B, C);
	}
	
	/*
	 * Для кожноі точки formPoints[n] і formPoints[n+1] отримати відоізки які вони утворюють
	 */
	public static List<Segment> getSegments(List<Point> list){
		List<Segment> result = new ArrayList<>();
		for(int i=0; i< list.size(); i++){
			if(i == list.size() -1){
				App.log.info(null, String.format("\nCreating segment for %s and %s", list.get(i), list.get(0)));
				result.add(new Segment(list.get(i),list.get(0)));
				return result;
			}
			App.log.info(null, String.format("\nCreating segment for %s and %s", list.get(i), list.get(i+1)));
			result.add(new Segment(list.get(i),list.get(i+1)));
		}
		return result;
	}
	
	/*
	 * Чи належить точка відрізку?
	 * чкщо сума відстаней до початку і кінця відрізку дорівнює довжині відрізку то ледить 
	 */
	public boolean contains(Point p){
		App.log.info(this.getClass(), String.format("\n  Does %s contains %s?\t", this, p));
		double da = p.distanceTo(a);
		double db = p.distanceTo(b);
		boolean bool = (Point.round(length, WaypointFinder.COORDINATE_PRECISION - 1) == Point.round(da + db, WaypointFinder.COORDINATE_PRECISION - 1));
		App.log.info(this.getClass(), String.format("  %f = %f?", length, da  + db));
		return bool;
	}
	public Point getA() {
		return a;
	}
	public void setA(Point a) {
		this.a = a;
	}
	public Point getB() {
		return b;
	}
	public void setB(Point b) {
		this.b = b;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Segment other = (Segment) obj;
		if((this.a.equals(other.a) && this.b.equals(other.b)) || (this.a.equals(other.b) && this.b.equals(other.a)) ){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public String toString() {
		return "Segment [a=" + a + ", b=" + b + ", length=" + length + "]";
	}	
}
