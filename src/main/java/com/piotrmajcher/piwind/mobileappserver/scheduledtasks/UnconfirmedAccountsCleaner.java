package com.piotrmajcher.piwind.mobileappserver.scheduledtasks;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.piotrmajcher.piwind.mobileappserver.domain.UserEntity;
import com.piotrmajcher.piwind.mobileappserver.repository.UserRepository;

@Component
public class UnconfirmedAccountsCleaner {
	
	private static final long CLEANUP_INTERVAL_MINUTES = 1000 * 60 * 1; // 5 minutes 
	private UserRepository userRepository;
	
	@Autowired
	public UnconfirmedAccountsCleaner(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Scheduled(fixedRate = CLEANUP_INTERVAL_MINUTES)
	private void removeUnconfirmedAccounts() {
		Date now = new Date();
		List<UserEntity> users = userRepository.findAllUnconfirmed();
		users = users.stream()
				.filter(u -> u.getToken().getExpiryDate().before(now))
				.collect(Collectors.toList());
		userRepository.delete(users);
	}
}
