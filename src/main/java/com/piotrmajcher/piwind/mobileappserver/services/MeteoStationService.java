package com.piotrmajcher.piwind.mobileappserver.services;

import java.util.List;
import java.util.UUID;

import com.piotrmajcher.piwind.mobileappserver.domain.MeteoStation;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.MeteoStationServiceException;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoStationTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.TemperatureTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.WindSpeedTO;

public interface MeteoStationService {

	UUID registerStation(MeteoStationTO stationTO) throws MeteoStationServiceException;
	
	List<MeteoStationTO> getAllStations();
	
	TemperatureTO getLatestTemperatureMeasurementFromStation(String stationId) throws MeteoStationServiceException;
	
	WindSpeedTO getLatestWindSpeedMeasurementFromStation(String stationId);
}
