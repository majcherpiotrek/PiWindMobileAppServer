package com.piotrmajcher.piwind.mobileappserver.web.websocket;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.piotrmajcher.piwind.mobileappserver.events.listeners.SnapshotUpdateApplicationListener;
import com.piotrmajcher.piwind.mobileappserver.events.listeners.SnapshotUpdatePublishEventListener;

@Component
public class SnapshotUpdateWebsocketHandler extends TextWebSocketHandler {
	
	private static final Logger logger = Logger.getLogger(SnapshotUpdateWebsocketHandler.class);
	private static final int BINARY_MESSAGE_SIZE_LIMIT = 1024 * 1024;
	private static final CharSequence CONNECTED_MESSAGE = "Snapshot update connection established.";
	
	List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
	Map<WebSocketSession, SnapshotUpdatePublishEventListener> sessionsListeners = new HashMap<>();
	
	private SnapshotUpdateApplicationListener snapshotUpdateListener;
	
	@Autowired
	public SnapshotUpdateWebsocketHandler(SnapshotUpdateApplicationListener snapshotUpdateListener) {
		this.snapshotUpdateListener = snapshotUpdateListener;
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {
		UUID stationId = UUID.fromString(message.getPayload().trim());
		logger.info("Station snapshot update request received. Station id:" + stationId);
		session.setBinaryMessageSizeLimit(BINARY_MESSAGE_SIZE_LIMIT);
		
		SnapshotUpdatePublishEventListener listener = createSnapshotUpdateListenerForSession(session, stationId);
		snapshotUpdateListener.addSnapshotUpdatePublishEventListener(listener);
		session.sendMessage(new TextMessage(CONNECTED_MESSAGE));
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
	}
	
	private SnapshotUpdatePublishEventListener createSnapshotUpdateListenerForSession(WebSocketSession session,
			UUID stationId) {
		return new SnapshotUpdatePublishEventListener() {
			
			@Override
			public void onSnapshotUpdatePublishedEvent(byte[] snapshot) {
				
					if (session.isOpen()) {
						try {
							session.sendMessage(new BinaryMessage(snapshot));
							logger.info("Snapshot update sent!");
						} catch (IOException e) {
							logger.error("Failed to send snapshot update: " + e.getMessage());
						}
					} else {
						logger.info("Session closed. Cleaning up ...");
						snapshotUpdateListener.removeSnapshotUpdatePublishEventListener(this);
						sessionsListeners.remove(session);
						sessions.remove(session);
					}					
			}
			
			@Override
			public UUID getListeningStationId() {
				return stationId;
			}
		};
	}
}
