package com.nisum;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class SaveImage {
	
	/*@RequestMapping(value = "/localimage", method = RequestMethod.GET, produces = "image/jpg")
	public @ResponseBody byte[] getLocalImage()  {
	    try {
	        // Retrieve image from the classpath.
	        InputStream is = this.getClass().getResourceAsStream("/test.jpg"); 

	        // Prepare buffered image.
	        BufferedImage img = ImageIO.read(is);

	        // Create a byte array output stream.
	        ByteArrayOutputStream bao = new ByteArrayOutputStream();

	        // Write to output stream
	        ImageIO.write(img, "jpg", bao);

	        return bao.toByteArray();
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}*/

	public static void main(String[] args) {
		 
		try {
 
			@SuppressWarnings("deprecation")
			Mongo mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("imagedb");
//			DBCollection collection = db.getCollection("dummyColl");
 
			String newFileName = "mkyong-java-image";
 
			File imageFile = new File("D:\\test.jpg");
 
			// create a "photo" namespace
			GridFS gfsPhoto = new GridFS(db, "photo");
 
			// get image file from local drive
			GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
 
			// set a new filename for identify purpose
			gfsFile.setFilename(newFileName);
 
			// save the image file into mongoDB
			gfsFile.save();
 
			// print the result
			DBCursor cursor = gfsPhoto.getFileList();
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
 
			// get image file by it's filename
			GridFSDBFile imageForOutput = gfsPhoto.findOne(newFileName);
 
			// save it into a new image file
			imageForOutput.writeTo("D:\\JavaWebHostingNew.jpg");
 
			// remove the image file from mongoDB
//			gfsPhoto.remove(gfsPhoto.findOne(newFileName));
 
			System.out.println("Done");
 
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
 
	}
}
