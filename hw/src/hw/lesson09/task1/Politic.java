package hw.lesson09.task1;

public class Politic extends Human {
	private double popularity;

	public Politic(String name, int age, String gender) {
		super(name, age, gender);
		this.popularity = .0;
	}

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public void convince(){
		System.out.println("Gaining 1%");
		this.setPopularity(this.getPopularity() + 1);
	}
	
	@Override
	public String print() {
		return "Politic: (" + super.print() + ") popularity=" + this.getPopularity() + "%";
	}
	
	
}
