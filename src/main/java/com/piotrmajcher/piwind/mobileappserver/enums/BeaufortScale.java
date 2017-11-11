package com.piotrmajcher.piwind.mobileappserver.enums;

public enum BeaufortScale {
	ZERO(0.0, "Calm"),
	ONE(0.3, "Light air"),
	TWO(1.5, "Light breeze"),
	THREE(3.3, "Gentle breeze"),
	FOUR(5.5, "Moderate breeze"),
	FIVE(7.9, "Fresh breeze"),
	SIX(10.7, "Strong breeze"),
	SEVEN(13.9, "Near gale"),
	EIGHT(17.2, "Gale"),
	NINE(20.8, "Strong gale"),
	TEN(24.5, "Storm"),
	ELEVEN(28.5, "Violent storm"),
	TWELVE(32.7, "Hurricane force");
	
	private double downLimitMPS;
	private String description;
	
	private BeaufortScale(double downLimitMPS, String descirption) {
		this.downLimitMPS = downLimitMPS;
		this.description = descirption;
	}

	public double getDownLimitMPS() {
		return downLimitMPS;
	}

	public String getDescription() {
		return description;
	}	
}
