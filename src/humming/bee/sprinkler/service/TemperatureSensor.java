package humming.bee.sprinkler.service;

public class TemperatureSensor {

	private static int currentTemperature = 75;

	public static int getCurrentTemperature() {
		return currentTemperature;
	}

	public static void setCurrentTemperature(int currentTemperature) {
		TemperatureSensor.currentTemperature = currentTemperature;
	}
}
