package com.piotrmajcher.piwind.mobileappserver.services;

import java.util.List;
import java.util.UUID;

import com.piotrmajcher.piwind.mobileappserver.domain.NotificationsRequest;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.MeteoStationServiceException;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoStationTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.WindStatisticsDataTO;

public interface MeteoStationService {

	UUID registerStation(MeteoStationTO stationTO) throws MeteoStationServiceException;
	
	List<MeteoStationTO> getAllStations();
	
	MeteoStationTO getStation(UUID stationId) throws MeteoStationServiceException;
	
	MeteoDataTO getLatestMeteoDataFromStation(UUID stationId) throws MeteoStationServiceException;
	
	byte[] getLatestSnapshotFromStation(UUID stationId) throws MeteoStationServiceException;
	
	List<WindStatisticsDataTO> getMeteoDataFromLastXMinutes(UUID stationId, int samples, int interval) throws MeteoStationServiceException;
	
	void addNotificationsRequest(UUID stationId, String username, Integer minWindLimit) throws MeteoStationServiceException;
	
	void cancelNotificationsRequest(UUID stationId, String username) throws MeteoStationServiceException;
	
	List<NotificationsRequest> findAllNotificationsRequestsForStation(UUID stationId);
}
