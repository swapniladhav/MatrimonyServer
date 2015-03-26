package com.nisum.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.nisum.config.MongoConfig;
import com.nisum.domain.NewMatchProfile;
import com.nisum.domain.Profile;
import com.nisum.domain.ViewedProfile;

@Component
public class AlbumService {

	@Autowired
	private MongoConfig mongoConfig;
	@Autowired
	private ViewedProfileService viewedProfileService;
	@Autowired
	public RestTemplate restTemplate;
	@Autowired
	NewMatchProfile newMatchProfile;
 
	public byte[] getFile(String imageName) {
		try {
			GridFsOperations gridOperations = mongoConfig.gridFsTemplate();

			imageName = imageName.concat(".jpg");

			List<GridFSDBFile> files = gridOperations.find(new Query()
					.addCriteria(Criteria.where("filename").is(imageName)));
			for (GridFSDBFile file : files) {
				// System.out.println(file.getFilename());
				// System.out.println(file.getContentType());

				// Prepare buffered image.
				BufferedImage img = ImageIO.read(file.getInputStream());

				// Create a byte array output stream.
				ByteArrayOutputStream bao = new ByteArrayOutputStream();

				// Write to output stream
				ImageIO.write(img, "jpg", bao);

				return bao.toByteArray();

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void saveFile(CommonsMultipartFile file) {
		InputStream inputStream = null;

		DBObject metaData = new BasicDBObject();
		try {
			GridFsOperations gridOperations = mongoConfig.gridFsTemplate();

			// Metadata - data about the file
			metaData.put("Name", "Aashish");

			gridOperations
					.store(file.getInputStream(), file.getOriginalFilename(),
							file.getContentType(), metaData);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void storeAlbum(List<String> albumList, String profile_id) {
		InputStream inputStream = null;
		byte[] photo;
		try {
			for (String url : albumList) {
				System.out.println(url);
				photo = restTemplate.getForObject(url, byte[].class);
				ByteArrayInputStream bis = new ByteArrayInputStream(photo);

				DBObject metaData = new BasicDBObject();

				GridFsOperations gridOperations = mongoConfig.gridFsTemplate();

				// Metadata - data about the file
				metaData.put("profile_id", profile_id);

				gridOperations.store(bis, "", "image/jpg", metaData);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public List<NewMatchProfile> loadRecommendedProfiles(String profile_id) {
		List<NewMatchProfile> multiMap = new ArrayList<NewMatchProfile>();
		List<byte[]> byteList = new ArrayList<byte[]>();
		List<ViewedProfile> profiles = viewedProfileService
				.getViewedProfile(profile_id);

		try {
			MongoOperations mongoOps = mongoConfig.mongoTemplate();
			Query serachCriteria = null;
			if (profiles.size() > 0) {
				serachCriteria = new Query()
						.addCriteria(
								Criteria.where("profileId").not()
										.in(profile_id))
						.addCriteria(
								Criteria.where("profileId").not()
										.in(profiles.get(0).getLikeProfile()))
						.addCriteria(
								Criteria.where("profileId").not()
										.in(profiles.get(0).getUnlikeProfile()));
			} else {
				serachCriteria = new Query().addCriteria(Criteria
						.where("profileId").not().in(profile_id));
			}

			List<Profile> list = mongoOps.find(serachCriteria, Profile.class);

			GridFsOperations gridOperations = mongoConfig.gridFsTemplate();

			for (Profile currObj : list) {
				GridFSDBFile gridFSDBFile = gridOperations.findOne(new Query()
						.addCriteria(Criteria.where("metadata.profile_id").is(
								currObj.getProfileId())));
	 
				// Prepare buffered image.
				BufferedImage img = ImageIO.read((gridFSDBFile)
						.getInputStream());

				/*
				 * DBObject db = (gridFSDBFile).getMetaData(); Map<String,
				 * String> key = db.toMap(); Iterator<Entry<String, String>> it
				 * = key.entrySet().iterator(); if (it.hasNext()) {
				 * Entry<String, String> entry = it.next(); String strKey =
				 * entry.getKey(); String strValue = entry.getValue();
				 * System.out.println(strKey + " " + strValue); }
				 */

				// Create a byte array output stream.
				ByteArrayOutputStream bao = new ByteArrayOutputStream();

				String[] strArray = (gridFSDBFile).getContentType().split("/");

				// Write to output stream
				ImageIO.write(img, strArray[1], bao);

				byteList.add(bao.toByteArray());
				List<byte[]> albumresponse = new ArrayList<byte[]>();
				albumresponse.add(bao.toByteArray());
				newMatchProfile.setAlbum(albumresponse);
				newMatchProfile.setProfile(currObj);

				multiMap.add(newMatchProfile);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return multiMap;

	}
	public List<NewMatchProfile> loadAlbum(String profileId) {
		List<NewMatchProfile> multiMap = new ArrayList<NewMatchProfile>();
		List<byte[]> byteList = new ArrayList<byte[]>();
	 

		try {
			MongoOperations mongoOps = mongoConfig.mongoTemplate();
			Query serachCriteria = null;
 
				serachCriteria = new Query().addCriteria(Criteria
						.where("profileId").is(profileId));
		 

			Profile obj = mongoOps.findOne(serachCriteria, Profile.class);

			GridFsOperations gridOperations = mongoConfig.gridFsTemplate();
 
				List<GridFSDBFile> gridFSDBFile = gridOperations.find(new Query()
						.addCriteria(Criteria.where("metadata.profile_id").is(
								obj.getProfileId())));
			 for (GridFSDBFile gridFS  : gridFSDBFile) {
				// Prepare buffered image.
					BufferedImage img = ImageIO.read((gridFS)
							.getInputStream());

					/*
					 * DBObject db = (gridFSDBFile).getMetaData(); Map<String,
					 * String> key = db.toMap(); Iterator<Entry<String, String>> it
					 * = key.entrySet().iterator(); if (it.hasNext()) {
					 * Entry<String, String> entry = it.next(); String strKey =
					 * entry.getKey(); String strValue = entry.getValue();
					 * System.out.println(strKey + " " + strValue); }
					 */

					// Create a byte array output stream.
					ByteArrayOutputStream bao = new ByteArrayOutputStream();

					String[] strArray = (gridFS).getContentType().split("/");

					// Write to output stream
					ImageIO.write(img, strArray[1], bao);

					byteList.add(bao.toByteArray());
			}
	
			 newMatchProfile.setAlbum(byteList);
			 newMatchProfile.setProfile(obj);

			 multiMap.add(newMatchProfile);
			 

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return multiMap;

	}
}
