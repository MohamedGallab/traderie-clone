package com.massivelyflammableapps.webserver.controllers;

import com.massivelyflammableapps.shared.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ExternalServiceClient {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public UserDto getDataFromExternalService(String param) {
        String url = "http://localhost:8081/api/v1/user/getUser";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("username", param);

        return restTemplate.getForObject(uriBuilder.toUriString(), UserDto.class);
    }
}
