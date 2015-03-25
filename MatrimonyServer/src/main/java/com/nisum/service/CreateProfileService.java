package com.nisum.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.business.ProfileHelper;
import com.nisum.domain.NewMatchProfile;
import com.nisum.domain.Profile;
import com.nisum.repository.CreateProfileRepository;

@Service
public class CreateProfileService {
	 static Logger logger = Logger.getLogger(CreateProfileService.class.getName());
	@Autowired
	private CreateProfileRepository profileRepository;
	@Autowired
	private ProfileHelper genericProfileAdapter;
	@Autowired
	AlbumService albumService;
	private Profile instance;

	public Profile createProfile(Profile profile, List<String> albumList) {

		if (genericProfileAdapter.checkRegisteredProfile(profile)) {
			logger.info("Profile Id:" + profile.getProfileId()+ "allready created");
			instance = loadProfile(profile.getProfileId()).get(0);
			
		} else {
			logger.info("saving profile");
			profile.setProfilePics(null);
			instance = profileRepository.save(profile);
			logger.info("stroring album");
			albumService.storeAlbum(albumList, profile.getProfileId());
		}

		return instance;
	}

	public List<Profile> loadProfile(String profile_id) {

		return profileRepository.findByProfileId(profile_id);
	}
	public List<NewMatchProfile> loadRecommendedProfiles(String profile_id) {
		logger.info("loading profile");
		return albumService.loadRecommendedProfiles(profile_id);
	}

}
