package geometry;

import java.util.ArrayList;
import java.util.Collection;

import calculator.App;
import graphics.Dimention;

@SuppressWarnings("serial")
public class Polygon extends ArrayList<Point> {

	public Polygon() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Polygon(Collection<Point> c) {
		super(c);
	}

	public Point getCenterOfMass(){
		double sumLat = 0, sumLong = 0;
		for(Point p : this){
			sumLat += p.getLatitude();
			sumLong += p.getLongitude();
		}
		return new Point(sumLat/this.size(), sumLong/this.size());
	}
	
	public Dimention getOvf(){
	   	Point center = this.getCenterOfMass();
    	App.log.info(this.getClass(), String.format("Central point: %s", center));
		
    	Segment vOvf = Line.getVertical(center).getProjection(this);
		Segment hOvf = Line.getHorizontal(center).getProjection(this);
		
		double latA, lonA, latB, lonB;
		if(vOvf.getA().getLatitude() < vOvf.getB().getLatitude() ){
			latA = vOvf.getA().getLatitude();
			latB = vOvf.getB().getLatitude();
		}else{
			latA = vOvf.getB().getLatitude();
			latB = vOvf.getA().getLatitude();
		}
		
		if(hOvf.getA().getLongitude() < hOvf.getB().getLongitude() ){
			lonA = hOvf.getA().getLongitude();
			lonB = hOvf.getB().getLongitude();
		}else{
			lonA = hOvf.getB().getLongitude();
			lonB = hOvf.getA().getLongitude();
		}
		
		return new Dimention(new Point(lonA, latA), new Point(lonB, latB), hOvf, vOvf);
	}
}
