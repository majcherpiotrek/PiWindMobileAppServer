package com.piotrmajcher.piwind.mobileappserver.dto;

public class MeteoStationTO {
	
	private String name;
	
	private String stationBaseURL;

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
