package com.piotrmajcher.piwind.mobileappserver.util;

import java.util.List;

public interface EntityAndTOConverter<E, T> {
	
	T entityToTransferObject(E entity);
	E transferObjectToEntity(T to);
	
	List<T> entityToTransferObject(List<E> entityList);
	List<E> transferObjectToEntity(List<T> toList);
}
