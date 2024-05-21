package com.massivelyflammableapps.mediaservice.controller;

import com.massivelyflammableapps.mediaservice.models.MediaMetadata;
import com.massivelyflammableapps.mediaservice.services.CouchDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private CouchDBService couchDBService;

    @PostMapping("/insert")
    public ResponseEntity<String> insertData(@RequestBody MediaMetadata metadata) {
        try {
            // Save the user document into Couchbase

            couchDBService.saveMetadata(metadata.getFileName(),metadata.getId());
            return ResponseEntity.ok("Data inserted successfully into Couchbase");
        } catch (Exception e) {
            // If an exception occurs, return an error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to insert data into Couchbase");
        }
    }
}
