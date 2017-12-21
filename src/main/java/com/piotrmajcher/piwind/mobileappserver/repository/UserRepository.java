package com.piotrmajcher.piwind.mobileappserver.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.piotrmajcher.piwind.mobileappserver.domain.UserEntity;
import com.piotrmajcher.piwind.mobileappserver.domain.VerificationToken;

public interface UserRepository extends CrudRepository<UserEntity, UUID>{
	
	UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
    
    UserEntity findByToken(VerificationToken token);
    
    @Query("SELECT u FROM UserEntity u WHERE u.enabled = FALSE")
	List<UserEntity> findAllUnconfirmed();
}
