package com.nisum.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.nisum.config.MongoConfig;

@Component
public class ImageService {

	@Autowired
	private MongoConfig mongoConfig;

	public byte[] getFile(String imageName)
	{
		try {
			GridFsOperations gridOperations = mongoConfig.gridFsTemplate();

			imageName = imageName.concat(".jpg");

			List<GridFSDBFile> files = gridOperations.find(new Query().addCriteria(Criteria.where(
					"filename").is(imageName)));
			for (GridFSDBFile file : files) {
				//System.out.println(file.getFilename());
				//System.out.println(file.getContentType());
				
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
	 
	public void saveFile(CommonsMultipartFile file)
	{
		InputStream inputStream = null;

		DBObject metaData = new BasicDBObject();
		try {
			GridFsOperations gridOperations = mongoConfig.gridFsTemplate();

			//Metadata - data about the file
			metaData.put("Name",  "Aashish");

			gridOperations.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metaData);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	 
	public byte[] get(String attributeMetaData)
	{
		try {
			GridFsOperations gridOperations = mongoConfig.gridFsTemplate();

			GridFSDBFile file = gridOperations.findOne(new Query().addCriteria(Criteria.where(
					"metadata.Name").is(attributeMetaData)));
			if(file != null) {
				
				// Prepare buffered image.
				BufferedImage img = ImageIO.read(file.getInputStream());
				
				DBObject db = file.getMetaData();
				Map<String, String> key = db.toMap();
				Iterator<Entry<String, String>> it= key.entrySet().iterator();
				if(it.hasNext()) {
					Entry<String, String> entry = it.next();
					String strKey = entry.getKey();
					String strValue = entry.getValue();
					System.out.println(strKey + " " + strValue);
				}

				// Create a byte array output stream.
				ByteArrayOutputStream bao = new ByteArrayOutputStream();
				
				String [] strArray = file.getContentType().split("/");

				// Write to output stream
				ImageIO.write(img, strArray[1], bao);

				return bao.toByteArray();

				//save as another image
				//file.writeTo("/Users/ashivhare/Downloads/new-testing.jpg");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
