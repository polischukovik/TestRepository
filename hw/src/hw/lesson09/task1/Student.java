package hw.lesson09.task1;

public class Student extends Human {
	
	private String faculty;

	public Student(String name, int age, String gender, String faculty) {
		super(name, age, gender);
		this.faculty = faculty;
	}

	public void singGaudeamus(){
		String lyrics = "Gaudeamus igitur,\n"
				+ "Juvenes dum sumus!\n"
				+ "Post jucundam juventutem,\n"
				+ "Post molestam senectutem\n"
				+ "Nos habebit humus!";
		System.out.println(lyrics);
	}

	public String getFaculty() {
		return faculty;
	}

	public String print() {
		return "Student: (" + super.print() + ") faculty=" + this.getFaculty();
	}	
}
