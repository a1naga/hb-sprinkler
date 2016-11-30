package humming.bee.sprinkler.ui;

import java.util.Timer;

public class SprinklerApplication {
	
	public static void main(String[] args) {
		long updateDuration = 5000;
		HomeScreen.startHomeScreen();
		SprinklerController controller = new SprinklerController(updateDuration);
		Timer timer = new Timer();
		timer.schedule(controller, updateDuration, updateDuration);
	}

}
