package humming.bird.sprinkler;

public class Sprinkler {

	private String sprinklerId;
	private String groupName;
	private SprinklerStatus sprinklerStatus;
	private double volumeOfWater;

	public String getSprinklerId() {
		return sprinklerId;
	}

	public void setSprinklerId(String sprinklerId) {
		this.sprinklerId = sprinklerId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public SprinklerStatus getSprinklerStatus() {
		return sprinklerStatus;
	}

	public void setSprinklerStatus(SprinklerStatus sprinklerStatus) {
		this.sprinklerStatus = sprinklerStatus;
	}

	public double getVolumeOfWater() {
		return volumeOfWater;
	}

	public void setVolumeOfWater(double volumeOfWater) {
		this.volumeOfWater = volumeOfWater;
	}

}
