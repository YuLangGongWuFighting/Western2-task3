import java.sql.Date;

public class weatherInformation {

	Date fxDate;
	double tempMax;
	double tempMin;
	String textDay;

	public weatherInformation(Date fxDate, double tempMax, double tempMin, String textDay) {

		this.fxDate = fxDate;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
		this.textDay = textDay;
	}

	public Date getFxDate() {
		return fxDate;
	}

	public void setFxDate(Date fxDate) {
		this.fxDate = fxDate;
	}

	public double getTempMax() {
		return tempMax;
	}

	public void setTempMax(double tempMax) {
		this.tempMax = tempMax;
	}

	public double getTempMin() {
		return tempMin;
	}

	public void setTempMin(double tempMin) {
		this.tempMin = tempMin;
	}

	public String getTextDay() {
		return textDay;
	}

	public void setTextDay(String textDay) {
		this.textDay = textDay;
	}

}
