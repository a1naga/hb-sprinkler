package humming.bee.sprinkler.service;

public class Sprinkler {

	private int sprinklerId;
	private String sprinklerName;
	private int groupId;
	private String sprinklerStatus;
	private double volumeOfWater;

	public int getSprinklerId() {
		return sprinklerId;
	}

	public void setSprinklerId(int sprinklerId) {
		this.sprinklerId = sprinklerId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getSprinklerStatus() {
		return sprinklerStatus;
	}

	public void setSprinklerStatus(String sprinklerStatus) {
		this.sprinklerStatus = sprinklerStatus;
	}

	public double getVolumeOfWater() {
		return volumeOfWater;
	}

	public void setVolumeOfWater(double volumeOfWater) {
		this.volumeOfWater = volumeOfWater;
	}

	public String getSprinklerName() {
		return sprinklerName;
	}

	public void setSprinklerName(String sprinklerName) {
		this.sprinklerName = sprinklerName;
	}

	@Override
	public String toString() {
		return "Sprinkler [sprinklerId=" + sprinklerId + ", sprinklerName=" + sprinklerName + ", groupId=" + groupId
				+ ", sprinklerStatus=" + sprinklerStatus + ", volumeOfWater=" + volumeOfWater + "\n"+ "]";
	}

}
