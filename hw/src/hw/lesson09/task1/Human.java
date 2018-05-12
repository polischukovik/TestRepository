package hw.lesson09.task1;
//1. Создайте класс описывающий человека, поля и методы которые характеризуют  человека, придумайте сами (создайте метод выводящий информацию о человеке)
//2. На его основе созданного класса человека, создайте  класс студент, добавтье  поля и методы для студента (переопределите метод вывода информации)
//3. На основе класса человек, создайте клаас сотрудник, добавтье  поля и методы для сотрудника (переопределите метод вывода информации)
//4. На основе класса человек, создайте класс Политик, добавтье поля и методы для политика (переопределите метод вывода информации)
//5. Создать класс Test  и  создать  студента, сотрудника, политика и проверить методы.

public class Human {
	private String name;
	private int age;
	private String gender;
	
	public Human(String name, int age, String gender) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String print(){
		return "Human: name=" + name + ", age=" + age + ", gender=" + gender;
	}
	
}
