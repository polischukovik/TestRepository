package probl;

import java.io.IOException;

import datasource.FileDS;
import geometry.Point;
import logic.WaypointFinder;

public class App {
	public static void main(String[] args) throws IOException{		
		WaypointFinder waypointFinder = new WaypointFinder(new FileDS("ds.txt"));
		
		for(Point p : waypointFinder.getWaypoints()){
			System.out.println(p);
		}
	}
}
