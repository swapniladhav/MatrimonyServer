package com.nisum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.nisum.domain.Profile;
import com.nisum.repository.ProfileRepository;
@Service
public class  ProfileService {
	@Autowired
	private ProfileRepository profileRepository;
	public boolean createProfile( Profile  profile) {
		 @SuppressWarnings("unused")
		Profile savedInstance = profileRepository.save(profile);
		return savedInstance==null?false:true;
	}
	public List<Profile> loadProfile( Integer  profile_id) {
		 
		 return profileRepository.findByProfileId(profile_id);
	}
	

}
