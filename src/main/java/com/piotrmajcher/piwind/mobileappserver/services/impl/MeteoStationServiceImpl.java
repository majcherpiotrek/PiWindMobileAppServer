package com.piotrmajcher.piwind.mobileappserver.services.impl;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;
import com.piotrmajcher.piwind.mobileappserver.domain.MeteoStation;
import com.piotrmajcher.piwind.mobileappserver.domain.NotificationsRequest;
import com.piotrmajcher.piwind.mobileappserver.domain.UserEntity;
import com.piotrmajcher.piwind.mobileappserver.repository.MeteoStationRepository;
import com.piotrmajcher.piwind.mobileappserver.repository.NotificationsRequestRepository;
import com.piotrmajcher.piwind.mobileappserver.repository.UserRepository;
import com.piotrmajcher.piwind.mobileappserver.services.MeteoStationService;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.MeteoStationServiceException;
import com.piotrmajcher.piwind.mobileappserver.util.EntityAndTOConverter;
import com.piotrmajcher.piwind.mobileappserver.util.impl.MeteoStationEntityConverter;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoStationTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.WindStatisticsDataTO;

@Service
public class MeteoStationServiceImpl implements MeteoStationService{
	
	private static final Logger logger = Logger.getLogger(MeteoStationServiceImpl.class);
	
	private static final String REGISTER_STATION_NULL_ARG_ERROR = "Failed to register new station - passed argument is null.";
	private static final String REGISTRATION_EXCEPTION_OCCURED = "Exception occurred while trying to register new meteo station: ";
	private static final String DUPLICATE_STATION_NAME_ERROR = "A meteo station with this name already exists. Please choose another name.";
	private static final String STATION_NOT_FOUND = "Meteo station with specified id not found";
	private static final String FETCHING_STATISTICS_DATA_FAILED = "Fetching statistics data failed";
	
	private static final String LAST_METEO_DATA_URL = "/meteo/last";
	private static final String LAST_SNAPSHOT = "/webcam/latest-snap";
	private final MeteoStationRepository meteoStationRepository;
	private final NotificationsRequestRepository notificationsRequestRepository;
	private final UserRepository userRepository;
	private final EntityAndTOConverter<MeteoStation, MeteoStationTO> converter;
	
	private final RestTemplate restTemplate;
	
	@Autowired
	public MeteoStationServiceImpl(MeteoStationRepository meteoStationRepository, 
			NotificationsRequestRepository notificationsRequestRepository,
			UserRepository userRepository) {
		this.meteoStationRepository = meteoStationRepository;
		this.notificationsRequestRepository = notificationsRequestRepository;
		this.userRepository = userRepository;
		this.converter = new MeteoStationEntityConverter();
		this.restTemplate = new RestTemplate();
	}
	
	@Override
	public UUID registerStation(MeteoStationTO stationTO) throws MeteoStationServiceException {
		try {
			Assert.notNull(stationTO, REGISTER_STATION_NULL_ARG_ERROR);
			return meteoStationRepository.save(converter.transferObjectToEntity(stationTO)).getId();
		} catch (Exception e) {
			String errorMessage = getPersistanceExceptionErrorMessage(e);
            throw new MeteoStationServiceException(errorMessage);
		}
	}

	private String getPersistanceExceptionErrorMessage(Exception e) {
		String errorMessage = null;
		if (e instanceof DataIntegrityViolationException) {
			errorMessage = DUPLICATE_STATION_NAME_ERROR;
		}
		if (e instanceof TransactionSystemException) {
		    Throwable cause = e.getCause();
		    while ( (cause != null) && !(cause instanceof ConstraintViolationException) ) {
		        cause = cause.getCause();
		    }

		    if (cause != null) {
		        StringBuilder message = new StringBuilder();
		        Set<ConstraintViolation<?>> violations = ((ConstraintViolationException)cause).getConstraintViolations();
		        for (ConstraintViolation<?> violation : violations) {
		            message.append(violation.getMessage());
		        }
		        
		        errorMessage = message.toString();
		    }
		}

		if (errorMessage == null) {
		    errorMessage = e.getMessage();
		}

		logger.error(REGISTRATION_EXCEPTION_OCCURED + errorMessage + "\n");
		e.printStackTrace();
		return errorMessage;
	}

