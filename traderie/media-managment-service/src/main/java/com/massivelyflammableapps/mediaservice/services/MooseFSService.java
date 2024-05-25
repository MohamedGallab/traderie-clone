package com.massivelyflammableapps.mediaservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class MooseFSService {


    @Autowired
    private RestTemplate restTemplate;



    public void uploadFile(FileSystemResource file) {
        String uploadUrl = "http://master:9421" ;

        // Set headers
        System.out.println("cc");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Set the file as part of the request body
        System.out.println("cc");
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file);
        System.out.println("cc");

        // Create the request entity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Send the POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                uploadUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        System.out.println(responseEntity);

        // Check the response status and handle accordingly
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("File uploaded successfully to MooseFS.");
        } else {
            System.out.println("Failed to upload file to MooseFS. Status code: " + responseEntity.getStatusCodeValue());
            // Handle error scenario
        }
    }


}