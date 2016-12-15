package logic;

import java.util.ArrayList;
import java.util.List;

import datasource.FileDS;
import geometry.Line;
import geometry.Point;
import geometry.Segment;

public class WaypointFinder {
	public final static int COORDINATE_PRECISION = 6;
	private List<Point> waypoints = new ArrayList<>(); 
	
	public WaypointFinder(FileDS ds) {
		List<Point> intersectionPoints, devisionPoints;
		
		intersectionPoints = new ArrayList<>();
		
		//1) Поділити відрізок base на devidor, знайти координати точок поділу: devisionPoints[u-1]
		//
		devisionPoints = ds.getBase().devideSegment(ds.getDevidor());
				
		//2) Для кожноі точки formPoints[n] і formPoints[n+1] отримати відоізки які вони утворюють: formSegments
		List<Segment> formSegments = Segment.getSegments(ds.getFormPoints());
				
		//3) Для кожної точки devisionPoints отримати пряму перпендикулярну основі base: devisionLines[u-1]
		List<Line> devisionLines = new ArrayList<>();
		for(Point p : devisionPoints){
			System.out.println(String.format("\nCreating perpendicular for devision point %s", p));
			devisionLines.add(ds.getBase().getLine().getPerprndicularAtPoint(p));
		}		
		
		//4) Для кожної з прямих devisionLines[m] для кожної з ліній на яких лежать відрізки formSegments[n] Визначити точки перетину які вони утвоюють...
		//Для кожної точки перетину визначити чи належить вона відрізку formSegments[n]
		for(Line dl : devisionLines){
			System.out.println(String.format("\nDevision line %s", dl));
			for(Segment segment : formSegments){
				System.out.print(String.format("\tFind intersection point with segment line %s:\t", segment));
				Point p = segment.getLine().getInterctionWithLine(dl);
				if(p == null){
					System.out.println(String.format("No intersection"));
					continue;
				}
				System.out.print(String.format("%s\t", p));
				if(segment.contains(p)){
					System.out.println(String.format("belongs to segment"));
					intersectionPoints.add(p);
				}else{
					System.out.println(String.format("does nto belongs to segment"));
				}
			}
		}
		
		for(int i = 0; i < ds.getDevidor(); i++){
			waypoints.add(devisionPoints.get(i));
			waypoints.add(intersectionPoints.get(i));
		}
	}

	public List<Point> getWaypoints() {
		return waypoints;
	}
}
