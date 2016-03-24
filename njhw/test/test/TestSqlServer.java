package test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cosmosource.app.utils.SqlserverJdbcUtils;

public class TestSqlServer {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test1() throws Exception {
		Connection conn = SqlserverJdbcUtils.getConnectionWireless();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		String tagMac = "B0:8E:1A:01:00:1C";

		sb.append("select c.coordinatesName, CONVERT(varchar, b.WriteTime, 120 ) as WriteTime from device_Tag a, history_TagPositionLog b, map_coordinates c ");
		sb.append("where a.Id = b.TagId and b.coordinatesid = c.id ");
		sb.append("and a.TagMac = ? ");
		sb.append("and CONVERT(varchar, b.WriteTime, 120 ) >= ? ");
		sb.append("and CONVERT(varchar, b.WriteTime, 120 ) <= ? ");
		sb.append("order by c.coordinatesName desc");
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		List<Map> list = null;
		Map m = null;
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, tagMac);
			ps.setString(2, "2014-01-27 10:01:01");
			ps.setString(3, "2014-01-27 18:00:00");

			rs = ps.executeQuery();
			list = new ArrayList<Map>();
			while (rs.next()) {
				m = new HashMap();
				m.put("WriteTime", rs.getString("WRITETIME"));
				m.put("CoordinatesId", rs.getString("coordinatesName"));
				System.out.println(m);
				list.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			SqlserverJdbcUtils.close(conn, ps, rs);
		}
	}
}
