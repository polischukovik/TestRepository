package geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import graphics.Dimention;
import logginig.Logger;

@SuppressWarnings("serial")
public class Polygon extends ArrayList<Point> {

	private static Logger logger = Logger.getLogger(Polygon.class);

	public Polygon() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Polygon(Collection<Point> c) {
		super(c);
	}
	
	public Dimention getOvf(){
	   	Point center = Point.getCenterOfMass(this.toArray(new Point[0]));
    	logger .info(String.format("Central point: %s", center));
		
    	Segment vOvf = Line.getVertical(center).getProjection(this.toArray(new Point[0]));
		Segment hOvf = Line.getHorizontal(center).getProjection(this.toArray(new Point[0]));
		
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
		
		return new Dimention(new Point(latA, lonA), new Point(latB, lonB));
	}
	
	/*
	 * Для кожноі точки formPoints[n] і formPoints[n+1] отримати відоізки які вони утворюють
	 */
	public List<Segment> getSegments(){
		List<Segment> result = new ArrayList<>();
		for(int i=0; i< this.size(); i++){
			if(i == this.size() -1){
				logger.info(String.format("\nCreating segment for %s and %s", this.get(i), this.get(0)));
				result.add(new Segment(this.get(i), this.get(0)));
				return result;
			}
			logger.info(String.format("\nCreating segment for %s and %s", this.get(i), this.get(i+1)));
			result.add(new Segment(this.get(i),this.get(i+1)));
		}
		return result;
	}
}
