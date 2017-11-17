package hw.lesson10.task1;

public class Firm {
	
	private String name;
	private String address;
	private double account;
	
	public double getAccount() {
		return account;
	}

	private Employee[] employees;
	
	public Firm(String name, String address, double account) {
		super();
		this.name = name;
		this.address = address;
		this.account = account;
		this.employees = new Employee[0];
	}

	public void printAllEmployees(){
		printAll(this.employees);
	}

	public void printAllEmployeesBySalary(){
		Employee[] sorted = new Employee[this.employees.length];
		System.arraycopy(this.employees, 0, sorted, 0, this.employees.length);
		
		int n = sorted.length;
		boolean swapped = false;

		do{
			swapped = false;
			for(int i = 1; i < n; i++){
	            if(sorted[i-1].getSalary() > sorted[i].getSalary()){
	            	Employee tmp = sorted[i-1];
	            	sorted[i-1] = sorted[i];
	            	sorted[i] = tmp;
	            	
	            	swapped = true;
	            }
	        }
		}while(swapped);
		
		printAll(sorted);
	}
	
	private void printAll(Employee[] employees) {
		for(int i = 0; i < employees.length; i++){
			employees[i].print();
		}
	}
	
	public void paySalaryForAll(){
		for(int i = 0; i < employees.length; i++){
			this.paySalary(employees[i]);
		}
	}
	
	private void paySalary(Employee employee) {
		employee.debit(employee.getSalary());
		this.credit(employee.getSalary());
	}

	private void credit(double salary) {
		this.account -= salary;
	}

	public void showVacationsForAll(){
		for(int i = 0; i < employees.length; i++){
			System.out.println(employees[i].getName() + " " + employees[i].getSurname() + " has vacation of " + employees[i].getVacations() + " days");
		}
	}
	
	public void print(){
		System.out.println("Firm " + this.name + " Ltd. address " + this.address + " has " + employees.length + " employees");
	}

	public void hire(Employee employee, String departament, double salary, int startYear, int startMonth) {
		if(startMonth > 12 && startMonth < 0){
			System.out.println("Wrong month");
		}
		employee.setWorkingSinceYear(startYear);
		employee.setWorkingSinceMonth(startMonth);
		employee.setSalary(salary);
		employee.setDepartament(departament);
		
		Employee[] temp = this.employees; 
		this.employees = new Employee[temp.length + 1];
		System.arraycopy(temp, 0, this.employees, 0, temp.length);
		
		this.employees[this.employees.length - 1] = employee;
	}

}
