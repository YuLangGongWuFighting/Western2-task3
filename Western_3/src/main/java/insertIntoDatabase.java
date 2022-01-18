
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class insertIntoDatabase {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		insertIntoDatabase instance = new insertIntoDatabase();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

		String s1 = instance.doGet(cm, "北京", "base");
		String s2 = instance.doGet(cm, "上海", "base");
		String s3 = instance.doGet(cm, "福州", "base");
		String s4 = instance.doGet(cm, "101010100", "weather");
		String s5 = instance.doGet(cm, "101020100", "weather");
		String s6 = instance.doGet(cm, "101230101", "weather");

		instance.dealWith(s1, "base", null);
		instance.dealWith(s2, "base", null);
		instance.dealWith(s3, "base", null);
		instance.dealWith(s4, "weather", "101010100");
		instance.dealWith(s5, "weather", "101020100");
		instance.dealWith(s6, "weather", "101230101");
		System.out.println("insert successfully");

	}

	public String doGet(PoolingHttpClientConnectionManager cm, String s, String judge) throws Exception {

		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
		URIBuilder uriBuilder;
		if (judge == "base") {
			uriBuilder = new URIBuilder(
					"https://geoapi.qweather.com/v2/city/lookup?key=906886e61b6041789e2f4c090b5a75fb&");

		} else {
			uriBuilder = new URIBuilder(
					"https://devapi.qweather.com/v7/weather/3d?key=906886e61b6041789e2f4c090b5a75fb&");

		}
		uriBuilder.setParameter("location", s);
		HttpGet httpGet = new HttpGet(uriBuilder.build());
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpGet);
			String content = EntityUtils.toString(response.getEntity());
			return content;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void dealWith(String s, String judge, String id) {
		JSONObject obj = JSONObject.parseObject(s);
		JSONArray arr = null;
		if (judge == "base") {
			arr = obj.getJSONArray("location");
			String js = JSON.toJSONString(arr);
			ArrayList<basicInformation> listBase = (ArrayList<basicInformation>) JSON.parseArray(js,
					basicInformation.class);

			insertBasic(listBase.get(0).getName(), listBase.get(0).getId(), listBase.get(0).getLat(),
					listBase.get(0).getLon());

		} else {
			arr = obj.getJSONArray("daily");
			String js = JSON.toJSONString(arr);
			ArrayList<weatherInformation> listWeather = (ArrayList<weatherInformation>) JSON.parseArray(js,
					weatherInformation.class);
			for (weatherInformation item : listWeather) {
				insertWeather(id, item.getFxDate(), item.getTempMax(), item.getTempMin(), item.getTextDay());

			}
		}

	}

	public static void insertBasic(String name, String id, double lat, double lon) {
		Connection con = null;
		PreparedStatement sta = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql:///western2_3", "root", "123456789");
			con.setAutoCommit(false);
			String sql = "Insert into basicInformation(name,id,lat,lon)values(?,?,?,?)";
			sta = con.prepareStatement(sql);
			sta.setString(1, name);
			sta.setString(2, id);
			sta.setDouble(3, lat);
			sta.setDouble(4, lon);
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
		} finally {

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

	public static void insertWeather(String id, Date fxDate, double tempMax, double tempMin, String textDay) {
		Connection con = null;
		PreparedStatement sta = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql:///western2_3", "root", "123456789");
			con.setAutoCommit(false);
			String sql = "insert into weatherInformation(id,fxDate,tempMax,tempMin,textDay)values(?,?,?,?,?)";
			sta = con.prepareStatement(sql);
			sta.setString(1, id);
			sta.setDate(2, fxDate);
			sta.setDouble(3, tempMax);
			sta.setDouble(4, tempMin);
			sta.setString(5, textDay);
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
		} finally {

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
