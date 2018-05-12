package hw.lesson09.task1;

public class Test {
	public static void main(String[] args){
		Human justAHuman = new Human("Albert", 28, "male");
		System.out.println(justAHuman.print());
		
		System.out.println();
		
		Student student = new Student("Neil", 26, "male", "Cumputer science");
		student.singGaudeamus();
		System.out.println(student.print());

		System.out.println();
		
		Employee employee = new Employee("Susanne", 31, "female", "manager");
		System.out.println("Working shift started.");
		for(int i = 0; i < 8 ; i++){
			employee.work();
		}
		System.out.println("Working shift ended.");
		System.out.println(employee.print());
		
		System.out.println();
		
		Politic politic = new Politic("Howard", 52, "male");
		System.out.println("Elections started.");
		for(int i = 0; i < 28 ; i++){
			politic.convince();
		}
		System.out.println("Elections ended.");
		System.out.println(politic.print());
		
		System.out.println();
		
		Human human;
		human = student;
		System.out.println(human.print());
		
		human = employee;
		System.out.println(human.print());
		
		human = politic;
		System.out.println(human.print());
	}
}
