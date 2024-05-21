package com.massivelyflammableapps.mediaservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "couchdb")
public class CouchDBConfig {

    private String url;
    private String database;
    private String username;
    private String password;

}

