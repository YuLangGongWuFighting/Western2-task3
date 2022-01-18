import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("The weather in Fuzhou");
		select("福州");
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		insertIntoDatabase instance2 = new insertIntoDatabase();
		String s = instance2.doGet(cm, "101010100", "weather");
		JSONObject obj = JSONObject.parseObject(s);
		JSONArray arr = obj.getJSONArray("daily");
		String js = JSON.toJSONString(arr);
		ArrayList<weatherInformation> listWeather = (ArrayList<weatherInformation>) JSON.parseArray(js,
				weatherInformation.class);
		for (weatherInformation item : listWeather) {
			update(item.getTempMax(), item.getTempMin(), item.getTextDay(), "101010100", item.getFxDate());
		}
		System.out.println("update successfully");
		System.out.println("This is the updated data");
		select("北京");

	}

	public static void select(String name) {
		Connection con = null;
		PreparedStatement sta = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql:///western2_3", "root", "123456789");
			con.setAutoCommit(false);
			String sql = "select*from weatherInformation where iD =(select iD from basicInformation where name=?)";
			sta = con.prepareStatement(sql);
			sta.setString(1, name);
			rs = sta.executeQuery();
			while (rs.next()) {
				System.out.print("iD " + rs.getString("iD"));
				System.out.print(" fxDate " + rs.getDate("fxDate"));
				System.out.print(" tempMax " + rs.getDouble("tempMax"));
				System.out.print(" tempMin " + rs.getDouble("tempMin"));
				System.out.print(" textDay " + rs.getString("textDay"));
				System.out.println();

			}
			con.commit();
		} catch (Exception e) {

			try {
				if (con != null) {
					con.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (sta != null) {
				try {
					sta.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public static void update(double tempMax, double tempMin, String textDay, String iD, Date fxDate) {
		Connection con = null;
		PreparedStatement sta = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql:///western2_3", "root", "123456789");
			con.setAutoCommit(false);
			String sql = "update weatherInformation set tempMax=?,tempMin=?,textDay=?where iD=? and fxDate=?";
			sta = con.prepareStatement(sql);
			sta.setDouble(1, tempMax);
			sta.setDouble(2, tempMin);
			sta.setString(3, textDay);
			sta.setString(4, iD);
			sta.setDate(5, fxDate);
			sta.executeUpdate();
			con.commit();

		} catch (Exception e) {

			try {
				if (con != null) {
					con.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			e.printStackTrace();
		}

		finally {

			if (sta != null) {
				try {
					sta.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

}
