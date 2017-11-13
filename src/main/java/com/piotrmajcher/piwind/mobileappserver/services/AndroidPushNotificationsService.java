package com.piotrmajcher.piwind.mobileappserver.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;

public interface AndroidPushNotificationsService {
	
	CompletableFuture<String> send(HttpEntity<String> entity);
}
