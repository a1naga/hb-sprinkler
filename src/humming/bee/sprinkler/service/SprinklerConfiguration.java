/**
 * 
 */
package humming.bee.sprinkler.service;

import humming.bee.sprinkler.db.SprinklerDb;

import java.util.List;

/**
 * 
 *
 */
public class SprinklerConfiguration extends AbstractConfiguration {
	
	public SprinklerConfiguration()
	{
		
	}
	
	//data members
	private int sprinklerId;
	
	
	//getters and setters
	public int getSprinklerId() {
		return sprinklerId;
	}

	public void setSprinklerId(int sprinklerId) {
		this.sprinklerId = sprinklerId;
	}
	
	
	
	//methods

	
	public List<SprinklerConfiguration> getConfiguration(String sprinklerId) {
		// TODO Auto-generated method stub
		
		SprinklerDb dbService = new SprinklerDb();
		return dbService.getConfiguration(sprinklerId);
		
	}
	
	public int addSprinklerConfiguration(List<SprinklerConfiguration> newSprinklerConfigList)
	{
		int i=0;
		SprinklerDb dbService = new SprinklerDb();
		//add all values in list to db
		for(SprinklerConfiguration sConfig:newSprinklerConfigList)
		{
			i=dbService.addSprinklerConfiguration(sConfig);
		}
		return i;
	}
	
	
	
	
	

}
