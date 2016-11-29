package humming.bee.sprinkler.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import humming.bee.sprinkler.service.OverrideTemperatureConfiguration;
import humming.bee.sprinkler.service.Sprinkler;
import humming.bee.sprinkler.service.SprinklerConfiguration;
import humming.bee.sprinkler.service.SprinklerGroup;
import humming.bee.sprinkler.service.SprinklerGroupConfiguration;

public class SprinklerDb {

	private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/sprinklerdb?autoReconnect=true&useSSL=false";
	private static final String USERNAME = "hummingbee";
	private static final String PASSWORD = "humming123";
	
	private Connection con = null;

	private void getConnection() throws Exception {
		// here sprinklerdb is database name, hummingbee is username and
		// humming123 is the password
		if (con == null || con.isClosed()) {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
		}
	}

	/**
	 * Get List of sprinklers and its statuses from Database
	 * 
	 * @return sprinklerList
	 */
	public List<Sprinkler> getSprinkler() {
		List<Sprinkler> sprinklerList = new ArrayList<Sprinkler>();

		try {
			getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select id,name,group_id,status,volume,functional from sprinkler");
			while (rs.next()) {
				Sprinkler sprinklerObj = new Sprinkler();
				sprinklerObj.setSprinklerId(rs.getInt("id"));
				sprinklerObj.setSprinklerName(rs.getString("name"));
				sprinklerObj.setGroupId(rs.getInt("group_id"));
				sprinklerObj.setSprinklerStatus(rs.getString("status"));
				sprinklerObj.setVolumeOfWater(rs.getDouble("volume"));
				sprinklerObj.setFunctional(rs.getBoolean("functional"));

				sprinklerList.add(sprinklerObj);

			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return sprinklerList;
	}

	public List<SprinklerGroup> getGroup() {
		List<SprinklerGroup> groupList = new ArrayList<SprinklerGroup>();

		try {
			getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select id,name,status from sprinkler_group");

			while (rs.next()) {
				SprinklerGroup groupObj = new SprinklerGroup();
				groupObj.setGroupId(rs.getInt("id"));
				groupObj.setGroupName(rs.getString("name"));
				groupObj.setStatus(rs.getString("status"));
				groupList.add(groupObj);

			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return groupList;
	}

	
	public void setTemperatureConfiguration(OverrideTemperatureConfiguration tempConfiguration) {

		int upperLimit = tempConfiguration.getTempUpperLimit();
		int duration = tempConfiguration.getDuration();
		int frequency = tempConfiguration.getFrequency();
		int lowerLimit = tempConfiguration.getTempLowerLimit();
		int groupId = tempConfiguration.getGroupId();
		PreparedStatement ps = null;

		try {

			getConnection();
			
			String query = "UPDATE override_temp_config SET upper_limit = ?, lower_limit =?, frequency = ?, duration = ? WHERE group_id = ?; ";

			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setInt(1, upperLimit);// add parameter 
			ps.setInt(2, lowerLimit);
			ps.setInt(3, frequency);
			ps.setInt(4, duration);
			ps.setInt(5, groupId);
			ps.executeUpdate();
		
		} catch (Exception e) {
			System.out.println(e);
		}finally {
			try {
				// close statement and connection
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	}
	
	
	public List<OverrideTemperatureConfiguration> getTemperatureConfiguration() {

		List<OverrideTemperatureConfiguration> tempConfigurationList = new ArrayList<OverrideTemperatureConfiguration>();

		try {
			getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select id,upper_limit,lower_limit,frequency,duration,group_id from override_temp_config");

			while (rs.next()) {
				OverrideTemperatureConfiguration overrideTemperature = new OverrideTemperatureConfiguration();
				overrideTemperature.setId(rs.getInt("id"));
				overrideTemperature.setTempUpperLimit(rs.getInt("upper_limit"));
				overrideTemperature.setTempLowerLimit(rs.getInt("lower_limit"));
				overrideTemperature.setFrequency(rs.getInt("frequency"));
				overrideTemperature.setDuration(rs.getInt("duration"));
				overrideTemperature.setGroupId(rs.getInt("group_id"));
				tempConfigurationList.add(overrideTemperature);

			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return tempConfigurationList;
	}

	// ******************************sprinkler**************************************//

	/*
	 * public List<Sprinkler> getAllStatus() { List<Sprinkler> sprinklerList =
	 * new ArrayList<Sprinkler>();
	 * 
	 * try { Class.forName("com.mysql.jdbc.Driver"); Connection con =
	 * DriverManager.getConnection(
	 * "jdbc:mysql://localhost:3306/sprinklerdb?autoReconnect=true&useSSL=false",
	 * "hummingbee", "humming123"); // here sprinklerdb is database name,
	 * hummingbee is username and // humming123 is the password Statement stmt =
	 * con.createStatement(); ResultSet rs = stmt.executeQuery(
	 * "select id,name,group_id,status,volume from sprinkler"); while
	 * (rs.next()) { Sprinkler sprinklerObj = new Sprinkler();
	 * sprinklerObj.setSprinklerId(rs.getInt("id"));
	 * sprinklerObj.setSprinklerName(rs.getString("name"));
	 * sprinklerObj.setGroupId(rs.getInt("group_id"));
	 * sprinklerObj.setSprinklerStatus(rs.getString("status"));
	 * sprinklerObj.setVolumeOfWater(rs.getDouble("volume"));
	 * 
	 * sprinklerList.add(sprinklerObj);
	 * 
	 * }
	 * 
	 * con.close(); } catch (Exception e) { System.out.println(e); } return
	 * sprinklerList; }
	 */

	/**
	 * Get all sprinklers from database by group name
	 * 
	 * @param groupName
	 * @return List
	 */
	public List<Sprinkler> getSprinklersByGroup(String groupName) {
		List<Sprinkler> sprinklerList = new ArrayList<Sprinkler>();

		PreparedStatement ps = null;

		try {

			// query statement and get result
			String query = "SELECT `sprinkler`.`id`,`sprinkler`.`name`,`sprinkler`.`group_id`,`sprinkler`.`status`,`sprinkler`.`functional` "
					+ "FROM `sprinkler` INNER JOIN sprinkler_group ON sprinkler.group_id = sprinkler_group.id "
					+ "where sprinkler_group.name=?;";

			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, groupName);// add parameter

			// get result from db
			ResultSet rs = ps.executeQuery();

			// loop through the result set and add to list
			while (rs.next()) {
				Sprinkler newSprinkler = new Sprinkler();
				newSprinkler.setSprinklerId(rs.getInt("id"));
				newSprinkler.setSprinklerName(rs.getString("name"));
				newSprinkler.setGroupId(rs.getInt("group_id"));
				newSprinkler.setSprinklerStatus(rs.getString("status"));
				newSprinkler.setFunctional((rs.getInt("functional")) == 1 ? true : false);

				sprinklerList.add(newSprinkler);

			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// close statement and connection
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return sprinklerList;
	}

	/**
	 * Get sprinkler details by name from database
	 * 
	 * @param sprinklerName
	 * @return Sprinkler
	 */
	public Sprinkler getSprinklerByName(String sprinklerName) {
		Sprinkler newSprinkler = new Sprinkler();

		PreparedStatement ps = null;

		try {
			getConnection();
			// query statement and get result
			String query = "SELECT `sprinkler`.`id`,`sprinkler`.`name`,`sprinkler`.`group_id`,`sprinkler`.`status`,`sprinkler`.`functional` "
					+ "FROM `sprinkler` where `sprinkler`.`name`=?;";

			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, sprinklerName);// add parameter

			// get result from db
			ResultSet rs = ps.executeQuery();

			// retrieve data to sprinkler object
			if (rs.next()) {
				newSprinkler.setSprinklerId(rs.getInt("id"));
				newSprinkler.setSprinklerName(rs.getString("name"));
				newSprinkler.setGroupId(rs.getInt("group_id"));
				newSprinkler.setSprinklerStatus(rs.getString("status"));
				newSprinkler.setFunctional((rs.getInt("functional")) == 1 ? true : false);
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// close statement and connection
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return newSprinkler;
	}

	/**
	 * Update Sprinkler status(ON/OFF) to database
	 * 
	 * @param sprinklerName
	 * @param status
	 */
	public void updateSprinklerStatus(String sprinklerName, String status) {
		PreparedStatement ps = null;

		try {
			getConnection();

			// query statement and get result
			String query = "UPDATE `sprinkler` SET `status` = ? WHERE `name` = ? ;";

			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, status);// add parameter
			ps.setString(2, sprinklerName);// add parameter

			// update db
			ps.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// close statement and connection
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateSprinklerStatusById(int id, String status) {
		PreparedStatement ps = null;

		try {
			getConnection();

			// query statement and get result
			String query = "UPDATE sprinkler SET status = ? WHERE id = ? ;";

			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, status);// add parameter
			ps.setInt(2, id);// add parameter

			// update db
			ps.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// close statement and connection
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Update sprinkler to functional/not-functional If changing to
	 * not-functional, also update status=off
	 * 
	 * @param sprinklerName
	 * @param functional
	 */
	public void updateSprinklerFunctional(String sprinklerName, int functional) {
		PreparedStatement ps = null;

		try {
			getConnection();

			// query statement and get result
			String query;
			if (functional == 0)// not-functional
				query = "UPDATE `sprinkler` SET `functional` = ?, status='OFF' WHERE `name` = ? ;";
			else
				query = "UPDATE `sprinkler` SET `functional` = ? WHERE `name` = ? ;";

			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, String.valueOf(functional));// add parameter
			ps.setString(2, sprinklerName);// add parameter

			// update db
			ps.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// close statement and connection
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// *******************************************************************************//

	// ******************************sprinkler
	// group**********************************//

	/**
	 * Gets sprinkler-group by group name from database
	 * 
	 * @param groupName
	 * @return
	 */
	public SprinklerGroup getSprinklerGroupByName(String groupName) {
		SprinklerGroup newGroup = new SprinklerGroup();

		PreparedStatement ps = null;

		try {
			getConnection();

			// query statement and get result
			String query = "SELECT `id`,`name`,`status` FROM `sprinkler_group` WHERE name=?;";

			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, groupName);// add parameter

			// get result from db
			ResultSet rs = ps.executeQuery();

			// retrieve data to sprinkler group object
			if (rs.next()) {
				newGroup.setGroupId(rs.getInt("id"));
				newGroup.setGroupName(rs.getString("name"));
				newGroup.setStatus(rs.getString("status"));

			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// close statement and connection
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return newGroup;
	}

	/**
	 * Update status of a sprinkler-group (on/off) to database
	 * 
	 * @param groupName
	 * @param status
	 */
	public void updateSprinklerGroupStatusById(int groupId, String status) {
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;

		try {

			getConnection();
			
			String query1 = "UPDATE sprinkler_group SET status = ? WHERE id = ? ; ";

			ps1 = (PreparedStatement) con.prepareStatement(query1);
			ps1.setString(1, status);// add parameter
			ps1.setInt(2, groupId);
			ps1.executeUpdate();

			String query2 = "UPDATE sprinkler SET status = ? WHERE group_id = ? and functional!=0 ";

			ps2 = (PreparedStatement) con.prepareStatement(query2);
			ps2.setString(1, status);// add parameter
			ps2.setInt(2, groupId);
			ps2.executeUpdate();
			//con.commit();

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// close statement and connection
				ps1.close();
				ps2.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// *******************************************************************************//

	// ******************************configuration************************************//

	/**
	 * Get Sprinkler configuration from database by sprinkler name
	 * 
	 * @param sprinklerName
	 * @return
	 */
	public List<SprinklerConfiguration> getConfiguration(String sprinklerName) {
		sprinklerName = "1N";// for testing

		List<SprinklerConfiguration> sprinklerConfigList = new ArrayList<SprinklerConfiguration>();
		PreparedStatement ps = null;

		try {
			getConnection();

			// query statement and get result
			String query = "SELECT `sprinkler_config`.`id`,`sprinkler_config`.`sprinkler_id`,`sprinkler_config`.`day`,"
					+ "`sprinkler_config`.`start_time`,`sprinkler_config`.`end_time` "
					+ "FROM sprinkler_config inner join sprinkler " + "on sprinkler.id=sprinkler_config.sprinkler_id "
					+ "where sprinkler.name=?;";

			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, sprinklerName);// add parameter

			// get result from db
			ResultSet rs = ps.executeQuery();

			// loop through the result set and add to list
			while (rs.next()) {
				SprinklerConfiguration newSprinklerConfig = new SprinklerConfiguration();
				newSprinklerConfig.setId(rs.getInt("id"));
				newSprinklerConfig.setSprinklerId(rs.getInt("sprinkler_id"));
				newSprinklerConfig.setDayOfWeek(rs.getString("day"));
				newSprinklerConfig.setStartTime(rs.getTime("start_time"));
				newSprinklerConfig.setEndTime(rs.getTime("end_time"));

				sprinklerConfigList.add(newSprinklerConfig);

				System.out.print(sprinklerName);
				System.out.println("config id=" + newSprinklerConfig.getId());

			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// close statement and connection
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return sprinklerConfigList;
	}

	// INSERT INTO
	// `sprinkler_config`(`sprinkler_id`,`day`,`start_time`,`end_time`) VALUES (
	// ?,?,?,?) ;

	public int addSprinklerConfiguration(SprinklerConfiguration newSprinklerConfig) {
		PreparedStatement ps = null;
		int i = 0;

		try {
			getConnection();

			// query statement and get result
			String query = "INSERT INTO `sprinkler_config`(`sprinkler_id`,`day`,`start_time`,`end_time`) "
					+ "VALUES ( ?,?,?,?) ;";

			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, String.valueOf(newSprinklerConfig.getSprinklerId()));// add
																					// parameters
			ps.setString(2, newSprinklerConfig.getDayOfWeek());
			ps.setString(3, formatter.format(newSprinklerConfig.getStartTime()));
			ps.setString(4, formatter.format(newSprinklerConfig.getEndTime()));

			// update to db
			i = ps.executeUpdate();

			System.out.println("db returned:" + i);

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// close statement and connection
				ps.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return i;

	}

	/**
	 * Update status of a sprinkler-group (on/off) to database
	 * 
	 * @param groupName
	 * @param status
	 */
	public void updateSprinklerGroupStatus(String groupName, String status) {
		PreparedStatement ps = null;

		try {
			getConnection();

			// query statement and get result
			String query = "UPDATE `sprinkler_group` SET `status` = ? WHERE `name` = ? ;";

			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, status);// add parameter
			ps.setString(2, groupName);// add parameter

			// update db
			ps.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// close statement and connection
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// *******************************************************************************//

	// SELECT `id`,`sprinkler_id`,`day`,`start_time`,`end_time` FROM
	// `sprinkler_config` WHERE sprinkler_id=1 AND day='Monday'

	public List<SprinklerConfiguration> getSprinklerConfigurationByDay(int sprinklerId, String day) {
		List<SprinklerConfiguration> sConfigList = new ArrayList<SprinklerConfiguration>();

		PreparedStatement ps = null;

		try {
			getConnection();

			// query statement and get result
			String query = "SELECT `id`,`sprinkler_id`,`day`,`start_time`,`end_time` "
					+ "FROM `sprinkler_config` WHERE sprinkler_id=? AND day=?";

			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, String.valueOf(sprinklerId));// add parameters
			ps.setString(2, day);

			// get result from db
			ResultSet rs = ps.executeQuery();
			SprinklerConfiguration newSprinklerConfig = null;
			// loop through the result set and add to list
			while (rs.next()) {
				newSprinklerConfig = new SprinklerConfiguration();
				newSprinklerConfig.setId(rs.getInt("id"));
				newSprinklerConfig.setSprinklerId(rs.getInt("sprinkler_id"));
				newSprinklerConfig.setDayOfWeek(rs.getString("day"));
				newSprinklerConfig.setStartTime(rs.getTime("start_time"));
				newSprinklerConfig.setEndTime(rs.getTime("end_time"));

				sConfigList.add(newSprinklerConfig);
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// close statement and connection
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return sConfigList;

	}

	/***********
	 * 
	 * */
	public List<SprinklerGroupConfiguration> getSprinklerGroupConfigurationByDay(String day) {
		List<SprinklerGroupConfiguration> sGroupConfigList = new ArrayList<SprinklerGroupConfiguration>();

		PreparedStatement ps = null;

		try {
			getConnection();
			// query statement and get result
			String query = "select id, group_id, day, start_time, end_time from sprinkler_group_config where day = ?";

			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, day);

			// get result from db
			ResultSet rs = ps.executeQuery();
			// loop through the result set and add to list
			while (rs.next()) {
				SprinklerGroupConfiguration sprinklerGrpConfig = new SprinklerGroupConfiguration();
				sprinklerGrpConfig.setId(rs.getInt("id"));
				sprinklerGrpConfig.setGroupId(rs.getInt("group_id"));
				sprinklerGrpConfig.setDayOfWeek(rs.getString("day"));
				sprinklerGrpConfig.setStartTime(rs.getTime("start_time"));
				sprinklerGrpConfig.setEndTime(rs.getTime("end_time"));

				sGroupConfigList.add(sprinklerGrpConfig);
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// close statement and connection
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return sGroupConfigList;

	}

	/**
	 * Delete selected sprinkler config from database
	 * 
	 * @param sConfigId
	 * @return
	 */
	public int deleteSprinklerConfigById(int sConfigId) {
		// DELETE FROM `sprinklerdb`.`sprinkler_config` WHERE `id`=?;

		PreparedStatement ps = null;
		int i = 0;

		try {
			getConnection();

			// query statement and get result
			String query = "DELETE FROM `sprinklerdb`.`sprinkler_config` WHERE `id`=?;";

			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, String.valueOf(sConfigId));// add parameters

			// delete from db
			i = ps.executeUpdate();

			System.out.println("db returned:" + i);

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// close statement and connection
				ps.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return i;
	}

	// *******************************************************************************//

	// ******************************<next
	// section>************************************//

	// *******************************************************************************//

}
