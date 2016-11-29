package humming.bee.sprinkler.ui;

import java.sql.Time;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import humming.bee.sprinkler.db.SprinklerDb;
import humming.bee.sprinkler.service.OverrideTemperatureConfiguration;
import humming.bee.sprinkler.service.Sprinkler;
import humming.bee.sprinkler.service.SprinklerConfiguration;
import humming.bee.sprinkler.service.SprinklerGroupConfiguration;
import humming.bee.sprinkler.service.TemperatureSensor;

public class SprinklerController extends TimerTask {

	public static String[] DAYS = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
			"Saturday" };

	@Override
	public void run() {

		SprinklerDb dbService = new SprinklerDb();
		TemperatureSensor tempSensor = new TemperatureSensor();
		List<Sprinkler> sprinklerList = dbService.getSprinkler();
		List<OverrideTemperatureConfiguration> tempConfigurationList = dbService.getTemperatureConfiguration();
		List<SprinklerConfiguration> sprinklerConfigList;
		List<SprinklerGroupConfiguration> sprinklerGroupConfigList;
		
		TemperatureUpperLimit freqDuration = new TemperatureUpperLimit();
		Map<Integer, TemperatureUpperLimit> upperLimisprinklerGroupMap = new HashMap<Integer, TemperatureUpperLimit>();
		Set<OverrideTemperatureConfiguration> lowerLimisprinklerGroupSet = new HashSet<OverrideTemperatureConfiguration>();

		for (int i = 0; i < tempConfigurationList.size(); i++) {
			OverrideTemperatureConfiguration tempConfig = tempConfigurationList.get(i);
			if (tempSensor.getCurrentTempearture() > tempConfig.getTempUpperLimit()) {
				freqDuration.setDuration(tempConfig.getDuration());
				freqDuration.setFrequency(tempConfig.getFrequency());
				freqDuration.setId(tempConfig.getId());
				upperLimisprinklerGroupMap.put(tempConfig.getGroupId(), freqDuration);
				/**
				 * Here update sprinkler group status based on frequency and
				 * duration
				 **/
			} else if (tempSensor.getCurrentTempearture() < tempConfig.getTempLowerLimit()) {
				lowerLimisprinklerGroupSet.add(tempConfig);
			}
		}

		for (Integer groupId : upperLimisprinklerGroupMap.keySet()) {
			dbService.updateSprinklerGroupStatusById(groupId, "ON");
		}

		for (OverrideTemperatureConfiguration tempConfig : lowerLimisprinklerGroupSet) {
			dbService.updateSprinklerGroupStatusById(tempConfig.getGroupId(), "OFF");
		}

		Calendar c1 = Calendar.getInstance();
		String currentDay = DAYS[c1.get(Calendar.DAY_OF_WEEK) - 1];

		sprinklerGroupConfigList = dbService.getSprinklerGroupConfigurationByDay(currentDay);

		for (Sprinkler sprinkler : sprinklerList) {
			if (!upperLimisprinklerGroupMap.containsKey(sprinkler.getGroupId())
					&& !lowerLimisprinklerGroupSet.contains(sprinkler.getGroupId())) {

				if (sprinkler.isFunctional()) {
					boolean isStatusUpdated = false;
					sprinklerConfigList = dbService.getSprinklerConfigurationByDay(sprinkler.getSprinklerId(),
							currentDay);
					if (sprinklerConfigList != null && !sprinklerConfigList.isEmpty()) {
						for (SprinklerConfiguration sprinklerConfig : sprinklerConfigList) {
							Time current = new Time(c1.get(Calendar.HOUR), c1.get(Calendar.MINUTE), c1.get(Calendar.SECOND));
							if (current.after(sprinklerConfig.getStartTime()) && current.before(sprinklerConfig.getEndTime())) {
								System.out.println("updating sprinkler " + sprinkler.getSprinklerId() + " to on based on sprinkler config " + sprinklerConfig.getId());
								dbService.updateSprinklerStatusById(sprinkler.getSprinklerId(), "ON");
								isStatusUpdated = true;
								break;
							}
						}
					} else {
						for (SprinklerGroupConfiguration groupConfig : sprinklerGroupConfigList) {
							if (sprinkler.getGroupId() == groupConfig.getGroupId()) {
								Time current = new Time(c1.get(Calendar.HOUR), c1.get(Calendar.MINUTE), c1.get(Calendar.SECOND));
								if (current.after(groupConfig.getStartTime()) && current.before(groupConfig.getEndTime())) {
									System.out.println("updating sprinkler " + sprinkler.getSprinklerId() + " to on based on sprinkler config " + groupConfig.getId());
									dbService.updateSprinklerStatusById(sprinkler.getSprinklerId(), "ON");
									isStatusUpdated = true;
									break;
								}
							}
						}
					}
					if (!isStatusUpdated) {
						//check if the current status is ON. if so then change it to OFF as it did not match
						//any sprinkler configuration
						if (sprinkler.getSprinklerStatus().equals("ON")) {
							System.out.println("updating sprinkler " + sprinkler.getSprinklerId() + " to OFF as no matching config ");
							dbService.updateSprinklerStatusById(sprinkler.getSprinklerId(), "OFF");
						}
					}

				}
			}

		}
	}
}
