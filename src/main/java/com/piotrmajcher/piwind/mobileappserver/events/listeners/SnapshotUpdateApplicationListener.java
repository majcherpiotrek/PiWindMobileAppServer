package com.piotrmajcher.piwind.mobileappserver.events.listeners;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.piotrmajcher.piwind.mobileappserver.events.OnSnapshotUpdateReceivedEvent;

@Component
public class SnapshotUpdateApplicationListener implements ApplicationListener<OnSnapshotUpdateReceivedEvent> {
	private static final Logger logger = Logger.getLogger(SnapshotUpdateApplicationListener.class);
	private List<SnapshotUpdatePublishEventListener> listeners;
	
	@Autowired
	public SnapshotUpdateApplicationListener() {
		this.listeners = new LinkedList<>();
	}
	
	@Override
	public void onApplicationEvent(OnSnapshotUpdateReceivedEvent event) {
		for (SnapshotUpdatePublishEventListener listener : listeners) {
			if (event.getStationId().equals(listener.getListeningStationId())) {
				listener.onSnapshotUpdatePublishedEvent(event.getSnapshot());
			}
		}
	}
	
	public void addSnapshotUpdatePublishEventListener(SnapshotUpdatePublishEventListener listener) {
			this.listeners.add(listener);
	}
	
	public void removeSnapshotUpdatePublishEventListener(SnapshotUpdatePublishEventListener listener) {
		this.listeners.remove(listener);
	}
}