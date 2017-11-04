package com.piotrmajcher.piwind.mobileappserver.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

@Entity
public class MeteoStation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@URL(message = "Valid REST API base url of the station must be specified.")
	@NotEmpty(message = "Valid REST API base url of the station must be specified.")
	private String stationBaseURL;
	
	@NotEmpty(message = "A unique name for the meteo station must be specified.")
	@Column(unique = true)
	@Length(min = 3, max = 100, message = "The station's name must be between 3 and 100 characters.")
	private String name;

	public UUID getId() {
		return id;
	}

	public String getStationBaseURL() {
		return stationBaseURL;
	}

	public void setStationBaseURL(String stationBaseURL) {
		this.stationBaseURL = stationBaseURL;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
