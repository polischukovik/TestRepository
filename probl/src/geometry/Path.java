package geometry;

import java.util.List;

public class Path implements Displayable{
	private List<GeoPoint> waypoints;

	public Path(List<GeoPoint> waypoints) {
		this.waypoints = waypoints;
	}

	public List<GeoPoint> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(List<GeoPoint> waypoints) {
		this.waypoints = waypoints;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((waypoints == null) ? 0 : waypoints.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Path other = (Path) obj;
		if (waypoints == null) {
			if (other.waypoints != null)
				return false;
		} else if (!waypoints.equals(other.waypoints))
			return false;
		return true;
	}
	
	
	
}
