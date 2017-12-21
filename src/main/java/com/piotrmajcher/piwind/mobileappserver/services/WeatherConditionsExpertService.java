package com.piotrmajcher.piwind.mobileappserver.services;

import com.piotrmajcher.piwind.mobileappserver.enums.WindDirection;

public interface WeatherConditionsExpertService {

	String getWindDirectionDescription(WindDirection beachFacingDirection, WindDirection windDirection);
	
	String getWindBeaufortCategoryDescription(double windSpeed);
	
	String getTemperatureConditionsDescription(double temperature);
	
	String getWaterConditionsDescription(WindDirection beachFacingDirection, WindDirection windDirection);
	
	String getEquipmentSuggestion(double windSpeed);
}
