package hw.lesson09.task1;

public class Employee extends Human {
	private String occupation;
	private double account;

	public Employee(String name, int age, String gender, String occupation) {
		super(name, age, gender);
		this.occupation = occupation;
		this.account = .0;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	public double getAccount() {
		return account;
	}

	public void work(){
		System.out.println("Working as " + this.getOccupation() + " earning 0.25$");
		account += .25;
	}

	public String print() {
		return "Employee: (" + super.print() + ") occupation=" + this.getOccupation() + " account=" + this.getAccount() + "$";
	}
}
