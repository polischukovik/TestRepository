package domains;

import java.util.ArrayList;
import java.util.List;

import geometry.Point;

public class Fields {
	private static List<Field> fields = new ArrayList<>();
	
	static{
		List<Point> pointList = new ArrayList<>();
		pointList.add(Point.parsePoint("49.856666, 30.122131"));
		pointList.add(Point.parsePoint("49.855485, 30.121552"));
		pointList.add(Point.parsePoint("49.856100, 30.117736"));
		pointList.add(Point.parsePoint("49.856143, 30.116169"));
		pointList.add(Point.parsePoint("49.856625, 30.116229"));
		pointList.add(Point.parsePoint("49.856646, 30.115896"));
		pointList.add(Point.parsePoint("49.857538, 30.115832"));		
		fields.add(new Field("Терезино №603", pointList));
		
		pointList = new ArrayList<>();
		pointList.add(Point.parsePoint("49.851887, 30.120772"));
		pointList.add(Point.parsePoint("49.848890, 30.137644"));
		pointList.add(Point.parsePoint("49.854177, 30.139869"));
		pointList.add(Point.parsePoint("49.856543, 30.122645"));		
		fields.add(new Field("Терезино №598", pointList));
		
		pointList = new ArrayList<>();
		pointList.add(Point.parsePoint("50.082687, 30.039135"));
		pointList.add(Point.parsePoint("50.080071, 30.040690"));
		pointList.add(Point.parsePoint("50.076592, 30.027692"));
		pointList.add(Point.parsePoint("50.078366, 30.024730"));
		pointList.add(Point.parsePoint("50.080584, 30.032966"));
		pointList.add(Point.parsePoint("50.081290, 30.033961"));		
		fields.add(new Field("Велика Снітинка №153", pointList));
			
	}
	
	public static class Field{
		private String name;
		private List<Point> fieldPoints;
		
		public Field(String name, List<Point> fieldPoints) {
			this.name = name;
			this.fieldPoints = fieldPoints;
		}
		public String getName() {
			return name;
		}
		public List<Point> getFieldPoints() {
			return fieldPoints;
		}
		@Override
		public String toString() {
			return name;
		}
		
	}

	public static List<Field> getFields() {
		return fields;
	}
}
