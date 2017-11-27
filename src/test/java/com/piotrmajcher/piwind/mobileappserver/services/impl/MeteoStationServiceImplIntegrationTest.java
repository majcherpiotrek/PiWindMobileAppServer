package com.piotrmajcher.piwind.mobileappserver.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.piotrmajcher.piwind.mobileappserver.domain.MeteoStation;
import com.piotrmajcher.piwind.mobileappserver.domain.NotificationsRequest;
import com.piotrmajcher.piwind.mobileappserver.domain.UserEntity;
import com.piotrmajcher.piwind.mobileappserver.enums.WindDirection;
import com.piotrmajcher.piwind.mobileappserver.repository.MeteoStationRepository;
import com.piotrmajcher.piwind.mobileappserver.repository.NotificationsRequestRepository;
import com.piotrmajcher.piwind.mobileappserver.repository.UserRepository;
import com.piotrmajcher.piwind.mobileappserver.services.MeteoStationService;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.MeteoStationServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeteoStationServiceImplIntegrationTest {
	
	private static final String STATION = "Station";

	private static final String USERNAME = "username";

	@Autowired
	private MeteoStationService meteoStationService;
	
	@Autowired
	private MeteoStationRepository meteoStationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NotificationsRequestRepository notificationsRequestRepository;
	
	@Before
	public void init() throws MeteoStationServiceException {
		notificationsRequestRepository.deleteAll();
		UserEntity userEntity = userRepository.findByUsername(USERNAME);
		if (userEntity != null) {
			userRepository.delete(userEntity);
		}
		userEntity = new UserEntity();
		userEntity.setUsername(USERNAME);
		userEntity.setPassword("password");
		userEntity.setEmail("example@mail.com");
		userEntity.setEnabled(true);
		userRepository.save(userEntity);
		
		MeteoStation meteoStation = meteoStationRepository.findByName(STATION);
		if (meteoStation != null) {
			meteoStationRepository.delete(meteoStation);
		}
		meteoStation = new MeteoStation();
		meteoStation.setName(STATION);
		meteoStation.setDescription("description");
		meteoStation.setStationBaseURL("http://example.com");
		String[] bestWindDirections = {"N", "W", "NW"};
		meteoStation.setBestWindDirections(bestWindDirections);
		meteoStation.setBeachFacingDirection(WindDirection.N);
		
		meteoStationRepository.save(meteoStation);
	}

	@Test
	public void addNotificationsRequest() throws MeteoStationServiceException {
		UserEntity userEntity = userRepository.findByUsername(USERNAME);
		MeteoStation meteoStation = meteoStationRepository.findByName(STATION);
		assertNotNull(userEntity);
		assertNotNull(meteoStation);
		
		Integer minWindLimit = 20;
		
		meteoStationService.addNotificationsRequest(meteoStation.getId(), USERNAME, minWindLimit);
		NotificationsRequest notificationsRequest = notificationsRequestRepository.findByMeteoStationIdAndUserId(meteoStation.getId(), userEntity.getId());
		assertNotNull(notificationsRequest);
		assertEquals(minWindLimit, notificationsRequest.getMinWindLimit());
	}
	
	@Test(expected = MeteoStationServiceException.class)
	public void addNotificationsRequestForNotExistingStationShouldThrowException() throws MeteoStationServiceException {
		UserEntity userEntity = userRepository.findByUsername(USERNAME);
		assertNotNull(userEntity);
		
		Integer minWindLimit = 20;
		
		meteoStationService.addNotificationsRequest(UUID.randomUUID(), USERNAME, minWindLimit);
	}
	
	@Test(expected = MeteoStationServiceException.class)
	public void addNotificationsRequestWithNullWindLimitShouldThrowException() throws MeteoStationServiceException {
		UserEntity userEntity = userRepository.findByUsername(USERNAME);
		MeteoStation meteoStation = meteoStationRepository.findByName(STATION);
		assertNotNull(userEntity);
		assertNotNull(meteoStation);
		
		meteoStationService.addNotificationsRequest(meteoStation.getId(), USERNAME, null);
	}
	
	@Test(expected = MeteoStationServiceException.class)
	public void addNotificationsRequestWithInvalidWindLimitShouldThrowException() throws MeteoStationServiceException {
		UserEntity userEntity = userRepository.findByUsername(USERNAME);
		MeteoStation meteoStation = meteoStationRepository.findByName(STATION);
		assertNotNull(userEntity);
		assertNotNull(meteoStation);
		
		Integer minWindLimit = -1;
		
		meteoStationService.addNotificationsRequest(meteoStation.getId(), USERNAME, minWindLimit);
	}
	
	@Test
	public void shouldChangeNotificationsRequestMinWindLimit() throws MeteoStationServiceException {
		UserEntity userEntity = userRepository.findByUsername(USERNAME);
		MeteoStation meteoStation = meteoStationRepository.findByName(STATION);
		assertNotNull(userEntity);
		assertNotNull(meteoStation);
		
		Integer minWindLimit = 20;
		
		meteoStationService.addNotificationsRequest(meteoStation.getId(), USERNAME, minWindLimit);
		NotificationsRequest notificationsRequest = notificationsRequestRepository.findByMeteoStationIdAndUserId(meteoStation.getId(), userEntity.getId());
		assertNotNull(notificationsRequest);
		assertEquals(minWindLimit, notificationsRequest.getMinWindLimit());
		
		Integer newMinWindLimit = 30;
		meteoStationService.changeNotificationsRequestMinWindLimit(meteoStation.getId(), USERNAME, newMinWindLimit);
		notificationsRequest = notificationsRequestRepository.findByMeteoStationIdAndUserId(meteoStation.getId(), userEntity.getId());
		assertNotNull(notificationsRequest);
		assertTrue(notificationsRequestRepository.findAll().size() == 1);
		assertEquals(newMinWindLimit, notificationsRequest.getMinWindLimit());
	}
	
	@Test(expected = MeteoStationServiceException.class) 
	public void changeNotificationsRequestMinWindLimitNullMinWindLimitShouldThrowException() throws MeteoStationServiceException {
		UserEntity userEntity = userRepository.findByUsername(USERNAME);
		MeteoStation meteoStation = meteoStationRepository.findByName(STATION);
		assertNotNull(userEntity);
		assertNotNull(meteoStation);
		
		Integer minWindLimit = 20;
		
		meteoStationService.addNotificationsRequest(meteoStation.getId(), USERNAME, minWindLimit);
		NotificationsRequest notificationsRequest = notificationsRequestRepository.findByMeteoStationIdAndUserId(meteoStation.getId(), userEntity.getId());
		assertNotNull(notificationsRequest);
		assertEquals(minWindLimit, notificationsRequest.getMinWindLimit());
		
		meteoStationService.changeNotificationsRequestMinWindLimit(meteoStation.getId(), USERNAME, null);
	}
	
	@Test(expected = MeteoStationServiceException.class) 
	public void changeNotificationsRequestMinWindLimitInvalidMinWindLimitShouldThrowException() throws MeteoStationServiceException {
		UserEntity userEntity = userRepository.findByUsername(USERNAME);
		MeteoStation meteoStation = meteoStationRepository.findByName(STATION);
		assertNotNull(userEntity);
		assertNotNull(meteoStation);
		
		Integer minWindLimit = 20;
		
		meteoStationService.addNotificationsRequest(meteoStation.getId(), USERNAME, minWindLimit);
		NotificationsRequest notificationsRequest = notificationsRequestRepository.findByMeteoStationIdAndUserId(meteoStation.getId(), userEntity.getId());
		assertNotNull(notificationsRequest);
		assertEquals(minWindLimit, notificationsRequest.getMinWindLimit());
		
		Integer newMinWindLimit = -1;
		
		meteoStationService.changeNotificationsRequestMinWindLimit(meteoStation.getId(), USERNAME, newMinWindLimit);
	}
	
	@Test
	public void cancelExistingNotificationsRequestShouldRemoveNotificationsRequest() throws MeteoStationServiceException {
		UserEntity userEntity = userRepository.findByUsername(USERNAME);
		MeteoStation meteoStation = meteoStationRepository.findByName(STATION);
		assertNotNull(userEntity);
		assertNotNull(meteoStation);
		
		Integer minWindLimit = 20;
		
		meteoStationService.addNotificationsRequest(meteoStation.getId(), USERNAME, minWindLimit);
		NotificationsRequest notificationsRequest = notificationsRequestRepository.findByMeteoStationIdAndUserId(meteoStation.getId(), userEntity.getId());
		assertNotNull(notificationsRequest);
		assertEquals(minWindLimit, notificationsRequest.getMinWindLimit());
		
		int repoSizeBefore = notificationsRequestRepository.findAll().size();
		meteoStationService.cancelNotificationsRequest(meteoStation.getId(), USERNAME);
		assertNull(notificationsRequestRepository.findByMeteoStationIdAndUserId(meteoStation.getId(), userEntity.getId()));
		assertTrue(repoSizeBefore - notificationsRequestRepository.findAll().size() == 1);
	}
}
