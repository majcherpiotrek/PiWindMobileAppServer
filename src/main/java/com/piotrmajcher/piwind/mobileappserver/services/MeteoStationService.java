package com.piotrmajcher.piwind.mobileappserver.services;

import java.util.List;
import java.util.UUID;

import com.piotrmajcher.piwind.mobileappserver.domain.MeteoStation;
import com.piotrmajcher.piwind.mobileappserver.dto.MeteoStationTO;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.MeteoStationServiceException;

public interface MeteoStationService {

	UUID registerStation(MeteoStationTO stationTO) throws MeteoStationServiceException;
	
	List<MeteoStationTO> getAllStations();
}
