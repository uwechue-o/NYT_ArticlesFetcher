package com.example.fitness.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 *
 *    ---------  Copyright 2018, Oke Uwechue, All rights reserved. ---------
 */
public class SearchResultsModel_bylineNode implements Serializable {
    private static final long serialVersionUID = 4010282614337029151L;

    private String original, organization;
    private List<SearchResultsModel_personNode> person;   // object serializer will ignore this data element

    public SearchResultsModel_bylineNode(){}

    public SearchResultsModel_bylineNode(String original, String organization, List<SearchResultsModel_personNode> person) {
        this.original = original;
        this.organization = organization;
        this.person = person;
    }

    // GETTERS
    public String getOriginal() { return original; }
    public String getOrganization() { return organization; }
    public List<SearchResultsModel_personNode> getPerson() { return person; }

    // SETTERS
    public void setOriginal(String original) { this.original = original; }
    public void setOrganization(String organization) { this.organization = organization; }
    public void setPerson(List<SearchResultsModel_personNode> person) { this.person = person; }
}