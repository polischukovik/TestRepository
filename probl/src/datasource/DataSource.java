package datasource;

import java.util.List;

import geometry.GeoPoint;
import geometry.Segment;

public class DataSource{

	private List<GeoPoint> formPoints;
	private Segment base;
	private int devidor;
	
	
	public DataSource(List<GeoPoint> formPoints, Segment base, int devidor) {
		super();
		this.formPoints = formPoints;
		this.base = base;
		this.devidor = devidor;
		
		if(formPoints == null || base == null || devidor == 0) 
			throw new IllegalArgumentException("DataSource is not ready");
	}
	public List<GeoPoint> getFormPoints() {
		return formPoints;
	}
	public void setFormPoints(List<GeoPoint> formPoints) {
		this.formPoints = formPoints;
	}
	public Segment getBase() {
		return base;
	}
	public void setBase(Segment base) {
		this.base = base;
	}
	public int getDevidor() {
		return devidor;
	}
	public void setDevidor(int devidor) {
		this.devidor = devidor;
	}
	@Override
	public String toString() {
		return "DataSourceImpl [formPoints=" + formPoints + ", base=" + base + ", devidor=" + devidor + "]";
	}	
	
	
}
