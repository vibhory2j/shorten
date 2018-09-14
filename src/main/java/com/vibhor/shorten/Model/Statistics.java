package com.vibhor.shorten.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.lang.reflect.Array;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Statistics {

    public ArrayList<ShortenUrl> shortenUrls;

    public ArrayList<ShortenUrl> getShortenUrls() {
        return shortenUrls;
    }

    public void setShortenUrls(ArrayList<ShortenUrl> shortenUrls) {
        this.shortenUrls = shortenUrls;
    }
}
