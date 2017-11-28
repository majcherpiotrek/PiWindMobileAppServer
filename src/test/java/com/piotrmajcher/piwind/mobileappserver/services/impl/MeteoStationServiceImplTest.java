package com.piotrmajcher.piwind.mobileappserver.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.piotrmajcher.piwind.mobileappserver.repository.MeteoStationRepository;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.WindStatisticsDataTO;


@RunWith(MockitoJUnitRunner.class)
public class MeteoStationServiceImplTest {
	
	@InjectMocks
    private	MeteoStationServiceImpl meteoStationService;

    @Mock
    private MeteoStationRepository meteoStationRepository;

    @Before
    public void injectMocks() {
        MockitoAnnotations.initMocks(this);
    }
    
	@Test
	public void createWindStatisticsFromMeteoDataTest() {
		MeteoDataTO[] meteoData = new MeteoDataTO[6];
		LocalDateTime now = LocalDateTime.now();
		
		MeteoDataTO data1 = new MeteoDataTO();
		data1.setWindSpeed(10.0);
		data1.setDateTime(now);
		
		MeteoDataTO data2 = new MeteoDataTO();
		data2.setWindSpeed(15.0);
		data2.setDateTime(now);
		
		MeteoDataTO data3 = new MeteoDataTO();
		data3.setWindSpeed(5.0);
		data3.setDateTime(now);
		
		MeteoDataTO data4 = new MeteoDataTO();
		data4.setWindSpeed(14.0);
		data4.setDateTime(now);
		
		MeteoDataTO data5 = new MeteoDataTO();
		data5.setWindSpeed(10.0);
		data5.setDateTime(now);
		
		MeteoDataTO data6 = new MeteoDataTO();
		data6.setWindSpeed(9.0);
		data6.setDateTime(now);
		
		meteoData[0] = data1;
		meteoData[1] = data2;
		meteoData[2] = data3;
		meteoData[3] = data4;
		meteoData[4] = data5;
		meteoData[5] = data6;
		
		int samples = 2;
		List<WindStatisticsDataTO> statisticsList = meteoStationService.createWindStatisticsFromMeteoData(meteoData, samples);
		
		assertNotNull(statisticsList);
		assertEquals(statisticsList.size(), samples);
		assertTrue(Math.abs(10f - statisticsList.get(0).getAvgWind()) < 0.1f );
		assertTrue(Math.abs(15f - statisticsList.get(0).getMaxGust()) < 0.1f );
		assertTrue(Math.abs(5f - statisticsList.get(0).getMinGust()) < 0.1f );
		
		assertTrue(Math.abs(11f - statisticsList.get(1).getAvgWind()) < 0.1f);
		assertTrue(Math.abs(14f - statisticsList.get(1).getMaxGust()) < 0.1f);
		assertTrue(Math.abs(9f - statisticsList.get(1).getMinGust()) < 0.1f);
		
		System.out.println(statisticsList);
	}
	
	@Test
	public void calculateReceivedSamplesNumberTest() {
		MeteoDataTO[] meteoData = new MeteoDataTO[3];
		long fiveMinutes= 5 * 60 * 1000;
		Date now = new Date();
		Date now5MinLater = new Date(now.getTime() + fiveMinutes);
		Date now10MinLater = new Date(now5MinLater.getTime() + fiveMinutes);
		
		LocalDateTime date1 = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault());
		LocalDateTime date2 = LocalDateTime.ofInstant(now5MinLater.toInstant(), ZoneId.systemDefault());
		LocalDateTime date3 = LocalDateTime.ofInstant(now10MinLater.toInstant(), ZoneId.systemDefault());
		
		MeteoDataTO data1 = new MeteoDataTO();
		data1.setDateTime(date1);
		
		MeteoDataTO data2 = new MeteoDataTO();
		data2.setDateTime(date2);
		
		MeteoDataTO data3 = new MeteoDataTO();
		data3.setDateTime(date3);
		
		meteoData[0] = data1;
		meteoData[1] = data2;
		meteoData[2] = data3;
		
		int samples = meteoStationService.calculateReceivedSamplesNumber(meteoData, 5);
		
		assertEquals(2, samples);
	}
}
