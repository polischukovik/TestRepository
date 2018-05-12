package reflection;


import simulation.CarManager;

import java.util.function.Function;

public class Utils {
	public static Function<CarManager,Boolean> getFirstAlgorithmAtClasspath() {
		ClassLoader loader = Utils.class.getClassLoader();
		System.out.println(loader.getResource("solution/AlgorithmImpl.class"));
		return null;
	}
}
