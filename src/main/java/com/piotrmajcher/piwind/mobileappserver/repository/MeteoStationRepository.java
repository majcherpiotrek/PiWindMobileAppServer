package com.piotrmajcher.piwind.mobileappserver.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.piotrmajcher.piwind.mobileappserver.domain.MeteoStation;

public interface MeteoStationRepository extends CrudRepository<MeteoStation, UUID> {
	
	List<MeteoStation> findAll();
	
	MeteoStation findByName(String name);
	
	MeteoStation findById(UUID id);
}
