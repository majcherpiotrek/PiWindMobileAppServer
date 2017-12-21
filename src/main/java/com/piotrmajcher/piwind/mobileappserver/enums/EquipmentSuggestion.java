package com.piotrmajcher.piwind.mobileappserver.enums;

public enum EquipmentSuggestion {

	VERY_BIG_SETUP(5.0, "Board: > 130l, sail: > 7.5m\u00B2"),
	BIG_SETUP(8.0, "Board: > 100l, sail: 6.0m\u00B2 - 7.5m\u00B2"),
	MEDIUM_SETUP(10.0, "Board: > 90l, sail: 4.8m\u00B2 - 6.2m\u00B2"),
	SMALL_SETUP(13.0, "Board: > 85l, sail: 4.4m\u00B2 - 5.8m\u00B2"),
	VERY_SMALL_SETUP(18.0, "Board: > 70l, sail: 4.0m\u00B2 - 4.8m\u00B2"),
	LIGHT_STORM_SETUP(24.0, "Board: > 60l, sail: 3.3m\u00B2 - 4.4m\u00B2"),
	STORM_SETUP(30.0, "Board: > 60l, sail: 3.0m\u00B2 - 3.7m\u00B2"),
	TOO_STRONG_WIND(Double.MAX_VALUE, "Better stay at home!");
	
	private double topLimitMPS;
	private String advice;
	
	private EquipmentSuggestion(double topLimitMPS, String advice) {
		this.topLimitMPS = topLimitMPS;
		this.advice = advice;
	}

	public double getTopLimitMPS() {
		return topLimitMPS;
	}

	public String getAdvice() {
		return advice;
	}
}
