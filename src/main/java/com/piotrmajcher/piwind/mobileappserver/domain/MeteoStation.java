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

import com.piotrmajcher.piwind.mobileappserver.enums.WindDirection;

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
	
	@Column
	private String description;
	
	@Column
	private WindDirection beachFacingDirection;
	
	@Column
	private String[] bestWindDirections;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public WindDirection getBeachFacingDirection() {
		return beachFacingDirection;
	}

	public void setBeachFacingDirection(WindDirection beachFacingDirection) {
		this.beachFacingDirection = beachFacingDirection;
	}

	public String[] getBestWindDirections() {
		return bestWindDirections;
	}

	public void setBestWindDirections(String[] bestWindDirections) {
		this.bestWindDirections = bestWindDirections;
	}
}
