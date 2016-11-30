package humming.bee.sprinkler.service;

import humming.bee.sprinkler.db.SprinklerDb;

public class SprinklerGroup {

	private SprinklerDb dbService=new SprinklerDb();
	
	
	private int groupId;
	private String groupName;
	private String status;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	/**
	 * Calls the method to Get sprinkler-group by group name
	 * @param groupName
	 * @param status
	 */
	public SprinklerGroup getSprinklerGroupByName(String groupName)
	{
		return dbService.getSprinklerGroupByName(groupName);
	}
	
	
	/**
	 * Calls method to Update status of a sprinkler-group (on/off) to database
	 * @param groupName
	 * @param status
	 */
	public void updateSprinklerGroupStatus(String groupName,String status)
	{
		dbService.updateSprinklerGroupStatus(groupName, status);
	}

}
