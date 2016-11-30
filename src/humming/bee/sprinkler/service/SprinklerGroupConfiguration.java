package humming.bee.sprinkler.service;

import humming.bee.sprinkler.db.SprinklerDb;

import java.util.List;


/**
 * 
 *
 */
public class SprinklerGroupConfiguration extends AbstractConfiguration{
	
	
	
	//Constructor
	public SprinklerGroupConfiguration()
	{
		
	}
	
	//data members
	private int groupId;
	
	
	//getters and setters
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	
	
	//methods
	
	
	
	
	public int addGroupConfiguration(List<SprinklerGroupConfiguration> newGroupConfigList)
	{
		int i=0;
		SprinklerDb dbService = new SprinklerDb();
		//add all values in list to db
		for(SprinklerGroupConfiguration gConfig:newGroupConfigList)
		{
			i=dbService.addGroupConfiguration(gConfig);
		}
		return i;
	}

}
