package graphics;

import geometry.Line;
import geometry.Point;
import geometry.Polygon;
import geometry.Segment;

public class Dimention extends Segment{
	private Segment vOvf, hOvf;
	
	public Dimention() {
		super();
	}

	public Dimention(Point a, Point b) {
		super(a, b);
		this.hOvf = Line.getVertical(Point.getCenterOfMass(this.getA(), this.getB())).getProjection(this.getA(), this.getB());
		this.vOvf = Line.getHorizontal(Point.getCenterOfMass(this.getA(), this.getB())).getProjection(this.getA(), this.getB());
	}
	
	public static Dimention getSquareOvf(Dimention dim){
		if (dim.vOvf == dim.hOvf){
			return dim;
		}
		Point center = Point.getCenterOfMass(dim.getA(), dim.getB());
		
		if(dim.vOvf.getLength() < dim.hOvf.getLength()){
			return new Dimention(new Point(center.getLatitude() - dim.gethOvf().getLength()/2, dim.getSW().getLongitude())
					, new Point(center.getLatitude() + dim.gethOvf().getLength()/2, dim.getNE().getLongitude()));
		}else{
			return new Dimention(new Point(dim.getSW().getLatitude(), center.getLongitude() - dim.getvOvf().getLength()/2)
					, new Point(dim.getNE().getLatitude(), center.getLongitude() + dim.getvOvf().getLength()/2));
		}
	}
	public Segment getvOvf() {
		return hOvf;
	}

	public Segment gethOvf() {
		return vOvf;
	}
	
	public Point getSW(){
		return this.getA();
	}
	
	public Point getNE(){
		return this.getB();
	}

}
