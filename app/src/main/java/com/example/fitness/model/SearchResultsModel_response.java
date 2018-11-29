package com.example.fitness.model;

/**
 *
 *    ---------  Copyright 2018, Oke Uwechue, All rights reserved. ---------
 */
public class SearchResultsModel_response {

    private String status, copyright;
    private SearchResultsModel_parentNode response;

    public SearchResultsModel_response(){}

    public SearchResultsModel_response(String status, String copyright, SearchResultsModel_parentNode response) {
        this.status = status;
        this.copyright = copyright;
        this.response = response;
    }

    // GETTERS
    public String getStatus() { return status; }
    public String getCopyright() { return copyright; }
    public SearchResultsModel_parentNode getResponse() { return response; }


    // SETTERS
    public void setStatus(String status) { this.status = status; }
    public void setCopyright(String copyright) { this.copyright = copyright; }
    public void setResponse(SearchResultsModel_parentNode response) { this.response = response; }
}
