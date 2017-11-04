package com.piotrmajcher.piwind.mobileappserver.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WindSpeedTO {

    private Integer id;

    private Double windSpeedMPS;

    private Integer measurementTimeSeconds;

    private LocalDate date;

    private LocalDateTime dateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public LocalDate getDate() {
        return date;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
