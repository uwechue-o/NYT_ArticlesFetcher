package com.example.fitness.webservices;

import com.example.fitness.model.SearchResultsModel_response;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 *  Definition of Retrofit web services endpoints and URLs
 *
 *    ---------  Copyright 2018, Oke Uwechue, All rights reserved. ---------
 */
public interface INewYorkTimesAPI {

    String BASE_URL = "https://api.nytimes.com";
    String WEB_URL = "https://www.nytimes.com";

    @GET("/svc/search/v2/articlesearch.json")
    Observable<SearchResultsModel_response> getSearchResults(@Query("q") String searchTerm,
                                                             @Query("page") int page,
                                                             @Query("api-key") String key);

    //fetch an image
    @GET
    Call<ResponseBody> fetchImage(@Url String url);
}