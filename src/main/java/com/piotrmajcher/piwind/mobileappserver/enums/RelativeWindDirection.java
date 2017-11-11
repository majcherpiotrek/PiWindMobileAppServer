package com.piotrmajcher.piwind.mobileappserver.enums;

public enum RelativeWindDirection {
	ONSHORE("onshore"),
	CROSSONSHORE("cross-onshore"),
	CROSSSHORE("cross-shore"),
	CROSSOFFSHORE("cross-offshore"),
	OFFSHORE("offshore");
	
	private final String name;
	
	private RelativeWindDirection(final String name) {
		this.name = name;
	}
	
	public boolean equalsName(String otherName) {
		return name.equals(otherName);
	}
	
	public String toString() {
		return name;
	}
}
