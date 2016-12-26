package graphics;

import geometry.Point;

public class Map{
	private Point start, end;

	public Map(Point start, Point end) {
		super();
		this.start = start;
		this.end = end;
	}

	public Point getStart() {
		return start;
	}

	public void setStart(Point start) {
		this.start = start;
	}

	public Point getEnd() {
		return end;
	}

	public void setEnd(Point end) {
		this.end = end;
	}

}
