package com.piotrmajcher.piwind.mobileappserver.web.dto;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WindStatisticsDataTO {
	
	private float avgWind;
    
	private float maxGust;
    
	private float minGust;
    
	private long date;

	public float getAvgWind() {
		return avgWind;
	}

	public void setAvgWind(float avgWind) {
		this.avgWind = avgWind;
	}

	public float getMaxGust() {
		return maxGust;
	}

	public void setMaxGust(float maxGust) {
		this.maxGust = maxGust;
	}

	public float getMinGust() {
		return minGust;
	}

	public void setMinGust(float minGust) {
		this.minGust = minGust;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
}
