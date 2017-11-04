package com.piotrmajcher.piwind.mobileappserver.web.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WindSpeedTO {

    private Double windSpeedMPS;

    private Integer measurementTimeSeconds;
    
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;

    public Double getWindSpeedMPS() {
        return windSpeedMPS;
    }

    public void setWindSpeedMPS(Double windSpeedMPS) {
        this.windSpeedMPS = windSpeedMPS;
    }

    public Integer getMeasurementTimeSeconds() {
        return measurementTimeSeconds;
    }

    public void setMeasurementTimeSeconds(Integer measurementTimeSeconds) {
        this.measurementTimeSeconds = measurementTimeSeconds;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
}
