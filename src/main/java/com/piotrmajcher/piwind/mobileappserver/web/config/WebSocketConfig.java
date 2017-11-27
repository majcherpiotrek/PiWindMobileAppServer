package com.piotrmajcher.piwind.mobileappserver.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.piotrmajcher.piwind.mobileappserver.web.websocket.MeteoDataUpdateWebsocketHandler;
import com.piotrmajcher.piwind.mobileappserver.web.websocket.SnapshotUpdateWebsocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	private static final String UPDATE_SNAPSHOTS_PATH = "/snapshots";
	private static final String UPDATE_METEO_DATA_PATH = "/meteo";
	
	private SnapshotUpdateWebsocketHandler snapshotWebsocketHandler;
	private MeteoDataUpdateWebsocketHandler meteoDataWebsocketHandler;
	
	@Autowired
	public WebSocketConfig(
			SnapshotUpdateWebsocketHandler snapshotWebsocketHandler, 
			MeteoDataUpdateWebsocketHandler meteoDataWebsocketHandler) {
		this.snapshotWebsocketHandler = snapshotWebsocketHandler;
		this.meteoDataWebsocketHandler = meteoDataWebsocketHandler;
	}
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(snapshotWebsocketHandler, UPDATE_SNAPSHOTS_PATH);
		registry.addHandler(meteoDataWebsocketHandler, UPDATE_METEO_DATA_PATH);
	}

}
