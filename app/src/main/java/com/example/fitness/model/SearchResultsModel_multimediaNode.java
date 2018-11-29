package com.example.fitness.model;

import java.io.Serializable;

/**
 *
 *    ---------  Copyright 2018, Oke Uwechue, All rights reserved. ---------
 */
public class SearchResultsModel_multimediaNode implements Serializable {
    private static final long serialVersionUID = 4050282614337029151L;

    private String type, url;   // don't bother parsing the remaining nodes. not used in this project.

    public SearchResultsModel_multimediaNode(){}

    public SearchResultsModel_multimediaNode(String type, String url) {
        this.type = type;
        this.url = url;
    }

    // GETTERS
    public String getType() { return type; }
    public String getUrl() { return url; }

    // SETTERS
    public void setType(String type) { this.type = type; }
    public void setUrl(String url) { this.url = url; }

}
