package com.piotrmajcher.piwind.mobileappserver.services.impl;

import org.springframework.stereotype.Service;

import com.piotrmajcher.piwind.mobileappserver.enums.BeaufortScale;
import com.piotrmajcher.piwind.mobileappserver.enums.EquipmentSuggestion;
import com.piotrmajcher.piwind.mobileappserver.enums.RelativeWindDirection;
import com.piotrmajcher.piwind.mobileappserver.enums.TemperatureCategories;
import com.piotrmajcher.piwind.mobileappserver.enums.WaterConditions;
import com.piotrmajcher.piwind.mobileappserver.enums.WindDirection;
import com.piotrmajcher.piwind.mobileappserver.services.WeatherConditionsExpertService;

@Service
public class WeatherConditionsExpertServiceImpl implements WeatherConditionsExpertService {

	@Override
	public String getWindDirectionDescription(WindDirection beachFacingDirection, WindDirection windDirection) {
		RelativeWindDirection relativeWindDirection = calculateRelativeWindDirection(beachFacingDirection , windDirection);
		StringBuilder sb = new StringBuilder();
		sb.append(windDirection);
		sb.append(" - ");
		sb.append(relativeWindDirection.toString());
		return sb.toString();
	}
	
	@Override
	public String getWindBeaufortCategoryDescription(double windSpeed) {
		BeaufortScale category = calculateBeaufortCategory(windSpeed);
		StringBuilder sb = new StringBuilder();
		sb.append(category.ordinal());
		sb.append(" Bft - ");
		sb.append(category.getDescription());
		return sb.toString();
	}

	@Override
	public String getTemperatureConditionsDescription(double temperature) {
		TemperatureCategories category  = calculatetemperatureCategory(temperature);
		return category.getAdvice();
	}

	protected RelativeWindDirection calculateRelativeWindDirection(WindDirection beachFacingDirection, WindDirection currentWindDirection) {
		
		RelativeWindDirection relativeWindDirection;
		
		if (beachFacingDirection.equals(currentWindDirection)) {
			relativeWindDirection = RelativeWindDirection.ONSHORE;
		} else if (WindDirection.oppositeDirection(beachFacingDirection).equals(currentWindDirection)) {
			relativeWindDirection = RelativeWindDirection.OFFSHORE;
		} else if (WindDirection.perpendicularDirections(beachFacingDirection).contains(currentWindDirection)) {
			relativeWindDirection = RelativeWindDirection.CROSSSHORE;
		} else if (WindDirection.crossOppositeDirections(beachFacingDirection).contains(currentWindDirection)) {
			relativeWindDirection = RelativeWindDirection.CROSSOFFSHORE;
		} else if (WindDirection.crossSimilarDirections(beachFacingDirection).contains(currentWindDirection)) {
			relativeWindDirection = RelativeWindDirection.CROSSONSHORE;
		} else {
			throw new IllegalStateException("Unexpected error while calculating relative wind direction");
		}
		return relativeWindDirection;
	}
	
	private BeaufortScale calculateBeaufortCategory(double windSpeed) {
		BeaufortScale[] beaufortCategories = BeaufortScale.values();
		BeaufortScale categoryCalculated = null;
		for (int i=0; i<beaufortCategories.length; i++) {
			BeaufortScale category = beaufortCategories[i];
			
			if (category.equals(BeaufortScale.TWELVE)) {
				categoryCalculated = category;
				break;
			} else {
				BeaufortScale nextCategory = beaufortCategories[i+1];
				if (windSpeed >= category.getDownLimitMPS() && windSpeed < nextCategory.getDownLimitMPS()) {
					categoryCalculated = category;
					break;
				}
			}
		}
		
		if (categoryCalculated == null) {
			throw new IllegalStateException("Unexpected error while calculating the wind beaufort category");
		}
		return categoryCalculated;
	}
	
	private TemperatureCategories calculatetemperatureCategory(double temperature) {
		TemperatureCategories[] categories = TemperatureCategories.values();
		TemperatureCategories categoryCalculated = null;
		
		for (int i = 1; i < categories.length; i++) {
			TemperatureCategories category = categories[i];
			if (temperature <= TemperatureCategories.FREEZING.getTopLimit()) {
				categoryCalculated = TemperatureCategories.FREEZING;
				break;
			} else {
				TemperatureCategories lowerCategory = categories[i-1];
				if (temperature > lowerCategory.getTopLimit() && temperature <= category.getTopLimit()) {
					categoryCalculated = category;
					break;
				}
			}
		}
		
		if (categoryCalculated == null) {
			throw new IllegalStateException("Unexpected error while calculating the temperature category");
		}
		return categoryCalculated;
	}

	@Override
	public String getWaterConditionsDescription(WindDirection beachFacingDirection, WindDirection windDirection) {
		RelativeWindDirection relativeWindDirection = calculateRelativeWindDirection(beachFacingDirection, windDirection);
		WaterConditions waterConditions = calculateWaterConditions(relativeWindDirection);
		return waterConditions.toString();
	}

	private WaterConditions calculateWaterConditions(RelativeWindDirection relativeWindDirection) {
		WaterConditions waterConditions = WaterConditions.CHOPPY;
		switch (relativeWindDirection) {
			case ONSHORE:
				waterConditions = WaterConditions.WAVY;
				break;
			case CROSSONSHORE:
				waterConditions = WaterConditions.VERYCHOPPY;
				break;
			case CROSSSHORE:
				waterConditions = WaterConditions.CHOPPY;
				break;
			case CROSSOFFSHORE:
				waterConditions = WaterConditions.FLAT;
				break;
			case OFFSHORE:
				waterConditions = WaterConditions.FLAT;
				break;
		}
		return waterConditions;
	}

	@Override
	public String getEquipmentSuggestion(double windSpeed) {
		EquipmentSuggestion eqSuggestion = calculateEquipmentSuggestion(windSpeed);
		return eqSuggestion.getAdvice();
	}

	private EquipmentSuggestion calculateEquipmentSuggestion(double windSpeed) {
		EquipmentSuggestion[] categories = EquipmentSuggestion.values();
		EquipmentSuggestion categoryCalculated = null;
		
		for (int i = 1; i < categories.length; i++) {
			EquipmentSuggestion category = categories[i];
			if (windSpeed <= EquipmentSuggestion.VERY_BIG_SETUP.getTopLimitMPS()) {
				categoryCalculated = EquipmentSuggestion.VERY_BIG_SETUP;
				break;
			} else {
				EquipmentSuggestion lowerCategory = categories[i-1];
				if (windSpeed > lowerCategory.getTopLimitMPS() && windSpeed <= category.getTopLimitMPS()) {
					categoryCalculated = category;
					break;
				}
			}
		}
		
		if (categoryCalculated == null) {
			throw new IllegalStateException("Unexpected error while calculating the equipment suggestion");
		}
		return categoryCalculated;
	}
}
