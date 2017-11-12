package com.piotrmajcher.piwind.mobileappserver.events;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

public class OnSnapshotUpdateReceivedEvent extends ApplicationEvent {
	
	private UUID stationId;
	private byte[] snapshot;
	
	public OnSnapshotUpdateReceivedEvent(Object source, UUID stationId, byte[] snapshot) {
		super(source);
		this.snapshot = snapshot;
		this.stationId = stationId;
	}

	public byte[] getSnapshot() {
		return snapshot;
	}
	
	public UUID getStationId() {
		return stationId;
	}
}
