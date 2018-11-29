package com.example.fitness.model;

import android.graphics.Bitmap;
import com.example.fitness.IArticlePicture;

/**
 * Created by OUwechue on 11/17/18.
 *
 *    ---------  Copyright 2018, Oke Uwechue, All rights reserved. ---------
 */

public class DataUIModel implements IArticlePicture
{
    private String descrip;
    private String picUrl;
    private Bitmap articlePic;
    private SearchResultsModel_documentNode nodeData;

    // GETTERS
    public String getDescrip() { return descrip; }
    public Bitmap getArticlePic() { return articlePic; }
    public String getPicUrl(){ return picUrl; }
    public SearchResultsModel_documentNode getNodeData() { return nodeData; }

    //SETTERS
    public void setDescrip(String d) { this.descrip = d; }
    public void setPicUrl(String u) { this.picUrl = u; }
    public void setNodeData(SearchResultsModel_documentNode nodeData) { this.nodeData = nodeData; }

    @Override
    public void setArticlePic(Bitmap b) { this.articlePic = b; }

}
