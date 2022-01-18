
public class basicInformation {
	String name;
	String id;
	double lat;
	double lon;

	public basicInformation(String name, String id, double lat, double lon) {
		this.name = name;
		this.id = id;
		this.lat = lat;
		this.lon = lon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

}
