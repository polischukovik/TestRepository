package solution;

import simulation.CarManager;

import java.util.function.Function;

import static solution.Passenger.Direction.FORWARD;

public class AnotherAlgorithm implements Function<CarManager, Boolean> {
	@Override
	public Boolean apply(CarManager carManager) {
		Passenger passenger = new Passenger(carManager);

		boolean lightsChanged = false;
		boolean hasNextCar;
		Passenger.Direction direction = FORWARD;
		while(!lightsChanged) {
			hasNextCar = passenger.moveFurther();
			if(!hasNextCar) return false;
			passenger.toggleLight();
			passenger.moveToStart();

			lightsChanged = passenger.checkLightsChanged();
		}
		return true;
	}
}
