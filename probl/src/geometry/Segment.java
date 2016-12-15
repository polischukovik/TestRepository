package geometry;

import java.util.ArrayList;
import java.util.List;

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
		length = a.distanceTo(b);
	}
	
	/*
	 * 1) Поділити відрізок з координатами x1,x2 на n частин
	 */
	public List<Point> devideSegment(int n){
		System.out.println(String.format("\nDeviding segment %s into %d parts", this, n));
		List<Point> subSegment = new ArrayList<>();
		for(int i = 1; i <= n-1; i++){
			System.out.println(String.format("  Calculating devition point %d", i));
			double R = 1.0*i/n;
			System.out.println(String.format("    Ratio is %d/%d = %f", i, n, R));
			double xM = (this.a.getX() + R * this.b.getX()) / (1 + R); 
			double yM = (this.a.getY() + R * this.b.getY()) / (1 + R);
			
			Point p = new Point(xM, yM);
			System.out.println(String.format("    Got point %s", p));
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
	 * 
	 * k=-A/B
	 * b=-C/B
	 */
	public Line getLine(){
		System.out.println(String.format("  Geting line for segment %s", this));
		double A,B,C,k,b;
		A=this.a.getY()-this.b.getY();
		System.out.println(String.format("  A = y1-y2=%f-%f = %f", this.a.getY(), this.b.getY(), A));
		
		B=this.b.getX()-this.a.getX();
		System.out.println(String.format("  B = x2-x1=%f-%f = %f", this.b.getX(), this.a.getX(), B));
		
		C=this.a.getX()*this.b.getY() - this.b.getX()*this.a.getY();
		System.out.println(String.format("  C = x1y2 - x2y1 = %f*%f - %f*%f = %f", this.a.getX(), this.b.getY(), this.b.getX(), this.a.getY(), C));
		
		k=(-1)*A/B;
		System.out.println(String.format("  k = -A/B = -1*(%f)/(%f) = %f", A, B, k));
		
		b=(-1)*C/B;
		System.out.println(String.format("  b = -C/B = -1*(%f)/(%f) = %f", C, B, b));
		
		return new Line(k, b);
	}
	
	/*
	 * Для кожноі точки formPoints[n] і formPoints[n+1] отримати відоізки які вони утворюють
	 */
	public static List<Segment> getSegments(List<Point> list){
		List<Segment> result = new ArrayList<>();
		for(int i=0; i< list.size(); i++){
			if(i == list.size() -1){
				System.out.println(String.format("\nCreating segment for %s and %s", list.get(i), list.get(0)));
				result.add(new Segment(list.get(i),list.get(0)));
				return result;
			}
			System.out.println(String.format("\nCreating segment for %s and %s", list.get(i), list.get(i+1)));
			result.add(new Segment(list.get(i),list.get(i+1)));
		}
		return result;
	}
	
	/*
	 * Чи належить точка відрізку?
	 * чкщо сума відстаней до початку і кінця відрізку дорівнює довжині відрізку то ледить 
	 */
	public boolean contains(Point p){
		System.out.print(String.format("\nDoes %s contains %s?\t", this, p));
		boolean bool = (length == (p.distanceTo(a)  + p.distanceTo(b)));
		System.out.println(String.format("%b", bool));
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
	public String toString() {
		return "Segment [a=" + a + ", b=" + b + ", length=" + length + "]";
	}	
}
