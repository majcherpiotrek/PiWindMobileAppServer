package com.piotrmajcher.piwind.mobileappserver.web.dto;

import java.util.Date;

import com.piotrmajcher.piwind.mobileappserver.enums.WindDirection;

public class MeteoDataTOAndroid {
	
	private double temperature;
	
	private double windSpeed;
	
	private Date dateTime;
	
	private WindDirection windDirection;
	
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
	
	public WindDirection getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(WindDirection windDirection) {
		this.windDirection = windDirection;
	}

	@Override
	public String toString() {
		return "MeteoDataTOAndroid [temperature=" + temperature + ", windSpeed=" + windSpeed + ", dateTime=" + dateTime
				+ ", windDirection=" + windDirection + "]";
	}
}
