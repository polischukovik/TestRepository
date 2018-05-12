package simulation;

import solution.AlgorithmImpl;

import java.util.Random;
import java.util.function.Function;

import static java.lang.String.format;

public class Main {

	public static void main(String[] args) {
		Random r = new Random();
		int startPosition = 0;
		for (int i = 2; i < 100; i++) {
			if (!runTest(i, false, startPosition)) {
				System.out.println("Algorithm is wrong");
			}
		}
	}

	private static boolean runTest(int numberOfCars, boolean isEnclosed, int startPosition) {
		CarManager carManager = CarManagerImpl.buildAndMoveToCarNo(numberOfCars, isEnclosed, startPosition);
		Function<CarManager, Boolean> algorithm = new AlgorithmImpl();
		Boolean answer = algorithm.apply(carManager);
		if(answer == null) throw new IllegalArgumentException("Answer cannot be null");
		System.out.println(format("%d %d", numberOfCars, carManager.getCarCount()));
		return isEnclosed == answer;
	}
}

//		Class<?> clazz = null;
//		Function<Car, Boolean> algorithm = null;
//		try {
//			clazz = Class.forName("AlgorithmImpl");
//			algorithm = (Function<Car, Boolean>) clazz.newInstance();
//		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
//			e.printStackTrace();
//		}
//		System.out.println(format("\nThe train is%s enclosed. Your answer is%s enclosed - %s"
//				,IS_ENCLOSED ? "" : " not" , answer ? "" : " not", IS_ENCLOSED == answer ? "Correct" : "Wrong"));
//		System.out.println(format("Passenger passed %d cars", carManager.getCarCount()));
