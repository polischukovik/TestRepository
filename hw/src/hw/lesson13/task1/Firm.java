package hw.lesson13.task1;

import java.util.ArrayList;

public class Firm {
	
	private String name;
	private String address;
	private double account;
	
	public double getAccount() {
		return account;
	}

	private ArrayList<Employee> employees;
	
	public Firm(String name, String address, double account) {
		super();
		this.name = name;
		this.address = address;
		this.account = account;
		this.employees = new ArrayList<>();
	}

	public void printAllEmployees(){
		printAll(this.employees);
	}

	public void printAllEmployeesBySalary(){
		ArrayList<Employee> sorted = new ArrayList<>(this.employees);
		
		int n = sorted.size();
		boolean swapped = false;

		do{
			swapped = false;
			for(int i = 1; i < n; i++){
	            if(sorted.get(i-1).getSalary() > sorted.get(i).getSalary()){
	            	Employee tmp = sorted.get(i-1);
	            	sorted.set(i-1, sorted.get(i));
	            	sorted.set(i, tmp);
	            	
	            	swapped = true;
	            }
	        }
		}while(swapped);
		
		printAll(sorted);
	}
	
	private void printAll(ArrayList<Employee> employees) {
		for(Employee employee : employees){
			employee.print();
		}
	}
	
	public void paySalaryForAll(){
		for(Employee employee : employees){
			this.paySalary(employee);
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
		for(Employee employee : employees){
			System.out.println(employee.getName() + " " + employee.getSurname() + " has vacation of " + employee.getVacations() + " days");
		}
	}
	
	public void print(){
		System.out.println("Firm " + this.name + " Ltd. address " + this.address + " has " + employees.size() + " employees");
	}

	public void hire(Employee employee, String departament, double salary, int startYear, int startMonth) {
		if(startMonth > 12 && startMonth < 0){
			System.out.println("Wrong month");
		}
		employee.setWorkingSinceYear(startYear);
		employee.setWorkingSinceMonth(startMonth);
		employee.setSalary(salary);
		employee.setDepartament(departament);
		
		this.employees.add(employee);
	}

}
