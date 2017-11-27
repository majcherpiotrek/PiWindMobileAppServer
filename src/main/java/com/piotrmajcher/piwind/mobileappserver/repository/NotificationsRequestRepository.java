package com.piotrmajcher.piwind.mobileappserver.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.piotrmajcher.piwind.mobileappserver.domain.NotificationsRequest;

public interface NotificationsRequestRepository extends CrudRepository<NotificationsRequest, Long>{

	List<NotificationsRequest> findAll();
	
	List<NotificationsRequest> findByMeteoStationId(UUID meteoStationId);
	
	NotificationsRequest findByMeteoStationIdAndUserId(UUID meteoStationId, UUID userId);
}
