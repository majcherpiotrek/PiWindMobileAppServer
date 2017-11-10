package com.piotrmajcher.piwind.mobileappserver.services;

import java.util.List;
import java.util.UUID;

import com.piotrmajcher.piwind.mobileappserver.services.exceptions.MeteoStationServiceException;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoStationTO;

public interface MeteoStationService {

	UUID registerStation(MeteoStationTO stationTO) throws MeteoStationServiceException;
	
	List<MeteoStationTO> getAllStations();
	
	MeteoDataTO getLatestMeteoData(UUID stationId) throws MeteoStationServiceException;
}
