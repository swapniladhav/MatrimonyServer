package com.nisum.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.nisum.config.MongoConfig;
import com.nisum.domain.ViewedProfile;
import com.nisum.repository.ViewedProfileRepository;

@Component
public class MatchProfileHelper {
	@Autowired
	ViewedProfileRepository viewedProfileRepository;
	@Autowired
	private MongoConfig mongoConfig;

	public void saveOrUpdatekMatchProfileLogs(ViewedProfile viewedProfile) {
		List<ViewedProfile> list = viewedProfileRepository
				.findByProfileId(viewedProfile.getProfileId());
		if (list.size() > 0) {
			try {
				MongoOperations mongop = mongoConfig.mongoTemplate();

				DBCollection collection = mongop.getCollection("ViewedProfile");
				BasicDBObject newDocument = new BasicDBObject();
				newDocument.put("likeProfile", viewedProfile.getLikeProfile());
				newDocument.put("unlikeProfile",
						viewedProfile.getUnlikeProfile());

				DBObject updateQuery = new BasicDBObject("$push", newDocument);
				collection.update(newDocument, updateQuery);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			viewedProfile = viewedProfileRepository.save(viewedProfile);
		}

	}
}
