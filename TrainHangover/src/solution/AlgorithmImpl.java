package solution;

import simulation.CarManager;

import java.util.function.Function;

public class AlgorithmImpl implements Function<CarManager, Boolean> {
	@Override
	public Boolean apply(CarManager carManager) {
		Passenger passenger = new Passenger(carManager);

		boolean lightsChanged = false;
		boolean hasNextCar;
		while(!lightsChanged) {
			hasNextCar = passenger.moveFurther();
			if(!hasNextCar) return false;
			passenger.toggleLight();
			passenger.moveToStart();

			lightsChanged = passenger.checkLightsChanged();
			if(lightsChanged){
//				System.out.println("The light state has changed, so I was here before!");
			} else {
//				System.out.println("Nothing changed. Lets continue...\n");
			}

		}
		return true;
	}
}
