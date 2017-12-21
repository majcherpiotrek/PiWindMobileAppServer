package com.piotrmajcher.piwind.mobileappserver.enums;

public enum WaterConditions {
	
	FLAT("Flatwater conditions."),
	WAVY("Possible waves or big chop."),
	VERYCHOPPY("Very choppy, small waves possible."),
	CHOPPY("Choppy water.");
	
	private final String name;
	
	private WaterConditions(final String name) {
		this.name = name;
	}
	
	public boolean equalsName(String otherName) {
		return name.equals(otherName);
	}
	
	public String toString() {
		return name;
	}
}
