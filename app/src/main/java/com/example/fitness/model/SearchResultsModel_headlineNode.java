package com.example.fitness.model;

import java.io.Serializable;

/**
 *
 *    ---------  Copyright 2018, Oke Uwechue, All rights reserved. ---------
 */
public class SearchResultsModel_headlineNode implements Serializable {
    private static final long serialVersionUID = 4020282614337029151L;

    private String main, print_headline;        // all other nodes have been ignored. just these two are useful for now

    public SearchResultsModel_headlineNode(){}

    public SearchResultsModel_headlineNode(String main, String print_headline)
    {
        this.main = main;
        this.print_headline = print_headline;
    }

    // GETTERS
    public String getMain() { return main; }
    public String getPrint_headline() { return print_headline; }

    // SETTERS
    public void setMain(String main) { this.main = main; }
    public void setPrint_headline(String print_headline) { this.print_headline = print_headline; }
}
