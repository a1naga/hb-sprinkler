package humming.bee.sprinkler.service;

public class OverrideTemperatureConfiguration {
	
	int id;
	int tempUpperLimit;
	int tempLowerLimit;
	int frequency;
	int duration;
	int groupId;
	
	
	public int getTempUpperLimit() {
		return tempUpperLimit;
	}
	public void setTempUpperLimit(int tempUpperLimit) {
		this.tempUpperLimit = tempUpperLimit;
	}
	public int getTempLowerLimit() {
		return tempLowerLimit;
	}
	public void setTempLowerLimit(int tempLowerLimit) {
		this.tempLowerLimit = tempLowerLimit;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
