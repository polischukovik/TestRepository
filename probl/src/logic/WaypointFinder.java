package logic;

import java.util.ArrayList;
import java.util.List;

import datasource.DataSource;
import datasource.FileDS;
import geometry.Line;
import geometry.Point;
import geometry.Segment;
import logginig.Logging;
import probl.App;

public class WaypointFinder {
	public final static int COORDINATE_PRECISION = 2;
	private List<Point> waypoints = new ArrayList<>(); 
	private List<Point> devisionPoints;
	private List<Point> intersections = new ArrayList<>();
	private List<Line> devisionLines = new ArrayList<>();
	public Segment ovf;
	
	public WaypointFinder(DataSource ds) {		
		
		//1) Для кожноі точки formPoints[n] і formPoints[n+1] отримати відоізки які вони утворюють: formSegments
		List<Segment> formSegments = Segment.getSegments(ds.getFormPoints());
		App.log.info(this.getClass(), String.format("---------------------------"));
		
		// 2)Знайти область визначення фігури відносно відрізка base
		Segment base = ds.getBase();
		// Отримаємо пряму на якій ледить відр. base
		
		Line baseLine = base.getLine();		
		ovf = new Segment(ds.getBase().getA(), ds.getBase().getB());
		//Для кожної точки фігури отримати пряму перпендикулярну основній що проходить через точку фігури
		for(Point formPoint : ds.getFormPoints()){
			Line pointPerprndicular = baseLine.getPerprndicularAtPoint(formPoint);
			Point projectionBase = pointPerprndicular.getInterctionWithLine(baseLine);
			
			if(projectionBase.relatesTo(ovf.getA(), base) < 0){
				ovf.setA(projectionBase);
			}
			
			if(projectionBase.relatesTo(ovf.getB(), base) > 0){
				ovf.setB(projectionBase);
			}
			
		}
		
		//3) Поділити відрізок base на devidor, знайти координати точок поділу: devisionPoints[u-1]
		//
		devisionPoints = ovf.devideSegment(ds.getDevidor());
		App.log.info(this.getClass(), String.format("---------------------------"));
				
		//4) Для кожної з прямих devisionLines[m] для кожної з ліній на яких лежать відрізки formSegments[n] Визначити точки перетину які вони утвоюють...
		//Для кожної точки перетину визначити чи належить вона відрізку formSegments[n]
		for(Point dp : devisionPoints){
			List<Point> multiIntersections = new ArrayList<>();
			
			App.log.info(this.getClass(), String.format("\nCreating perpendicular for devision point %s", dp));
			Line dl = ovf.getLine().getPerprndicularAtPoint(dp);
			devisionLines.add(dl);
			
			App.log.info(this.getClass(), String.format("\nDevision line %s", dl));
			for(Segment segment : formSegments){
				
				App.log.info(this.getClass(), String.format("  Find intersection point with segment line %s:\t", segment));
				Point p = segment.getLine().getInterctionWithLine(dl);
				if(p == null){
					App.log.info(this.getClass(), String.format("  Parallel"));
					continue;
				}
				if(segment.contains(p)){
					App.log.info(this.getClass(), String.format("  Belongs to segment\n"));
					multiIntersections.add(p);			
				}else{
					App.log.info(this.getClass(), String.format("  Does not belongs to segment\n"));
				}
			}
			
			multiIntersections.sort(Point.getPointNameComparator(base.getLine()));
			intersections.addAll(multiIntersections);
			waypoints.addAll(multiIntersections);
		}
		App.log.info(this.getClass(), String.format("---------------------------"));
				
		for(Point p : waypoints){
			App.log.info(this.getClass(), p.toString());
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
	
}
