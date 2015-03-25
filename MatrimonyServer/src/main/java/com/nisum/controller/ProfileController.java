package com.nisum.controller;

import java.util.List;

 

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nisum.constants.URLConstants;
import com.nisum.domain.Profile;
import com.nisum.domain.NewMatchProfile;
import com.nisum.service.CreateProfileService;

@Controller
public class ProfileController {
	 static Logger log = Logger.getLogger(ProfileController.class.getName());
	@Autowired
	private CreateProfileService service;

	@RequestMapping(value = URLConstants.CREATE_OR_LOAD_PROFILE, method = RequestMethod.POST)
	public @ResponseBody Profile createProfile(@RequestBody Profile profile) {
		log.info("Profile Id:" + profile.getProfileId());
		 
		Profile instance = service.createProfile(profile,
				profile.getProfilePics());
		log.info ("loading finished");
		return instance;
	}

	@RequestMapping(value = URLConstants.LOAD_PROFILE, method = RequestMethod.GET)
	public @ResponseBody   ResponseEntity<List<NewMatchProfile>> loadProfile(
			@RequestParam("profile_id") String profile_id) {
		log.info("Loading Profile Id:" + profile_id);
		return new ResponseEntity<List<NewMatchProfile>>(service.loadRecommendedProfiles(profile_id), HttpStatus.OK);
	}
}
