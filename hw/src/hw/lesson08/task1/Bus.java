package hw.lesson08.task1;

public class Bus {

	private int countHuman = 0;
	
	public void addHuman(int numberOfPassangers){
		System.out.println("Changing the number of passangers: " + numberOfPassangers);
		int total = this.getCountHuman() + numberOfPassangers;
		total = total < 0 ? 0 : total;
		this.setCountHuman(total);
		printAboutMe();
	}
	
	public void printAboutMe(){
		System.out.println("Bus contains " + this.getCountHuman() + " passangers");
	}

	public int getCountHuman() {
		return countHuman;
	}

	public void setCountHuman(int countHuman) {
		this.countHuman = countHuman;
	}
}
