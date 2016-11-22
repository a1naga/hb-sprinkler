package humming.bee.sprinkler.service;

import java.util.ArrayList;
import java.util.List;

import humming.bee.sprinkler.db.SprinklerDb;

public class SprinklerService {

	public List<Sprinkler> getStatus() {
		SprinklerDb dbService = new SprinklerDb();
		return dbService.getAllStatus();
	}
	// System.out.println(dbService.getAllStatus());

	/*
	 * public void getStatus(int sprinklerId, int groupId);
	 * 
	 * public void getGroupStatus(String groupId) {
	 * 
	 * }
	 * 
	 * public List<SprinklerGroupConfiguration> getGroupConfiguration(String
	 * groupId) { return null; }
	 * 
	 * public void updateGroupConfiguration(List<SprinklerGroupConfiguration>
	 * sprinklerList) {
	 * 
	 * }
	 * 
	 * public List<SprinklerConfiguration> getSprinklerConfiguraion(String
	 * SprinklerId, String groupId) { return null; }
	 * 
	 * public void updateSprinklerConfiguration(List<SprinklerConfiguration>
	 * sprinklerList) {
	 * 
	 * }
	 * 
	 * public TemperatureConfiguration temperatureConfiguration() { return null;
	 * }
	 */
	/*
	 * public void overrideTemperature(TemperatureConfiguration tempConfig) {
	 * 
	 * }
	 * 
	 * public void updateTemperature() {
	 * 
	 * }
	 * 
	 * public double getGroupWaterConsumption(String groupId) { return 0.0; }
	 * 
	 * public double getSprinklerWaterConsumption(String sprinklerId) { return
	 * 0.0; }
	 */
}
