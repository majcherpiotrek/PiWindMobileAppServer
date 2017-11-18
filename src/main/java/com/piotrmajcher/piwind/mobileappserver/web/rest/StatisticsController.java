package com.piotrmajcher.piwind.mobileappserver.web.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piotrmajcher.piwind.mobileappserver.services.MeteoStationService;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.MeteoStationServiceException;
import com.piotrmajcher.piwind.mobileappserver.web.dto.WindStatisticsDataTO;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
	
	private MeteoStationService meteoStationService;
	
	@Autowired
	public StatisticsController(MeteoStationService meteoStationService) {
		this.meteoStationService = meteoStationService;
	}
	@GetMapping("/{stationId}/{samples}/{interval}")
	@CrossOrigin
	public ResponseEntity<List<WindStatisticsDataTO>> getWindStatisticsFromXMinutes(@PathVariable String stationId, @PathVariable int samples , @PathVariable int interval) {
		if (stationId == null || samples <= 0 || interval <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		
		List<WindStatisticsDataTO> windStatistics;

		try {
			windStatistics = meteoStationService.getMeteoDataFromLastXMinutes(UUID.fromString(stationId.trim()), samples, interval);
		} catch(MeteoStationServiceException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
         return new ResponseEntity<>(windStatistics, HttpStatus.OK);	
	}

}
