package com.piotrmajcher.piwind.mobileappserver.web.dto;

public class MeteoStationTO {
	
	private String id;
	
	private String name;
	
	private String stationBaseURL;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
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
