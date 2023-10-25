package org.reader.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Feed {

    private final List<Article> articles = new ArrayList<>();
    private String rssUrl;
    private String websiteUrl;
    private String baseUri;
    private String title;
    private String language;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime lastFetchDate;
    private LocalDateTime deleteDate;

    public void addArticle( Article article ) {
        this.articles.add( article );
    }

}
