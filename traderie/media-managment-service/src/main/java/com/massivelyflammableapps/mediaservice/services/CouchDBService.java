package com.massivelyflammableapps.mediaservice.services;

import com.massivelyflammableapps.mediaservice.configuration.CouchDBConfig;
import com.massivelyflammableapps.mediaservice.models.MediaMetadata;
import jakarta.annotation.PostConstruct;
import org.ektorp.*;
import org.ektorp.http.*;
import org.ektorp.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


import java.net.MalformedURLException;
import java.util.UUID;

@Service
public class CouchDBService {

    @Autowired
    private CouchDBConfig couchDBConfig;

    private CouchDbConnector couchDbConnector;

    @PostConstruct
    public void init() throws MalformedURLException {

        HttpClient httpClient =  new StdHttpClient.Builder()
                .url(couchDBConfig.getUrl())
                .username("admin")
                .password("admin_password")
                .build();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        this.couchDbConnector = new StdCouchDbConnector(couchDBConfig.getDatabase(), dbInstance);
        this.couchDbConnector.createDatabaseIfNotExists();
    }

    public String saveMetadata(String fileName, String fileId) {
        MediaMetadata metadata = new MediaMetadata();
        metadata.setId(fileId);
        metadata.setFileName(fileName);

        couchDbConnector.create(metadata);
        return fileId;
    }

    public MediaMetadata getMetadata(String fileId) {
        return couchDbConnector.get(MediaMetadata.class, fileId);
    }

    public void deleteMetadata(String fileId) {
        MediaMetadata metadata = couchDbConnector.get(MediaMetadata.class, fileId);
        couchDbConnector.delete(metadata);
    }
}
