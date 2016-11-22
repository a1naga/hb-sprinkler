package humming.bee.sprinkler.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import humming.bee.sprinkler.service.*;

public class SprinklerDb {

	public List<Sprinkler> getAllStatus() {
		List<Sprinkler> sprinklerList = new ArrayList<Sprinkler>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sprinklerdb", "hummingbee",
					"humming123");
			// here sprinklerdb is database name, hummingbee is username and
			// humming123 is the password
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select id,name,group_id,status,volume from sprinkler");
			while (rs.next()) {
				Sprinkler sprinklerObj = new Sprinkler();
				sprinklerObj.setSprinklerId(rs.getInt("id"));
				sprinklerObj.setSprinklerName(rs.getString("name"));
				sprinklerObj.setGroupId(rs.getInt("group_id"));
				sprinklerObj.setSprinklerStatus(rs.getString("status"));
				sprinklerObj.setVolumeOfWater(rs.getDouble("volume"));

				sprinklerList.add(sprinklerObj);

			}

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return sprinklerList;
	}

}
