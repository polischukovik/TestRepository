package datasource;

import java.io.File;
import java.util.List;

import geometry.Point;
import geometry.Segment;

public interface DataSource {
	public List<Point> getFormPoints();

	public void setFormPoints(List<Point> formPoints) ;

	public Segment getBase() ;

	public void setBase(Segment base);

	public int getDevidor() ;

	public void setDevidor(int devidor) ;

	public File getPath();

	public void setPath(File path);
	public boolean isValid();
}
