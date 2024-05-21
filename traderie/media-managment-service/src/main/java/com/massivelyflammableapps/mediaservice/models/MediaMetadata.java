package com.massivelyflammableapps.mediaservice.models;
import org.ektorp.support.CouchDbDocument;
import lombok.*;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaMetadata extends CouchDbDocument {

    // Getters and Setters

    private String id;

    private String fileName;

    private long offset;

    private String key;

    private String alternateKey;

    private String cookie;


}
