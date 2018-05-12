package simulation;

import java.util.*;

import static java.lang.String.format;

public final class CarManagerImpl implements CarManager{
	private final int numberOfCars;
	private final boolean isEnclosed;
	private final int starCarNo;

	private final LinkedList<Car> cars = new LinkedList<>();
	private Car currentCar;
	private int numberOfCarsPassed;

	private CarManagerImpl(int numOfCars, boolean isEnclosed, int startCarNo){
		this.numberOfCars = numOfCars;
		this.isEnclosed = isEnclosed;
		this.starCarNo = startCarNo;
		this.numberOfCarsPassed = 0;
	}

	public static CarManager buildAndMoveToCarNo(int numOfCars, boolean isEnclosed, int startCarNo){
		if(numOfCars < 2) throw new IllegalArgumentException("Train is too small: minimum 2 cars");
		if(startCarNo < 0 || startCarNo > numOfCars - 1) throw new IllegalArgumentException("Requested car is beyond train");
		CarManagerImpl carManagerImpl = new CarManagerImpl(numOfCars, isEnclosed, startCarNo);

		Random r = new Random();

		for (int i = 0; i < numOfCars; i++) {
			Car car = new Car(r.nextBoolean());
			Car prevCar = null;

			if(carManagerImpl.cars.size() != 0){
				prevCar = carManagerImpl.cars.getLast();
				prevCar.next = car;
			}

			car.previous = prevCar;

			carManagerImpl.cars.add(car);

			if(i == startCarNo) carManagerImpl.currentCar = car;
		}

		if(isEnclosed) {
			Car firstCar = carManagerImpl.cars.getFirst();
			Car lastCar = carManagerImpl.cars.getLast();
			firstCar.previous = lastCar;
			lastCar.next = firstCar;
		}

//		carManagerImpl.showTrain();

		return carManagerImpl;
	}

	private void showTrain() {
		System.out.println(format("Passenger wakes up in%s enclosed train in car number %d", isEnclosed ? "" : " not", starCarNo));
		System.out.print(isEnclosed ? "-" : "#");

		for(Car c : cars){
			System.out.print(format("[%s]", c.lightState));
		}
		System.out.println(isEnclosed ? "-" : "#");
		System.out.println();
	}

	@Override
	public void toggleLight() {
		currentCar.togglelight();
	}

	@Override
	public LightState getLightState() {
		return currentCar.lightState;
	}

	@Override
	public boolean moveNext() {
		if(currentCar.next == null){
			return false;
		}
		currentCar = currentCar.next;
		numberOfCarsPassed++;
		return true;
	}

	@Override
	public boolean movePrevious() {
		if(currentCar.previous == null){
			return false;
		}
		currentCar = currentCar.previous;
		numberOfCarsPassed++;
		return true;
	}

	@Override
	public int getCarCount() {
		return numberOfCarsPassed;
	}

	private static final class Car{
		private LightState lightState;
		public Car next;
		public Car previous;

		Car(boolean lightsOn){
			this.lightState = LightState.valueOf(lightsOn);
		}

		public void togglelight(){
			lightState = lightState.inverse();
		}
	}
}
