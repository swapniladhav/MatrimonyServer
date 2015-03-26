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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.nisum.constants.URLConstants;
import com.nisum.domain.NewMatchProfile;
import com.nisum.service.AlbumService;

@Controller
public class AlbumController {

	@Autowired
	private AlbumService service;

	@RequestMapping(value = URLConstants.LOAD_ALBUM, method = RequestMethod.GET, produces = "image/jpg")
	public List<NewMatchProfile> loadAlbum(@RequestParam String profileId) {
		return service.loadAlbum(profileId);
	}

	@RequestMapping(value = URLConstants.GET_ALBUM, method = RequestMethod.POST, headers = { "content-type=multipart/form-data" })
	public ResponseEntity<String> storeImage(
			@RequestBody CommonsMultipartFile[] file) {
		// service.saveFile(file);
		return new ResponseEntity<String>("Succesfully Stored the image",
				HttpStatus.OK);
	}

}
