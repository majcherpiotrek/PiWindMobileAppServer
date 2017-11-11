package com.piotrmajcher.piwind.mobileappserver.util.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.util.Assert;

import com.piotrmajcher.piwind.mobileappserver.domain.MeteoStation;
import com.piotrmajcher.piwind.mobileappserver.util.EntityAndTOConverter;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoStationTO;

public class MeteoStationEntityConverter implements EntityAndTOConverter<MeteoStation, MeteoStationTO>{

	@Override
	public MeteoStationTO entityToTransferObject(MeteoStation entity) {
		MeteoStationTO to = null;
		if (entity != null) {
			to = new MeteoStationTO();
			to.setName(entity.getName());
			to.setStationBaseURL(entity.getStationBaseURL());
			to.setId(entity.getId());
		}
		return to;
	}

	@Override
	public MeteoStation transferObjectToEntity(MeteoStationTO to) {
		MeteoStation entity = null;
		if (to != null) {
			entity = new MeteoStation();
			entity.setName(to.getName());
			entity.setStationBaseURL(to.getStationBaseURL());
			entity.setBeachFacingDirection(to.getBeachFacingDirection());
			entity.setBestWindDirections(to.getBestWindDirections());
			entity.setDescription(to.getDescription());
		}
		return entity;
	}

	@Override
	public List<MeteoStationTO> entityToTransferObject(List<MeteoStation> entityList) {
		List<MeteoStationTO> toList = null;
		if (entityList != null) {
			toList = new LinkedList<>();
			for (MeteoStation entity : entityList) {
				toList.add(entityToTransferObject(entity));
			}
		}
		return toList;
	}

	@Override
	public List<MeteoStation> transferObjectToEntity(List<MeteoStationTO> toList) {
		List<MeteoStation> entityList = null;
		if (toList != null) {
			entityList = new LinkedList<>();
			for (MeteoStationTO to : toList) {
				entityList.add(transferObjectToEntity(to));
			}
		}
		return entityList;
	}

}
