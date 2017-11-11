package com.piotrmajcher.piwind.mobileappserver.enums;

public enum TemperatureCategories {
	FREEZING(0.0, "Better stay at home!"),
	VERY_COLD(5.0, "Drysuit, hood, gloves and boots indispensable!"),
	COLD(10.0, "Drysuit or 5 mm winter wetsuit, hood, gloves and boots necessary!"),
	FRESH(15.0, "Long, 5 mm wetsuit weather. Hood and boots recommended"),
	WARM(20.0, "3 mm wetsuit with short arms should be great. No need for more!"),
	VERY_WARM(25.0, "Short wetsuit should be great!"),
	HOT(30.0, "Short wetsuit or boardshorts or bikini with neoprene shirt would be awesome!"),
	VERY_HOT(35.0, "Boardshorts or bikini weather! Watch out for the sun!"),
	EXTREMELY_HOT(Double.MAX_VALUE, "It will be hot, whatever you take...");
	
	private double topLimit;
	private String advice;
	
	private TemperatureCategories(double topLimit, String advice) {
		this.topLimit = topLimit;
		this.advice = advice;
	}

	public double getTopLimit() {
		return topLimit;
	}

	public String getAdvice() {
		return advice;
	}
}
