package com.piotrmajcher.piwind.mobileappserver.web.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.piotrmajcher.piwind.mobileappserver.enums.WindDirection;


public class MeteoDataTO {
	
private double temperature;
	
	private double windSpeed;
	
	private WindDirection windDirection;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime dateTime;
	
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
	
	public WindDirection getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(WindDirection windDirection) {
		this.windDirection = windDirection;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "MeteoDataTO [temperature=" + temperature + ", windSpeed=" + windSpeed + ", windDirection="
				+ windDirection + ", dateTime=" + dateTime + "]";
	}
}
