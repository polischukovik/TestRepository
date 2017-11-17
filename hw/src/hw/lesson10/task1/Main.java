package hw.lesson10.task1;

public class Main {

	public static void main(String[] args) {
		Firm firm = new Firm("Roga & Kopyta", "Morgenshtrasse 11, 25", 8325000);
		
		Employee employeeOne = new Employee("Ban", "Stuard", 124, "male");
		Employee employeeTwo = new Employee("Sarra", "Miller", 800, "female");
		Employee employeeThree = new Employee("Trevor", "Noah", 9800.25, "male");
		

		firm.hire(employeeThree, "Sales", 4321.97, 2016, 11);
		firm.hire(employeeOne, "Security", 2512.06, 2017, 6);
		firm.hire(employeeTwo, "Management", 3250.75, 2013, 12);
		
		firm.print();
		System.out.println("Employees:");
		firm.printAllEmployees();
		
		System.out.println();
		
		System.out.println("Employees sorted by selaries:");
		firm.printAllEmployeesBySalary();
		
		firm.paySalaryForAll();
		
		System.out.println();
		
		System.out.println("After salary:");
		firm.printAllEmployees();
		
		System.out.println();
		
		System.out.println("Vacations");
		firm.showVacationsForAll();
	}

}