	@Override
	public List<MeteoStationTO> getAllStations() {
		return converter.entityToTransferObject(meteoStationRepository.findAll());
	}
	
	

	@Override
	public MeteoDataTO getLatestMeteoDataFromStation(UUID stationId) throws MeteoStationServiceException {
		MeteoStation station = meteoStationRepository.findById(stationId);
		if (station == null) {
			throw new MeteoStationServiceException(STATION_NOT_FOUND);
		}
		
		MeteoDataTO meteoDataTO = null;
		try {
			meteoDataTO =	restTemplate.getForObject(station.getStationBaseURL() + LAST_METEO_DATA_URL, MeteoDataTO.class);
			Assert.notNull(meteoDataTO, "Failed to fetch data");
		} catch (Exception e) {
			throw new MeteoStationServiceException(e.getMessage());
		}
		return meteoDataTO;
	}

	@Override
	public MeteoStationTO getStation(UUID stationId) throws MeteoStationServiceException {
		MeteoStation meteoStation = meteoStationRepository.findById(stationId);
		if (meteoStation == null) {
			throw new MeteoStationServiceException(STATION_NOT_FOUND);
		}
		
		return converter.entityToTransferObject(meteoStation);
	}

	@Override
	public byte[] getLatestSnapshotFromStation(UUID stationId) throws MeteoStationServiceException {
		MeteoStation station = meteoStationRepository.findById(stationId);
		if (station == null) {
			throw new MeteoStationServiceException(STATION_NOT_FOUND);
		}
		
		byte[] snapshot = null;
		try {
			snapshot =	restTemplate.getForObject(station.getStationBaseURL() + LAST_SNAPSHOT, byte[].class);
			Assert.notNull(snapshot, "Failed to fetch data");
		} catch (Exception e) {
			throw new MeteoStationServiceException(e.getMessage());
		}
		return snapshot;
	}

	@Override
	public List<WindStatisticsDataTO> getMeteoDataFromLastXMinutes(UUID stationId, int samples, int intervalMinutes) throws MeteoStationServiceException {
		MeteoStation meteoStation = meteoStationRepository.findById(stationId);
		if (meteoStation == null) {
			throw new MeteoStationServiceException(STATION_NOT_FOUND);
		}
		int minutes= samples * intervalMinutes;
		// /last/{minutes}/minutes
		StringBuilder sb = new StringBuilder();
		sb.append(meteoStation.getStationBaseURL());
		sb.append("/meteo");
		sb.append("/last/");
		sb.append(minutes);
		sb.append("/minutes");
		
		ResponseEntity<MeteoDataTO[]> responseEntity = restTemplate.getForEntity(sb.toString(), MeteoDataTO[].class);
	
		if (responseEntity.getStatusCode() != HttpStatus.OK) {
			throw new MeteoStationServiceException(FETCHING_STATISTICS_DATA_FAILED);
		}
		
		MeteoDataTO[] meteoData = responseEntity.getBody();
		int actualSamplesReceived = calculateReceivedSamplesNumber(meteoData, intervalMinutes);
		List<WindStatisticsDataTO> result = null;
		try {
			result = createWindStatisticsFromMeteoData(meteoData, actualSamplesReceived);
		} catch (IllegalArgumentException e) {
			throw new MeteoStationServiceException(e.getMessage());
		}
		
		return result;
	}

	protected int calculateReceivedSamplesNumber(MeteoDataTO[] meteoData, int intervalMinutes) {
		int result = 0;
		if (meteoData.length != 0) {
			long oldestSampleSeconds = meteoData[0].getDateTime().atZone(ZoneId.systemDefault()).toEpochSecond();
			long newestSampleSeconds = meteoData[meteoData.length - 1].getDateTime().atZone(ZoneId.systemDefault()).toEpochSecond();
			long timeDiffSeconds = newestSampleSeconds - oldestSampleSeconds;
			long intervalSeconds = intervalMinutes * 60;
			if (timeDiffSeconds < intervalSeconds) {
				result = 1;
			} else {
				result = Math.toIntExact(timeDiffSeconds / intervalSeconds);
				if (timeDiffSeconds - result * intervalSeconds > 0) {
					result++;
				}
			}
		}
		
		return result;
	}

