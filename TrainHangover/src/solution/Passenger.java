package solution;

import simulation.CarManager;
import simulation.LightState;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

class Passenger{
	int position;
	int maxPosition;
	int minPosition;
	CarManager carManager;
	final List<CarImpl> cars;

	Passenger(CarManager carManager){
		this.carManager = carManager;
		position = maxPosition = 0;
		cars = new ArrayList<>();
//		System.out.println("Passenger's thoughts:");

		addNewCar(carManager.getLightState());
	}

	enum Direction{
		FORWARD(1), BACKWARD(-1);

		int vector;
		Direction(int vector) {
			this.vector = vector;
		}
	}

	boolean moveFurther(){
//		System.out.println("Now lets move to the next undiscovered car to the right");
		while(position <= maxPosition){
			if(!moveForward()){
//				System.out.println("The train has ended!");
				return false;
			}
		}
		maxPosition = position;
		addNewCar(carManager.getLightState());
		return true;
	}

	boolean moveFurther(Direction direction){
		if(direction.vector > 0){
			while(position <= maxPosition){
				if(!moveDirection(direction)){
					return false;
				}
			}
			maxPosition = position;
		} else {
			while(position >= minPosition){
				if(!moveDirection(direction)){
					return false;
				}
			}
			minPosition = position;
		}

		addNewCar(carManager.getLightState());
		return true;
	}

	void moveToStart(){
//		System.out.println("Return back to start");
		while(position > 0){
			moveBack();
		}
	}

	boolean moveDirection(Direction direction){
		boolean result;
		if(direction.vector > 0){
			result = carManager.moveNext();
		} else {
			result = carManager.movePrevious();
		}
		if(result) position += direction.vector;
		return result;
	}

	boolean moveForward(){
		boolean result;
		if(result = carManager.moveNext()) position++;
		return result;
	}

	boolean moveBack(){
		boolean result;
		if(result = carManager.movePrevious()) position--;
		return result;
	}

	public void showDiscoveredCars() {
		for(CarImpl car : cars){
			System.out.print(car);
		}
		System.out.println();
		for(CarImpl car : cars){
			System.out.print(format("|%-4d", car.carNumber));
		}
		System.out.println();
	}

	void addNewCar(LightState lightState){
		cars.add(new CarImpl(position, lightState));
//		System.out.print("Let give this car number: " + position);
//		System.out.println("; Lights here are " + lightState);
//		showDiscoveredCars();
	}

	public boolean checkLightsChanged() {
//		System.out.println("Checking if light state has changed");
		CarImpl car = cars.get(position);
		return carManager.getLightState() != car.lightState;
	}

	public void toggleLight() {
//		System.out.println("Toggle the light");
		carManager.toggleLight();
	}

	static class CarImpl{

		int carNumber;
		LightState lightState;
		CarImpl(int carNumber, LightState isLightOn){
			this.carNumber = carNumber;
			this.lightState = isLightOn;
		}
		public String toString(){
			return format("[ %s ]", lightState);
		}
	}
}