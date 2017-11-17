package hw.lesson10.task1;

public class Employee {
	private String name;
	private String surname;
	private double salary;
	private double account;
	private int workingSinceYear;
	private int workingSinceMonth;
	private String gender;
	private String departament;
	public Employee(String name, String surname, double account, String gender) {
		super();
		this.name = name;
		this.surname = surname;
		this.account = account;
		this.gender = gender;
	}
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public double getSalary() {
		return salary;
	}
	
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public void setWorkingSinceYear(int year) {
		this.workingSinceYear = year;
	}
	
	public void setWorkingSinceMonth(int month) {
		this.workingSinceMonth = month;
	}
	
	public String getGender() {
		return gender;
	}
	
	public String getDepartament() {
		return departament;
	}
	
	public void setDepartament(String departament) {
		this.departament = departament;
	}

	public void print() {
		String text = "Employee: name=" + name + ", surname=" + surname + ", salary=" + salary + ", account=" + account
				+ ", gender=" + gender + ", departament=" + departament;
		System.out.println(text);
	}
	
	public void debit(double salary) {
		this.account += this.salary;
	}
	public int getVacations() {
		int month = Calendar.month - this.workingSinceMonth + (Calendar.year - this.workingSinceYear) * 12;
		if(month >= 6){
			return month * 2;
		}
		return 0;
	}

}
