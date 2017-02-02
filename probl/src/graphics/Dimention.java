package graphics;

import geometry.Point;
import geometry.Segment;

public class Dimention extends Segment{
	private Segment vOvf, hOvf;
	
	public Dimention() {
		super();
	}

	public Dimention(Point a, Point b, Segment hOvf, Segment vOvf) {
		super(a, b);
		this.hOvf = hOvf;
		this.vOvf = vOvf;
	}

	public Segment getvOvf() {
		return vOvf;
	}

	public void setvOvf(Segment vOvf) {
		this.vOvf = vOvf;
	}

	public Segment gethOvf() {
		return hOvf;
	}

	public void sethOvf(Segment hOvf) {
		this.hOvf = hOvf;
	}
	
	public Point getSW(){
		return this.getA();
	}
	
	public Point getNE(){
		return this.getB();
	}

}
