package simulation;

public interface CarManager {
	LightState getLightState();
	void toggleLight();
	boolean moveNext();
	boolean movePrevious();
	int getCarCount();
}
