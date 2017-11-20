package com.piotrmajcher.piwind.mobileappserver.repository;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.piotrmajcher.piwind.mobileappserver.domain.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, UUID>{
	
	UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.enabled = true WHERE u.id = :id")
    void setUserEnabled(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.password = :password WHERE u.id = :id")
    void setNewPassword(@Param("password") String password, @Param("id") UUID id);
}
