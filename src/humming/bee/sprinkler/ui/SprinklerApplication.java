package humming.bee.sprinkler.ui;

import java.util.Timer;

public class SprinklerApplication {
	
	public static void main(String[] args) {
		HomeScreen.startHomeScreen();
		SprinklerController controller = new SprinklerController();
		Timer timer = new Timer();
		timer.schedule(controller, 5000, 3000);
	}

}
