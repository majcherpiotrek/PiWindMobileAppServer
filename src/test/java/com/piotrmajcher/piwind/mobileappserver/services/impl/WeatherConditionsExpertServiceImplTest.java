package com.piotrmajcher.piwind.mobileappserver.services.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.piotrmajcher.piwind.mobileappserver.enums.RelativeWindDirection;
import com.piotrmajcher.piwind.mobileappserver.enums.WindDirection;
import com.piotrmajcher.piwind.mobileappserver.services.WeatherConditionsExpertService;
import com.piotrmajcher.piwind.mobileappserver.services.impl.WeatherConditionsExpertServiceImpl;

public class WeatherConditionsExpertServiceImplTest {

	private WeatherConditionsExpertServiceImpl service;
	
	public WeatherConditionsExpertServiceImplTest() {
		service = new WeatherConditionsExpertServiceImpl();
	}
	
	@Test
	public void beachFacingNWindNEShouldGiveCrossOnShore() {
		assertEquals(RelativeWindDirection.CROSSONSHORE, service.calculateRelativeWindDirection(WindDirection.N, WindDirection.NE));
	}
	
	@Test
	public void beachFacingNWindSShouldGiveOffShore() {
		assertEquals(RelativeWindDirection.OFFSHORE, service.calculateRelativeWindDirection(WindDirection.N, WindDirection.S));
	}
	
	@Test
	public void beachFacingNWindEShouldGiveCrossShore() {
		assertEquals(RelativeWindDirection.CROSSSHORE, service.calculateRelativeWindDirection(WindDirection.N, WindDirection.E));
	}
	
	@Test
	public void beachFacingNWindSEShouldGiveCrossOffShore() {
		assertEquals(RelativeWindDirection.CROSSOFFSHORE, service.calculateRelativeWindDirection(WindDirection.N, WindDirection.SE));
	}
	
	@Test
	public void beachFacingNWindSWShouldGiveCrossOffShore() {
		assertEquals(RelativeWindDirection.CROSSOFFSHORE, service.calculateRelativeWindDirection(WindDirection.N, WindDirection.SW));
	}
	
	@Test
	public void beachFacingNWindWShouldGiveCrossShore() {
		assertEquals(RelativeWindDirection.CROSSSHORE, service.calculateRelativeWindDirection(WindDirection.N, WindDirection.W));
	}
	
	@Test
	public void beachFacingNWindNWShouldGiveCrossOnShore() {
		assertEquals(RelativeWindDirection.CROSSONSHORE, service.calculateRelativeWindDirection(WindDirection.N, WindDirection.NW));
	}
	
	@Test
	public void beachFacingNWindNShouldGiveOnShore() {
		assertEquals(RelativeWindDirection.ONSHORE, service.calculateRelativeWindDirection(WindDirection.N, WindDirection.N));
	}
}
