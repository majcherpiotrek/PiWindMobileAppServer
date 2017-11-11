package com.piotrmajcher.piwind.mobileappserver.web.dto;

import java.util.UUID;

public class MeteoStationTO {
	
	private UUID id;
	
	private String name;
	
	private String stationBaseURL;
	
	
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
}
