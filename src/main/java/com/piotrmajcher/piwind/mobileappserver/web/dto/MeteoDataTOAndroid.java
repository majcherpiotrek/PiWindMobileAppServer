package com.piotrmajcher.piwind.mobileappserver.web.dto;

import java.util.Date;

public class MeteoDataTOAndroid {
	
private double temperature;
	
	private double windSpeed;
	
	private Date dateTime;
	
	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}
	
	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "MeteoDataTO [temperature=" + temperature + ", windSpeed=" + windSpeed + ", dateTime=" + dateTime + "]";
	}
}
