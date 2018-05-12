package simulation;

public enum LightState {
	ON("I"), OFF("O");

	private final String str;
	private LightState inverse;

	LightState(String str){
		this.str = str;
	}

	@Override
	public String toString() {
		return this.str;
	}

	public static LightState valueOf(boolean lightsOn) {
		return lightsOn ? ON : OFF;
	}

	public LightState inverse() {
		return inverse;
	}
}
