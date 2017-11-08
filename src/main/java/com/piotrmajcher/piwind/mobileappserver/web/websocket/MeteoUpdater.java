package com.piotrmajcher.piwind.mobileappserver.web.websocket;

import java.util.UUID;

import com.piotrmajcher.piwind.mobileappserver.services.MeteoStationService;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.MeteoStationServiceException;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.TemperatureTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.WindSpeedTO;

public class MeteoUpdater implements Runnable {
	
	private MeteoDataRefreshController controller;
	private MeteoStationService meteoStationService;
	private UUID stationId;
	private boolean isRunning = true;
	
	public MeteoUpdater(MeteoDataRefreshController controller, MeteoStationService meteoStationService, UUID stationId) {
		this.controller = controller;
		this.meteoStationService = meteoStationService;
		this.stationId = stationId;
	}
	
	public synchronized void stop() {
		this.isRunning = false;
	}
	
	private synchronized boolean isRunning() {
		return this.isRunning;
	}
	
	@Override
	public void run() {
		while(isRunning()) {
			try {
				Thread.sleep(5000);
				
				try {
					TemperatureTO temperatureTO = meteoStationService.getLatestTemperatureMeasurementFromStation(stationId);
					WindSpeedTO windSpeedTO = meteoStationService.getLatestWindSpeedMeasurementFromStation(stationId);
					MeteoDataTO meteoData = new MeteoDataTO(temperatureTO.getTemperatureCelsius(), windSpeedTO.getWindSpeedMPS());
					controller.fireUpdate(meteoData);
				} catch (MeteoStationServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
