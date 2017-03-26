package graphics;

import java.util.ArrayList;

import geometry.Line;
import geometry.GeoPoint;
import geometry.Segment;

public class Dimention{
	private Segment vOvf, hOvf, diagonal, squareDiagonal;
	private GeoPoint SW, NE, center;
	
	public Dimention(ArrayList<GeoPoint> points) {
		//rough center of mass represents average point concentration
		GeoPoint centerDummy = GeoPoint.getCenterOfMass(points.toArray(new GeoPoint[0]));
		//Segments progected on rough center of mass
    	Segment vOvfDummy = Line.getVertical(centerDummy).getProjection(points.toArray(new GeoPoint[0]));
		Segment hOvfDummy = Line.getHorizontal(centerDummy).getProjection(points.toArray(new GeoPoint[0]));
		
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
		
		this.SW = new GeoPoint(latMin, lonMin);
		this.NE = new GeoPoint(latMax, lonMax);
		this.diagonal = new Segment(SW, NE);
		
		this.center = GeoPoint.getCenterOfMass(diagonal.getA(), diagonal.getB());
		
		this.hOvf = Line.getHorizontal(center).getProjection(diagonal.getA(), diagonal.getB());
		this.vOvf = Line.getVertical(center).getProjection(diagonal.getA(), diagonal.getB());
		
		if (vOvf.getLength() == hOvf.getLength()){
			this.squareDiagonal = diagonal;
		}
		
		if(vOvf.getLength() < hOvf.getLength()){
			//Latitude is 2 times less then Longitude
			double factor = ((NE.getLongitude() - SW.getLongitude())/2)/2;
			this.squareDiagonal = new Segment(
					  new GeoPoint(center.getLatitude() - factor, SW.getLongitude())
					, new GeoPoint(center.getLatitude() + factor, NE.getLongitude()));
		}else{
			double factor = ((NE.getLatitude() - SW.getLatitude())/2)*2;
			this.squareDiagonal = new Segment(
					  new GeoPoint(SW.getLatitude(), center.getLongitude() - factor)
					, new GeoPoint(NE.getLatitude(), center.getLongitude() + factor)
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

	public GeoPoint getSW(){
		return SW;
	}
	
	public GeoPoint getNE(){
		return NE;
	}

	public GeoPoint getCenter() {
		return center;
	}

}
