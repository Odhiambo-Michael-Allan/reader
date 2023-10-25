package org.reader.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
public class Article {

    private String url;
    private String baseUri;
    private String guid;
    private String title;
    private String creator;
    private String description;
    private String thumbnailUrl;
    private ZonedDateTime publicationDate;
    private ZonedDateTime createdDate;
    private ZonedDateTime deleteDate;

}
