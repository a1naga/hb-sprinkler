package humming.bee.sprinkler.service;

public class TemperatureSensor {

	private int currentTempearture = 75;

	public int getCurrentTempearture() {
		return currentTempearture;
	}

	public void setCurrentTempearture(int currentTempearture) {
		this.currentTempearture = currentTempearture;
	}
}
