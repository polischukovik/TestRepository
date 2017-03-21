package graphics;

import java.util.ArrayList;

import geometry.Line;
import geometry.Point;
import geometry.Segment;

public class Dimention{
	private Segment vOvf, hOvf, diagonal, squareDiagonal;
	private Point SW, NE;
	
	public Dimention(ArrayList<Point> points) {
		//rough center of mass represents average point concentration
		Point center = Point.getCenterOfMass(points.toArray(new Point[0]));
		//Segments progected on rough center of mass
    	Segment vOvfDummy = Line.getVertical(center).getProjection(points.toArray(new Point[0]));
		Segment hOvfDummy = Line.getHorizontal(center).getProjection(points.toArray(new Point[0]));
		
		double latMin, lonMin, latMax, lonMax;
		if(vOvfDummy.getA().getLatitude() < vOvfDummy.getB().getLatitude() ){
			latMin = vOvfDummy.getA().getLatitude();
			latMax = vOvfDummy.getB().getLatitude();
		}else{
			latMin = vOvfDummy.getB().getLatitude();
			latMax = vOvfDummy.getA().getLatitude();
		}
		
		if(hOvfDummy.getA().getLongitude() < hOvfDummy.getB().getLongitude() ){
			lonMin = hOvfDummy.getA().getLongitude();
			lonMax = hOvfDummy.getB().getLongitude();
		}else{
			lonMin = hOvfDummy.getB().getLongitude();
			lonMax = hOvfDummy.getA().getLongitude();
		}		
		
		this.SW = new Point(latMin, lonMin);
		this.NE = new Point(latMax, lonMax);
		this.diagonal = new Segment(SW, NE);
		
		Point realCenterOfDiagonal = Point.getCenterOfMass(diagonal.getA(), diagonal.getB());
		
		this.hOvf = Line.getVertical(realCenterOfDiagonal).getProjection(diagonal.getA(), diagonal.getB());
		this.vOvf = Line.getHorizontal(realCenterOfDiagonal).getProjection(diagonal.getA(), diagonal.getB());
		
		if (vOvf.getLength() == hOvf.getLength()){
			this.squareDiagonal = diagonal;
		}
		
		if(vOvf.getLength() < hOvf.getLength()){
			this.squareDiagonal = new Segment(
					  new Point(center.getLatitude() - hOvf.getLength()/2, SW.getLongitude())
					, new Point(center.getLatitude() + hOvf.getLength()/2, NE.getLongitude()));
		}else{
			this.squareDiagonal = new Segment(
					  new Point(SW.getLongitude(), center.getLongitude() - vOvf.getLength()/2)
					, new Point(NE.getLongitude(), center.getLongitude() + vOvf.getLength()/2)
					);
		}
	}
	
	public Segment getvOvf() {
		return hOvf;
	}

	public Segment gethOvf() {
		return vOvf;
	}
	
	public Segment getDiagonal() {
		return diagonal;
	}

	public Segment getSquareDiagonal() {
		return squareDiagonal;
	}

	public Point getSW(){
		return SW;
	}
	
	public Point getNE(){
		return NE;
	}

}
