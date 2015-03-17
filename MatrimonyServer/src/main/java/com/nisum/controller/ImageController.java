package com.nisum.controller;

import javax.print.DocFlavor.BYTE_ARRAY;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.nisum.service.ImageService;

@Controller
public class ImageController {

	@Autowired
	private ImageService service;

	@RequestMapping(value = "/getimage", method = RequestMethod.GET, produces = "image/jpg")
	public ResponseEntity<byte[]> getLocalImage(@RequestParam("imageName") String imageName)  {
		byte[] byteResponse = service.getFile(imageName);

		if(byteResponse != null)
		{
			return new ResponseEntity<byte[]>(byteResponse, HttpStatus.OK);
		}
		else
			return new ResponseEntity<byte[]>(HttpStatus.NOT_ACCEPTABLE);
	}
	
	 

	@RequestMapping(value = "/storeimage", method = RequestMethod.POST, headers={"content-type=multipart/form-data"})
	public ResponseEntity<String> storeImage(@RequestParam("fieldName") CommonsMultipartFile  file )
	{	
		service.saveFile(file);
		return new ResponseEntity<String>("Succesfully Stored the image", HttpStatus.OK);
	}
	 
}
