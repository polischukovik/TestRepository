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
	
	public Dimention getDimention(){
	   	return new Dimention(this);
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
