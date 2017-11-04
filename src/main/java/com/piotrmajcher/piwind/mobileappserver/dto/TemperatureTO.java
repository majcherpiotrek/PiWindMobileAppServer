package com.piotrmajcher.piwind.mobileappserver.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TemperatureTO {
	
	private Integer id;
	
	private Double temperatureCelsius;
	
	private LocalDate date;
	
	private LocalDateTime dateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getTemperatureCelsius() {
		return temperatureCelsius;
	}

	public void setTemperatureCelsius(Double temperatureCelsius) {
		this.temperatureCelsius = temperatureCelsius;
	}

	public LocalDate getDate() {
		return date;
	}
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}
}
