package hw.lesson08.task1;

import java.util.Random;

public class MainBus {

	public static void main(String[] args) {
		Random random = new Random();
		
		Bus bus = new Bus();
		for(int i = 0; i < 10; i++){
			bus.addHuman((int) (11 * (2 * Math.random() - 1)));
			System.out.println();
		}
	}

}
