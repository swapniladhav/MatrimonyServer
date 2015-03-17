package com.nisum.controller;

import java.util.List;

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
import com.nisum.service.ProfileService;

@Controller
public class ProfileController {
	@Autowired
	ProfileService service;

	@RequestMapping(value =  URLConstants.CREATE_PROFILE, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> createProfile(
			@RequestBody Profile profile) {

		boolean flag= service.createProfile(profile);
		return new ResponseEntity<String>("Succesfully Profile Created",
				HttpStatus.OK);
	}

	 @RequestMapping(value =URLConstants.LOAD_PROFILE, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Profile>> storeImage(@RequestParam("profile_id") Integer profile_id)
	{
		return new ResponseEntity<List<Profile>>(service.loadProfile(profile_id) , HttpStatus.OK);
	} 
	}
