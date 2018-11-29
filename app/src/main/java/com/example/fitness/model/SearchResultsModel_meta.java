package com.example.fitness.model;

import java.io.Serializable;

/**
 *
 *    ---------  Copyright 2018, Oke Uwechue, All rights reserved. ---------
 */
public class SearchResultsModel_meta implements Serializable {
    private static final long serialVersionUID = 4040282614337029151L;

    public SearchResultsModel_meta(){}

    private long hits, offset, time;

    //GETTERS
    public long getHits() { return hits; }
    public long getOffset() { return offset; }
    public long getTime() { return time; }

    //SETTERS
    public void setHits(long hits) { this.hits = hits; }
    public void setOffset(long offset) { this.offset = offset; }
    public void setTime(long time) { this.time = time; }

}
