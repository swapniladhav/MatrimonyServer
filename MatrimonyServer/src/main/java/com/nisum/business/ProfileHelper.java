package com.nisum.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nisum.domain.Profile;
import com.nisum.repository.CreateProfileRepository;
@Component
public class ProfileHelper {
	@Autowired
	private CreateProfileRepository profileRepository;
	List<Profile> list = new ArrayList<Profile>();

	public boolean checkRegisteredProfile(Profile profile) {
		boolean registered = false;
		list = profileRepository.findByProfileId(profile.getProfileId());
		if (list.size() > 0)
			registered= true;
			return registered;
	}
}
