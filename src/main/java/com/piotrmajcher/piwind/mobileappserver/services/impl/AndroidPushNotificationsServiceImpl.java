package com.piotrmajcher.piwind.mobileappserver.services.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.piotrmajcher.piwind.mobileappserver.services.AndroidPushNotificationsService;
import com.piotrmajcher.piwind.mobileappserver.web.rest.HeaderRequestInterceptor;

@Service
public class AndroidPushNotificationsServiceImpl implements AndroidPushNotificationsService {
	private static final Logger logger = Logger.getLogger(AndroidPushNotificationsServiceImpl.class);
	private static final String FIREBASE_SERVER_KEY = "AAAAskASe5Q:APA91bHI37wmTGb063qT80i3RZ35tIpVKjQq7D-OxhL_EApX-WoL-K_Vg2XO8RPO-TzLSrG7ZYl-OgMhYThaaXcJXcrpxa71rkrInp7V0H4sXodz1DPcp4hK13nprKL3jp1OH8WbLr9B";
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
	
	private RestTemplate restTemplate;
	
	@Autowired
	public AndroidPushNotificationsServiceImpl() {
		this.restTemplate = new RestTemplate();
	}
	
	@Async
	@Override
	public CompletableFuture<String> send(HttpEntity<String> entity) {
		
		/**
		https://fcm.googleapis.com/fcm/send
		Content-Type:application/json
		Authorization:key=FIREBASE_SERVER_KEY*/
		
		List<ClientHttpRequestInterceptor> interceptors = new LinkedList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);
 
		String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);
 
		return CompletableFuture.completedFuture(firebaseResponse);
	}

}
