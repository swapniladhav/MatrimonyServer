package com.nisum.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.business.MatchProfileHelper;
import com.nisum.domain.ViewedProfile;
import com.nisum.repository.ViewedProfileRepository;

@Service
public class ViewedProfileService {
	private static Logger logger = Logger.getLogger(ViewedProfileService.class);
	@Autowired
	private ViewedProfileRepository repository;
	@Autowired
	private MatchProfileHelper matchProfileHelper;

	public void saveViewedProfile(ViewedProfile profile) {
	 matchProfileHelper.saveOrUpdatekMatchProfileLogs(profile);
	}
	public List<ViewedProfile> getViewedProfile(String profileId) {
		return repository.findByProfileId(profileId);
	}
}
