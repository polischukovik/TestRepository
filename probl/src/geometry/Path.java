package geometry;

import java.util.List;

public class Path {
	private List<Point> waypoints;

	public Path(List<Point> waypoints) {
		this.waypoints = waypoints;
	}

	public List<Point> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(List<Point> waypoints) {
		this.waypoints = waypoints;
	}
	
	
	
}
