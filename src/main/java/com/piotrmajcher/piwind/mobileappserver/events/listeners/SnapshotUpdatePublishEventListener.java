package com.piotrmajcher.piwind.mobileappserver.events.listeners;

import java.util.EventListener;
import java.util.UUID;

public interface SnapshotUpdatePublishEventListener extends EventListener {
	void onSnapshotUpdatePublishedEvent(byte[] snapshot);
	UUID getListeningStationId();
}
