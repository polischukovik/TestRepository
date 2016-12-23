package logic;

import java.util.ArrayList;
import java.util.List;

import datasource.DataSource;
import geometry.Line;
import geometry.Point;
import geometry.Segment;
import logginig.Logging;

public class WaypointFinder {
	public final static int COORDINATE_PRECISION = 2;

	private Logging logging;
	@SuppressWarnings("unused")
	private DataSource ds;
	
	private List<Point> waypoints = new ArrayList<>(); 
	private List<Point> devisionPoints;
	private List<Point> intersections = new ArrayList<>();
	private List<Line> devisionLines = new ArrayList<>();
	public Segment ovf;
	
	public WaypointFinder(DataSource ds, Logging logging) {
		this.ds = ds;
		this.logging = logging;
		/*
		 *1) Для кожноі точки formPoints[n] і formPoints[n+1] отримати відоізки які вони утворюють: formSegments 
		 */
		List<Segment> formSegments = Segment.getSegments(ds.getFormPoints());
		log(String.format("---------------------------"));
		
		/*
		 * 2)Знайти область визначення фігури відносно відрізка base
		 * Отримаємо пряму на якій ледить відр. base
		 */
		Segment base = ds.getBase();
		ovf = base.getLine().getProjection(ds.getFormPoints());
		
		/*
		 * 3) Поділити відрізок ovf на devidor, знайти координати точок поділу: devisionPoints[u-1]
		 */
		devisionPoints = ovf.devideSegment(ds.getDevidor());
		log(String.format("---------------------------"));
				
		/*
		 * 4) Для кожної з прямих devisionLines[m] для кожної з ліній на яких лежать відрізки formSegments[n] Визначити точки перетину які вони утвоюють...
		 * Для кожної точки перетину визначити чи належить вона відрізку formSegments[n]
		 */		
		for(Point dp : devisionPoints){
			List<Point> allIntersections = new ArrayList<>();
			
			log(String.format("\nCreating perpendicular for devision point %s", dp));
			Line dl = ovf.getLine().getPerprndicularAtPoint(dp);
			devisionLines.add(dl);
			
			log(String.format("\nDevision line %s", dl));
			for(Segment segment : formSegments){
				
				log(String.format("  Find intersection point with segment line %s:\t", segment));
				Point p = segment.getLine().getInterctionWithLine(dl);
				if(p == null){
					log(String.format("  Parallel"));
					continue;
				}
				if(segment.contains(p)){
					log(String.format("  Belongs to segment\n"));
					allIntersections.add(p);			
				}else{
					log(String.format("  Does not belongs to segment\n"));
				}
			}
			
			allIntersections.sort(Point.getPointNameComparator(base.getLine()));
			intersections.addAll(allIntersections);
			waypoints.addAll(allIntersections);
		}
		log(String.format("---------------------------"));
				
		for(Point p : waypoints){
			log(p.toString());
		}	
	}

	public List<Point> getWaypoints() {
		return waypoints;
	}

	public List<Point> getDevisionPoints() {
		return devisionPoints;
	}

	public List<Point> getIntersections() {
		return intersections;
	}

	public List<Line> getDevisionLines() {
		return devisionLines;
	}

	private void log(String string) {
		if(logging != null){
			logging.info(this.getClass(), string);
		}		
	}	
}
