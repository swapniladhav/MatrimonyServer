package com.nisum.domain;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

 
@JsonIgnoreProperties(ignoreUnknown=true)
@Document(collection="Profile")
public class Profile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String profileId;
	String fullName;
	String cast;
	String city;
	String religion;
	Integer age;
	String birthDate;
	List<String> profilePics;
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getCast() {
		return cast;
	}
	public void setCast(String cast) {
		this.cast = cast;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public List<String> getProfilePics() {
		return profilePics;
	}
	public void setProfilePics(List<String> profilePics) {
		this.profilePics = profilePics;
	}
	 
	 
}
