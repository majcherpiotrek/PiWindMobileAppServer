package com.piotrmajcher.piwind.mobileappserver.services;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;

import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTOAndroid;

public interface AndroidPushNotificationsService {
	
	CompletableFuture<String> send(HttpEntity<String> entity);
	
	void handleMeteoDataUpdate(UUID stationId, MeteoDataTOAndroid updatedMeteoData);
}
