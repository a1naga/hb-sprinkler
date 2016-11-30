package humming.bee.sprinkler.ui;

import java.sql.Time;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimerTask;

import humming.bee.sprinkler.db.SprinklerDb;
import humming.bee.sprinkler.service.OverrideTemperatureConfiguration;
import humming.bee.sprinkler.service.Sprinkler;
import humming.bee.sprinkler.service.SprinklerConfiguration;
import humming.bee.sprinkler.service.SprinklerGroup;
import humming.bee.sprinkler.service.SprinklerGroupConfiguration;
import humming.bee.sprinkler.service.TemperatureSensor;

public class SprinklerController extends TimerTask {

	public static String[] DAYS = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
			"Saturday" };

	private SprinklerDb dbService = new SprinklerDb();

	private long durationInSeconds;

	public SprinklerController(long duration) {
		this.durationInSeconds = duration / 1000;
	}

	@Override
	public void run() {
		double totalRunTime = 0;
		List<SprinklerGroup> groupList = dbService.getGroup();
		List<Sprinkler> sprinklerList = dbService.getSprinkler();

		Map<Integer, TemperatureUpperLimit> upperLimisprinklerGroupMap = new HashMap<Integer, TemperatureUpperLimit>();
		Set<OverrideTemperatureConfiguration> lowerLimisprinklerGroupSet = new HashSet<OverrideTemperatureConfiguration>();

		checkTemperatureOverride(upperLimisprinklerGroupMap, lowerLimisprinklerGroupSet);

		Calendar c1 = Calendar.getInstance();
		String currentDay = DAYS[c1.get(Calendar.DAY_OF_WEEK) - 1];

		Map<Integer, Long> groupWiseDuration = new HashMap<Integer, Long>();
		for (SprinklerGroup group : groupList) {
			groupWiseDuration.put(group.getGroupId(), 0L);
		}

		List<SprinklerGroupConfiguration> sprinklerGroupConfigList = dbService
				.getSprinklerGroupConfigurationByDay(currentDay);

		for (Sprinkler sprinkler : sprinklerList) {
			if (!upperLimisprinklerGroupMap.containsKey(sprinkler.getGroupId())
					&& !lowerLimisprinklerGroupSet.contains(sprinkler.getGroupId())) {

				if (sprinkler.isFunctional()) {
					boolean isStatusUpdated = false;
					// LocalTime localTime = LocalTime.now();
					// long currentTime = localTime.toSecondOfDay() * 1000l;
					Time current = new Time(c1.get(Calendar.HOUR_OF_DAY), c1.get(Calendar.MINUTE),
							c1.get(Calendar.SECOND));
					List<SprinklerConfiguration> sprinklerConfigList = dbService
							.getSprinklerConfigurationByDay(sprinkler.getSprinklerId(), currentDay);
					if (sprinklerConfigList != null && !sprinklerConfigList.isEmpty()) {
						for (SprinklerConfiguration sprinklerConfig : sprinklerConfigList) {
							if (current.after(sprinklerConfig.getStartTime())
									&& current.before(sprinklerConfig.getEndTime())) {
								// if (currentTime >=
								// sprinklerConfig.getStartTime().getTime() &&
								// currentTime <=
								// sprinklerConfig.getEndTime().getTime()) {
								System.out.println("updating sprinkler " + sprinkler.getSprinklerId()
										+ " to on based on sprinkler config " + sprinklerConfig.getId());
								dbService.updateSprinklerStatusById(sprinkler.getSprinklerId(), "ON");
								addGroupWiseDurationForSprinkler(groupWiseDuration, sprinkler);
								isStatusUpdated = true;
								break;
							}
							totalRunTime = totalRunTime + (sprinklerConfig.getEndTime().getTime()
									- sprinklerConfig.getStartTime().getTime()) / 1000;
						}
					} else {
						for (SprinklerGroupConfiguration groupConfig : sprinklerGroupConfigList) {
							if (sprinkler.getGroupId() == groupConfig.getGroupId()) {
								if (current.after(groupConfig.getStartTime())
										&& current.before(groupConfig.getEndTime())) {
									// if (currentTime >=
									// groupConfig.getStartTime().getTime() &&
									// currentTime <=
									// groupConfig.getEndTime().getTime()) {
									System.out.println("updating sprinkler " + sprinkler.getSprinklerId()
											+ " to on based on sprinkler config " + groupConfig.getId());
									System.out.println("start " + groupConfig.getStartTime().getTime());
									System.out.println("end " + groupConfig.getEndTime().getTime());
									dbService.updateSprinklerStatusById(sprinkler.getSprinklerId(), "ON");
									addGroupWiseDurationForSprinkler(groupWiseDuration, sprinkler);
									isStatusUpdated = true;
									break;
								}
							}
						}
					}
					if (!isStatusUpdated) {
						// check if the current status is ON. if so then change
						// it to OFF as it did not match
						// any sprinkler configuration
						if (sprinkler.getSprinklerStatus().equals("ON")) {
							System.out.println("updating sprinkler " + sprinkler.getSprinklerId()
									+ " to OFF as no matching config ");
							dbService.updateSprinklerStatusById(sprinkler.getSprinklerId(), "OFF");
						}
					}
				}
			} else {
				if (upperLimisprinklerGroupMap.containsKey(sprinkler.getGroupId())) {
					addGroupWiseDurationForSprinkler(groupWiseDuration, sprinkler);
				}
			}
		}
		updateGroupWiseDuration(groupWiseDuration, c1.get(Calendar.DAY_OF_MONTH));
	}

	private void updateGroupWiseDuration(Map<Integer, Long> groupWiseDuration, int dayOfMonth) {
		Set<Entry<Integer, Long>> set = groupWiseDuration.entrySet();

		for (Entry<Integer, Long> entry : set) {
			int groupId = entry.getKey();
			long duration = entry.getValue();
			if (duration > 0) {
				dbService.updateSprinklerGroupEvent(dayOfMonth, groupId, duration);
			}
		}
	}

	private void addGroupWiseDurationForSprinkler(Map<Integer, Long> groupWiseDuration, Sprinkler sprinkler) {
		Long currentValue = groupWiseDuration.get(sprinkler.getGroupId());
		groupWiseDuration.put(sprinkler.getGroupId(), currentValue + durationInSeconds);
	}

	private void checkTemperatureOverride(Map<Integer, TemperatureUpperLimit> upperLimisprinklerGroupMap,
			Set<OverrideTemperatureConfiguration> lowerLimisprinklerGroupSet) {
		List<OverrideTemperatureConfiguration> tempConfigurationList = dbService.getTemperatureConfiguration();
		for (int i = 0; i < tempConfigurationList.size(); i++) {
			OverrideTemperatureConfiguration tempConfig = tempConfigurationList.get(i);
			if (TemperatureSensor.getCurrentTemperature() > tempConfig.getTempUpperLimit()) {
				TemperatureUpperLimit freqDuration = new TemperatureUpperLimit();
				freqDuration.setDuration(tempConfig.getDuration());
				freqDuration.setFrequency(tempConfig.getFrequency());
				freqDuration.setId(tempConfig.getId());
				upperLimisprinklerGroupMap.put(tempConfig.getGroupId(), freqDuration);
				/**
				 * Here update sprinkler group status based on frequency and
				 * duration
				 **/
			} else if (TemperatureSensor.getCurrentTemperature() < tempConfig.getTempLowerLimit()) {
				lowerLimisprinklerGroupSet.add(tempConfig);
			}
		}

		for (Integer groupId : upperLimisprinklerGroupMap.keySet()) {
			dbService.updateSprinklerGroupStatusById(groupId, "ON");
		}

		for (OverrideTemperatureConfiguration tempConfig : lowerLimisprinklerGroupSet) {
			dbService.updateSprinklerGroupStatusById(tempConfig.getGroupId(), "OFF");
		}
	}
}
