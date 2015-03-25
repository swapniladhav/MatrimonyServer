package com.nisum.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nisum.constants.URLConstants;
import com.nisum.domain.ViewedProfile;
import com.nisum.service.ViewedProfileService;

@Controller
public class MatchProfileController {
	private static Logger log = Logger.getLogger(MatchProfileController.class
			.getName());
	@Autowired
	ViewedProfileService viewProfileService;

	
	@RequestMapping(value = URLConstants.LOAD_LIKE_UNLIKE_PROFILE, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> savePreference(
			@RequestBody ViewedProfile viewed) {
		log.info("save or update like unlike profile started:"+viewed.getProfileId());

		 viewProfileService.saveViewedProfile(viewed);
		log.info("task completed");
		return new ResponseEntity<String>("Successfully saved", HttpStatus.OK);
	}

}
