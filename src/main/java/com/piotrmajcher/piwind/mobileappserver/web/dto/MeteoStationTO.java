package com.piotrmajcher.piwind.mobileappserver.web.dto;

import java.util.Arrays;
import java.util.UUID;

import com.piotrmajcher.piwind.mobileappserver.enums.WindDirection;

public class MeteoStationTO {
	
	private UUID id;
	
	private String name;
	
	private String stationBaseURL;
	
	private String description;
	
	private WindDirection beachFacingDirection;
	
	private String[] bestWindDirections;
	
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStationBaseURL() {
		return stationBaseURL;
	}

	public void setStationBaseURL(String stationBaseURL) {
		this.stationBaseURL = stationBaseURL;
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

	@Override
	public String toString() {
		return "MeteoStationTO [id=" + id + ", name=" + name + ", stationBaseURL=" + stationBaseURL + ", description="
				+ description + ", beachFacingDirection=" + beachFacingDirection + ", bestWindDirections="
				+ Arrays.toString(bestWindDirections) + "]";
	}
}
