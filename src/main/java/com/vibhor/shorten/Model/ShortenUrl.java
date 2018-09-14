package com.vibhor.shorten.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShortenUrl {

    private String url;
    private String hits;
    private String converted;

    @JsonCreator
    public ShortenUrl() {

    }

    @JsonCreator
    public ShortenUrl(@JsonProperty("url") String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String longUrl) {
        this.url = url;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getConverted() {
        return converted;
    }

    public void setConverted(String converted) {
        this.converted = converted;
    }
}
