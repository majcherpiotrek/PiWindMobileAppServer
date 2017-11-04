package com.piotrmajcher.piwind.mobileappserver.web.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemperatureTO {
	
	private Double temperatureCelsius;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime dateTime;

	public Double getTemperatureCelsius() {
		return temperatureCelsius;
	}

	public void setTemperatureCelsius(Double temperatureCelsius) {
		this.temperatureCelsius = temperatureCelsius;
	}
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
}
