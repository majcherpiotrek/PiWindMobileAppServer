package com.piotrmajcher.piwind.mobileappserver.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.piotrmajcher.piwind.mobileappserver.domain.UserEntity;
import com.piotrmajcher.piwind.mobileappserver.domain.VerificationToken;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long>{

    VerificationToken findByToken(String token);
    List<VerificationToken> findAll();
}
