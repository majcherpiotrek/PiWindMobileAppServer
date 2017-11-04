package com.piotrmajcher.piwind.mobileappserver.web.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piotrmajcher.piwind.mobileappserver.domain.MeteoStation;
import com.piotrmajcher.piwind.mobileappserver.dto.MeteoStationTO;
import com.piotrmajcher.piwind.mobileappserver.services.MeteoStationService;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.MeteoStationServiceException;
import com.piotrmajcher.piwind.mobileappserver.util.EntityAndTOConverter;
import com.piotrmajcher.piwind.mobileappserver.util.impl.MeteoStationEntityConverter;

@RestController
@RequestMapping("/stations")
public class MeteoStationController {
	
	private final MeteoStationService meteoStationService;
	
	private static final String STATION_REGISTRATION_SUCCESS = "Successfully registered new station with id ";
	@Autowired
	public MeteoStationController(MeteoStationService meteoStationService) {
		this.meteoStationService = meteoStationService;
	}
	
	@PostMapping("/register")
	@CrossOrigin
	public ResponseEntity<String> registerStation(@RequestBody MeteoStationTO stationTO) {
		try {
			
			UUID savedStationId = meteoStationService.registerStation(stationTO);
			return new ResponseEntity<>(STATION_REGISTRATION_SUCCESS + savedStationId.toString(), HttpStatus.OK);
		} catch (MeteoStationServiceException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/all")
	@CrossOrigin
	public ResponseEntity<List<MeteoStationTO>> getAllMeteoStations() {
		return new ResponseEntity<> (meteoStationService.getAllStations(), HttpStatus.OK);
	}
}
