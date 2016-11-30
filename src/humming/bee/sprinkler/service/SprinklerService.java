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
	
	public List<GroupRunDuration> getGroupRunDuration() {
		return dbService.getGroupRunDuration();
	}
	

	/**
	 * Calls the method to Get all sprinklers by Group name
	 * @param groupName
	 * @return List
	 */
	public List<Sprinkler> getSprinklersByGroup(String groupName)
	{
		return dbService.getSprinklersByGroup(groupName);
	
	}
	
	/**
	 * Calls the method to Get details of a sprinkler
	 * @param sprinklerName
	 * @return Sprinkler
	 */
	public Sprinkler getSprinklerByName(String sprinklerName)
	{
		return dbService.getSprinklerByName(sprinklerName);
	}
	
	/**
	 * Calls the method to update sprinkler status to database
	 * @param sprinklerName
	 * @param status
	 */
	public void updateSprinklerStatus(String sprinklerName,String status)
	{
		dbService.updateSprinklerStatus(sprinklerName, status);
	}
	
	/**
	 * calls method to Update sprinkler to functional/not-functional
	 * @param sprinklerName
	 * @param functional
	 */
	public void updateSprinklerFunctional(String sprinklerName,int functional)
	{
		dbService.updateSprinklerFunctional(sprinklerName,functional);
	}
	
	
	//***********************sprinkler configuration*******************
	
	public List<SprinklerConfiguration> getSprinklerConfigurationByDay(int sprinklerId,String day)
	{	
		return dbService.getSprinklerConfigurationByDay(sprinklerId, day);
	}
	
	public int deleteSprinklerConfigById(int sConfigId)
	{
		return dbService.deleteSprinklerConfigById(sConfigId);
	}
	
	public boolean isSprinklerConfigExist(SprinklerConfiguration sConfig)
	{
		return dbService.isSprinklerConfigExist(sConfig);
	}
	
	
	//******************************************************************
	
	
	
	//***********************group configuration*******************
	
	public List<SprinklerGroupConfiguration> getGroupConfigurationByDay(int groupId,String day)
	{	
		return dbService.getGroupConfigurationByDay(groupId, day);
	}
	
	public int deleteGroupConfigById(int sConfigId)
	{
		return dbService.deleteGroupConfigById(sConfigId);
	}
	
	public boolean isGroupConfigExist(SprinklerGroupConfiguration gConfig)
	{
		return dbService.isGroupConfigExist(gConfig);
	}
	
	//**********************************************************************
	
	
	//*************************sprinkler run time************************
	
	public int addSprinklerRunTime(int sprinklerId)
	{
		return dbService.addSprinklerRunTime(sprinklerId);
	}
	
	public void updateSprinklerRunTime(int runTimeId)
	{
		dbService.updateSprinklerRunTime(runTimeId);
	}
	
	public SprinklerRunTime getSprinklerRunTimeToUpdate(int sprinklerId)
	{
		return dbService.getSprinklerRunTimeToUpdate(sprinklerId);
	}
	
	//******************************************************************
	
	//***********************sprinkler group run time*******************
	
	public int addGroupRunTime(int groupId)
	{
		return dbService.addGroupRunTime(groupId);
	}
	
	public void updateGroupRunTime(int gRunTimeId)
	{
		dbService.updateGroupRunTime(gRunTimeId);
	}
	
	public SprinklerGroupRunTime getGroupRunTimeToUpdate(int groupId)
	{
		return dbService.getGroupRunTimeToUpdate(groupId);
	}
	
}
