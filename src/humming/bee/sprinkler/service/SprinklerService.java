package humming.bee.sprinkler.service;

import java.util.List;

import humming.bee.sprinkler.db.SprinklerDb;
import humming.bee.sprinkler.ui.HomeScreen;

public class SprinklerService {

	private SprinklerDb dbService = new SprinklerDb();
	

	/*public List<Sprinkler> getStatus() {

		return dbService.getAllStatus();
	}*/

	public List<Sprinkler> getSprinkler() {
		return dbService.getSprinkler();
	}

	public List<SprinklerGroup> getGroup() {
		return dbService.getGroup();
	}

	public List<OverrideTemperatureConfiguration> temperatureConfiguration() {
		return dbService.getTemperatureConfiguration();
	}
	
	public void setTemperatureConfiguration(OverrideTemperatureConfiguration tempConfiguration) {
		 dbService.setTemperatureConfiguration(tempConfiguration);
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

	/**
	 * Calls the method to Get all sprinklers by Group name
	 * 
	 * @param groupName
	 * @return List
	 */
	public List<Sprinkler> getSprinklersByGroup(String groupName) {
		return dbService.getSprinklersByGroup(groupName);

	}

	/**
	 * Calls the method to Get details of a sprinkler
	 * 
	 * @param sprinklerName
	 * @return Sprinkler
	 */
	public Sprinkler getSprinklerByName(String sprinklerName) {
		return dbService.getSprinklerByName(sprinklerName);
	}

	/**
	 * Calls the method to update sprinkler status to database
	 * 
	 * @param sprinklerName
	 * @param status
	 */
	public void updateSprinklerStatus(String sprinklerName, String status) {
		dbService.updateSprinklerStatus(sprinklerName, status);
	}

	/**
	 * calls method to Update sprinkler to functional/not-functional
	 * 
	 * @param sprinklerName
	 * @param functional
	 */
	public void updateSprinklerFunctional(String sprinklerName, int functional) {
		dbService.updateSprinklerFunctional(sprinklerName, functional);
	}

	// ***********************sprinkler configuration*******************

	public List<SprinklerConfiguration> getSprinklerConfigurationByDay(int sprinklerId, String day) {
		return dbService.getSprinklerConfigurationByDay(sprinklerId, day);
	}

	public int deleteSprinklerConfigById(int sConfigId) {
		return dbService.deleteSprinklerConfigById(sConfigId);
	}

	// ******************************************************************

}
