package com.example.fitness.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.io.Serializable;
import java.util.List;

/**
 * Created by OUwechue on 11/17/2018.
 *
 *    ---------  Copyright 2018, Oke Uwechue, All rights reserved. ---------
 *
 *
 * This class models the first-level nested nodes of interest in the NYT JSON response.
 *
 * It contains the following JSON nodes:
 *      "web_url", "snippet", "print_page", "blog", "source", "multimedia", "headline", "keywords", "pub_date",
 *      "document_type", "news_desk", "byline",  "type_of_material", "_id",  "word_count", "score", "uri"
 *
 */
public class SearchResultsModel_documentNode extends BaseObservable implements Serializable {
    private static final long serialVersionUID = 4980282614337029151L;

    // top-level nodes:
    private String web_url, snippet, print_page, source, pub_date, document_type, news_desk, type_of_material, _id, uri;
    private float score;
    private int word_count;

    // nested nodes:
    private SearchResultsModel_bylineNode byline;
    private List<SearchResultsModel_keywordsNode> keywords;
    private SearchResultsModel_headlineNode headline;
    private List<SearchResultsModel_multimediaNode> multimedia;
    // private Object[] blog;       --  don't bother capturing this node. not used in this sample project.

    public SearchResultsModel_documentNode(){}

    public SearchResultsModel_documentNode(String web_url,
                               String snippet,
                               String print_page,
                               String source,
                               String pub_date,
                               String document_type,
                               String news_desk,
                               String type_of_material,
                               String _id,
                               String uri,
                               float score,
                               int word_count,
                               SearchResultsModel_bylineNode byline,
                               List<SearchResultsModel_keywordsNode> keywords,
                               SearchResultsModel_headlineNode headline,
                               List<SearchResultsModel_multimediaNode> multimedia )
    {
        this.web_url = web_url;
        this.snippet = snippet;
        this.print_page = print_page;
        this.source = source;
        this.pub_date = pub_date;
        this.document_type = document_type;
        this.news_desk = news_desk;
        this.type_of_material = type_of_material;
        this._id = _id;
        this.uri = uri;
        this.score = score;
        this.word_count = word_count;
        this.headline = headline;
        this.byline = byline;
        this.keywords = keywords;
        this.multimedia = multimedia;
    }

    // GETTERS
    @Bindable
    public String getWeb_url() { return web_url; }
    @Bindable
    public String getSnippet() { return snippet; }
    @Bindable
    public String getPrint_page() { return print_page; }
    @Bindable
    public String getSource() { return source; }
    @Bindable
    public String getPub_date() { return pub_date; }
    @Bindable
    public String getDocument_type() { return document_type; }
    @Bindable
    public String getNews_desk() { return news_desk; }
    @Bindable
    public String getType_of_material() { return type_of_material; }
    @Bindable
    public String get_id() { return _id; }
    @Bindable
    public String getUri() { return uri; }
    @Bindable
    public float getScore() { return score; }
    @Bindable
    public int getWord_count() { return word_count; }
    public List<SearchResultsModel_multimediaNode> getMultimedia(){ return multimedia; }
    @Bindable
    public SearchResultsModel_headlineNode getHeadline(){ return headline; }
    public List<SearchResultsModel_keywordsNode> getKeywords(){ return keywords; }
    @Bindable
    public SearchResultsModel_bylineNode getByline(){ return byline; }

    // SETTERS
    public void setWeb_url(String web_url) { this.web_url = web_url; notifyPropertyChanged(BR.web_url);}
    public void setSnippet(String snippet) { this.snippet = snippet; notifyPropertyChanged(BR.snippet);}
    public void setPrint_page(String print_page) { this.print_page = print_page; notifyPropertyChanged(BR.print_page);}
    public void setSource(String source) { this.source = source; notifyPropertyChanged(BR.source);}
    public void setPub_date(String pub_date) { this.pub_date = pub_date; notifyPropertyChanged(BR.pub_date);}
    public void setDocument_type(String document_type) { this.document_type = document_type; notifyPropertyChanged(BR.document_type);}
    public void setNews_desk(String news_desk) { this.news_desk = news_desk; notifyPropertyChanged(BR.news_desk);}
    public void setType_of_material(String type_of_material) { this.type_of_material = type_of_material; notifyPropertyChanged(BR.type_of_material);}
    public void set_id(String _id) { this._id = _id; notifyPropertyChanged(BR._id);}
    public void setUri(String uri) { this.uri = uri; notifyPropertyChanged(BR.uri);}
    public void setScore(float score) { this.score = score; notifyPropertyChanged(BR.score);}
    public void setWord_count(int word_count) { this.word_count = word_count; notifyPropertyChanged(BR.word_count);}
    public void setByline(SearchResultsModel_bylineNode byline) { this.byline = byline; notifyPropertyChanged(BR.byline);}
    public void setKeywords(List<SearchResultsModel_keywordsNode> keywords) { this.keywords = keywords; }
    public void setHeadline(SearchResultsModel_headlineNode headline) { this.headline = headline; notifyPropertyChanged(BR.headline);}
    public void setMultimedia(List<SearchResultsModel_multimediaNode> multimedia) { this.multimedia = multimedia; }
}
