package com.example.fitness.model;

import java.io.Serializable;

/**
 *
 *    ---------  Copyright 2018, Oke Uwechue, All rights reserved. ---------
 */
class SearchResultsModel_keywordsNode implements Serializable {
    private static final long serialVersionUID = 4030282614337029151L;

    private String name, value, major;
    private int rank;

    public SearchResultsModel_keywordsNode(){}

    public SearchResultsModel_keywordsNode(String name, String value, String major, int rank) {
        this.name = name;
        this.value = value;
        this.major = major;
        this.rank = rank;
    }

    // GETTERS
    public String getName() { return name; }
    public String getValue() { return value; }
    public String getMajor() { return major; }
    public int getRank() { return rank; }

    // SETTERS
    public void setName(String name) { this.name = name; }
    public void setValue(String value) { this.value = value; }
    public void setMajor(String major) { this.major = major; }
    public void setRank(int rank) { this.rank = rank; }

}
