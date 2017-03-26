package geometry;

import tools.GoogleTools;

public class Vector {
	double a, b, c;
	double r, ϕ, θ;
	
//	public Vector(double a, double b, double c) {
//		super();
//		this.a = a;
//		this.b = b;
//		this.c = c;
//	}
//	
	public Vector(double r, double φ, double θ) {
		super();
		this.r = r;
		this.ϕ = φ;
		this.θ = θ;
	}

//	public Vector crossProduct(Vector other) {
//		a = this.b * other.c - this.c * other.b;
//		b = this.c * other.a - this.a * other.c;
//		c = this.a * other.b - this.b * other.a;
//
//		return new Vector(a, b, c);
//	}
	
	public Vector crossProduct(Vector other) {
		int r2 = GoogleTools.RADIUS;
		double ϕ1 = this.ϕ;
		double θ1 = this.θ;
		double ϕ2 = other.ϕ;
		double θ2 = other.θ;
		a = r2 * r2 * sin(θ1) * sin(ϕ1) * cos(ϕ2) - cos(ϕ1) * sin(ϕ2) * sin(θ2);
		b = r2 * r2 * (sin(θ2) - sin(θ1)) * cos(ϕ1) * cos(ϕ2);
		c = r2 * r2 * sin(θ1) * sin(θ2) * sin(ϕ2 - ϕ1);
		
		a = r2 * r2 * sin(ϕ1) * sin(θ1) * cos(θ2) - cos(θ1) * sin(θ2) * sin(ϕ2);
		b = r2 * r2 * (sin(ϕ2) - sin(ϕ1)) * cos(θ1) * cos(θ2);
		c = r2 * r2 * sin(ϕ1) * sin(ϕ2) * sin(θ2 - θ1);

		return new Vector(a, b, c);
	}
	
	public double dotProduct(Vector other) {
		//return this.a * other.a + this.b * other.b + this.c * other.c;
		int r2 = GoogleTools.RADIUS;
		double ϕ1 = this.ϕ;
		double θ1 = this.θ;
		double ϕ2 = other.ϕ;
		double θ2 = other.θ;
		return r2 * r2 * (sin(ϕ1) * sin(ϕ2) * cos(θ1-θ2) + cos(ϕ1) * cos(ϕ2));
		 
	}

	public boolean isZeroVector() {
		return a == 0 && b == 0 && c == 0;
	}
	
	public double getLength(){
		return Math.sqrt(this.a * this.a + this.b * this.b + this.c * this.c);
	}
	
	public double cos(double degree){
		return Math.cos(GeoPoint.toRad(degree));
	}
	
	public double sin(double degree){
		return Math.sin(GeoPoint.toRad(degree));
	}

	@Override
	public String toString() {
		return "Vector [r=" + r + ", ϕ=" + ϕ + ", θ=" + θ + "]";
	}
	
	
}