	protected List<WindStatisticsDataTO> createWindStatisticsFromMeteoData(MeteoDataTO[] meteoData, int samples) throws IllegalArgumentException{
		List<WindStatisticsDataTO> windStatistics = new LinkedList<>();
		if (samples > 0) {
			int partition = meteoData.length / samples;
			List<List<MeteoDataTO>> meteoDataSamples = Lists.partition(Arrays.asList(meteoData), partition); // can throw exception i partition too big
			int diff = meteoDataSamples.size() - samples;
			meteoDataSamples = meteoDataSamples.subList(diff, meteoDataSamples.size());
			
			for (int i=0; i < samples; i++) {
				List<MeteoDataTO> sampleList = meteoDataSamples.get(i);
				WindStatisticsDataTO statistic = new WindStatisticsDataTO();
				float avgWind = 0f;
				float maxGust = 0f;
				float minGust = Float.MAX_VALUE;
				for (MeteoDataTO sample :sampleList) {
					float wind = (float) sample.getWindSpeed();
					avgWind += wind;
					if (wind > maxGust) {
						maxGust = wind;
					}
					 if (wind < minGust) {
						 minGust = wind;
					 }
				}
				
				avgWind = avgWind / sampleList.size();
				
				statistic.setAvgWind(avgWind);
				statistic.setMaxGust(maxGust);
				statistic.setMinGust(minGust);
				Date date = Date.from(sampleList.get(sampleList.size() - 1).getDateTime().atZone(ZoneId.systemDefault()).toInstant());
				statistic.setDate(date.getTime());
				
				windStatistics.add(statistic);
			}
		}
		
		return windStatistics;
	}

	@Override
	public void addNotificationsRequest(UUID stationId, String username, Integer minWindLimit) throws MeteoStationServiceException {
		MeteoStation meteoStation = meteoStationRepository.findById(stationId);
		if (meteoStation == null) {
			throw new MeteoStationServiceException("Station doesn't exist!");
		}
		if (minWindLimit == null || minWindLimit < 0) {
			throw new MeteoStationServiceException("Minimal wind limit has to be specified! Minimal wind limit has to be >= 0");
		}
		
		UserEntity userEntity = userRepository.findByUsername(username);
		
		NotificationsRequest notificationsRequest = new NotificationsRequest();
		notificationsRequest.setMeteoStation(meteoStation);
		notificationsRequest.setUser(userEntity);
		notificationsRequest.setMinWindLimit(minWindLimit);
		
		notificationsRequestRepository.save(notificationsRequest);
	}

	@Override
	public void changeNotificationsRequestMinWindLimit(UUID stationId, String username, Integer newMinWindLimit) throws MeteoStationServiceException {
		NotificationsRequest notificationsRequest = getNotificationsRequestForStationAndUser(stationId, username);
		if (notificationsRequest == null) {
			throw new MeteoStationServiceException("No notifications request for this station and user!");
		}
		
		if (newMinWindLimit == null || newMinWindLimit < 0) {
			throw new MeteoStationServiceException("Minimal wind limit has to be specified! Minimal wind limit has to be >= 0");
		}
		
		notificationsRequest.setMinWindLimit(newMinWindLimit);
		notificationsRequestRepository.save(notificationsRequest);
	}
	
	@Override
	public void cancelNotificationsRequest(UUID stationId, String username) throws MeteoStationServiceException {
		NotificationsRequest notificationsRequest = getNotificationsRequestForStationAndUser(stationId, username);
		if (notificationsRequest != null) {
			notificationsRequestRepository.delete(notificationsRequest);
		}
	}
	
	
	@Override
	public List<NotificationsRequest> findAllNotificationsRequestsForStation(UUID stationId) {
		return notificationsRequestRepository.findByMeteoStationId(stationId);
	}

	private NotificationsRequest getNotificationsRequestForStationAndUser(UUID stationId, String username)
			throws MeteoStationServiceException {
		MeteoStation meteoStation = meteoStationRepository.findById(stationId);
		if (meteoStation == null) {
			throw new MeteoStationServiceException("Station doesn't exist!");
		}
		UserEntity userEntity = userRepository.findByUsername(username);
		NotificationsRequest notificationsRequest = notificationsRequestRepository.findByMeteoStation_idAndUser_id(stationId, userEntity.getId());
		return notificationsRequest;
	}
}
