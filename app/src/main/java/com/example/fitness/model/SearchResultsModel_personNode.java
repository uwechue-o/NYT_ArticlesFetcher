package com.example.fitness.model;

import java.io.Serializable;

/**
 *
 *    ---------  Copyright 2018, Oke Uwechue, All rights reserved. ---------
 */
class SearchResultsModel_personNode implements Serializable {
    private static final long serialVersionUID = 4060282614337029151L;

    private String firstname, middlename, lastname, qualifier, title, role, organization;
    private int rank;

    public SearchResultsModel_personNode(){}

    public SearchResultsModel_personNode(String firstname, String middlename, String lastname, String qualifier, String title, String role, String organization, int rank) {
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.qualifier = qualifier;
        this.title = title;
        this.role = role;
        this.organization = organization;
        this.rank = rank;
    }

    // GETTERS
    public String getFirstname() { return firstname; }
    public String getMiddlename() { return middlename; }
    public String getLastname() { return lastname; }
    public String getQualifier() { return qualifier; }
    public String getTitle() { return title; }
    public String getRole() { return role; }
    public String getOrganization() { return organization; }
    public int getRank() { return rank; }

    // SETTERS
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public void setMiddlename(String middlename) { this.middlename = middlename; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setQualifier(String qualifier) { this.qualifier = qualifier; }
    public void setTitle(String title) { this.title = title; }
    public void setRole(String role) { this.role = role; }
    public void setOrganization(String organization) { this.organization = organization; }
    public void setRank(int rank) { this.rank = rank; }

}
