package com.piotrmajcher.piwind.mobileappserver.web.dto;

public class MeteoDataTO {
	
	private double temperature;
	
	private double windSpeed;
	
	public MeteoDataTO(double temperature, double windSpeed) {
		super();
		this.temperature = temperature;
		this.windSpeed = windSpeed;
	}

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

	@Override
	public String toString() {
		return "MeteoDataTO [temperature=" + temperature + ", windSpeed=" + windSpeed + "]";
	}
}
