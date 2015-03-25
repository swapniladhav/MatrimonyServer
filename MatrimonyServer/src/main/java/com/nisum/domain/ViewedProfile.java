package com.nisum.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
@Document(collection="ViewedProfile")
public class ViewedProfile {
	String profileId;
	List<String> likeProfile= new ArrayList<String>();
	List<String> unlikeProfile= new ArrayList<String>();
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public List<String> getLikeProfile() {
		return likeProfile;
	}
	public void setLikeProfile(List<String> likeProfile) {
		this.likeProfile = likeProfile;
	}
	public List<String> getUnlikeProfile() {
		return unlikeProfile;
	}
	public void setUnlikeProfile(List<String> unlikeProfile) {
		this.unlikeProfile = unlikeProfile;
	}
	
}
