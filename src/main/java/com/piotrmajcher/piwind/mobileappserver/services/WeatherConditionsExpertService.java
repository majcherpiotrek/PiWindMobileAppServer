package com.piotrmajcher.piwind.mobileappserver.services;

import com.piotrmajcher.piwind.mobileappserver.domain.MeteoStation;
import com.piotrmajcher.piwind.mobileappserver.enums.WindDirection;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTO;

public interface WeatherConditionsExpertService {

	String getWindDirectionDescription(WindDirection beachFacingDirection, WindDirection windDirection);
	
	String getWindBeaufortCategoryDescription(double windSpeed);
	
	String getTemperatureConditionsDescription(double temperature);
}
