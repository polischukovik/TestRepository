package geometry;

import java.util.Comparator;

import calculator.App;

public class Point {
	public static final Point HOME = new Point(50.392621, 30.496226);
	
	private double latitude;
	private double longitude;
	public Point(double latitude, double longitude) {
		super();
		this.latitude = round(latitude,App.COORDINATE_PRECISION);
		this.longitude = round(longitude,App.COORDINATE_PRECISION);
	}
	
	/*
	 * d=sqlrt((x2−x1)^2+(y2−y1)^2)
	 */
//	public double distanceTo(Point p){
//		App.log.info(this.getClass(), String.format("    Finding distance from %s to %s", this, p));		
//		double d = round(Math.sqrt(Math.pow((p.getX() - this.x), 2) + Math.pow((p.getY() - this.y), 2)), App.COORDINATE_PRECISION);
//		App.log.info(this.getClass(), String.format("    Distance is sqlrt((x2−x1)^2+(y2−y1)^2) = %f", d));
//		return d;
//	}
	
	public double distanceTo(Point p){
		
		double R = 6371000; // metres
		double φ1 = Math.toRadians(this.getLatitude());
		double φ2 = Math.toRadians(p.getLatitude());
		double Δφ = Math.toRadians(p.getLatitude() - this.getLatitude());
		double Δλ = Math.toRadians(p.getLongitude() - this.getLongitude());

		double a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
		        Math.cos(φ1) * Math.cos(φ2) *
		        Math.sin(Δλ/2) * Math.sin(Δλ/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

		double d = R * c;
		
		return d;
	}
	
	public Point moveTo(double direction, double distance){
		double tc = direction;
		double lat =Math.asin(Math.sin(this.getLatitude())*Math.cos(distance)+Math.cos(this.getLatitude())*Math.sin(distance)*Math.cos(tc));
		double dlon=Math.atan2(Math.sin(tc)*Math.sin(distance)*Math.cos(this.getLatitude()),Math.cos(distance)-Math.sin(this.getLatitude())*Math.sin(lat));
		double lon= mod( (this.getLatitude()-dlon +Math.PI), 2*Math.PI) - Math.PI;
		return new Point(this.getLatitude() + lat, this.getLongitude() + lon);
	}
	
	private double mod(double y, double x){
		double mod = y - x * (int)(y/x); ///mood
		if(mod < 0){
			mod = mod + x;
		}
		return mod;
	}

	public static Comparator<Point> getPointNameComparator(Line base){
		return new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				Point intersection = base.getInterctionWithLine(base.getPerprndicularAtPoint(o1));
				if(!intersection.equals(base.getInterctionWithLine(base.getPerprndicularAtPoint(o2)))){
					App.log.info(this.getClass(), "ERRRRRRRRRRRRRRRRRRRRRRRROOOOOOOOOOOOOOOOOOOOOOOOOOOOR");
				}
				double diff = o1.distanceTo(intersection) - o2.distanceTo(intersection);
				return diff < 0 ? -1 : diff > 0 ? 1 : 0;
			}
		};
	}
	
//	public Point translateCoordinates(final double distance, final Point origpoint, final double angle) {
//        final double distanceNorth = Math.sin(angle) * distance;
//        final double distanceEast = Math.cos(angle) * distance;
//
//        final double earthRadius = 6371000;
//
//        final double newLat = origpoint.latitude + (distanceNorth / earthRadius) * 180 / Math.PI;
//        final double newLon = origpoint.longitude + (distanceEast / (earthRadius * Math.cos(newLat * 180 / Math.PI))) * 180 / Math.PI;
//
//        return new Point(newLat, newLon);
//}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	
	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		return true;
	}

	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	@Override
	public String toString() {
		return "Point [" + latitude + ", " + longitude + "]";
	}

	public int relatesTo(Point a, Segment base) {
		if(this.equals(a)){
			return 0;
		}
		
		if(this.distanceTo(base.getA()) + this.distanceTo(base.getB()) > 
			a.distanceTo(base.getA()) + a.distanceTo(base.getB())){
			return 1;
		}else{
			return -1;
		}
	}
}
