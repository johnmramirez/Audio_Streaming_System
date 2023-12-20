package com.practice.audio_system.services;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.practice.audio_system.dto.Audio;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AudioService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations operations;

    public String putAudio(String title, MultipartFile file) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put("title", title);
        ObjectId id = gridFsTemplate.store(
                file.getInputStream(), file.getName(), file.getContentType(), metaData);
        return id.toString();
    }

    public Audio getAudio(String id) throws IllegalStateException, IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        Audio audio = null;
        if(file != null){
            audio = new Audio();
            audio.setTitle(file.getMetadata().get("title").toString());
            audio.setStream(operations.getResource(file).getInputStream());
        }
        return audio;
    }
}