package com.nisum.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nisum.domain.Profile;
import java.lang.Integer;

@Repository
public interface CreateProfileRepository extends CrudRepository<Profile, String> {
	
	List<Profile> findByProfileId(String profileId);
	
}
