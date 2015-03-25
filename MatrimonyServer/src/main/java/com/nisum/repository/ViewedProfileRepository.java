package com.nisum.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nisum.domain.Profile;
import com.nisum.domain.ViewedProfile;

@Repository
public interface ViewedProfileRepository extends CrudRepository<ViewedProfile, String> {
	
	List<ViewedProfile> findByProfileId(String profileId);
	
}
