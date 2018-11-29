package com.example.fitness.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.fitness.model.SearchResultsModel_documentNode;
import com.example.fitness.model.SearchResultsModel_parentNode;
import com.example.fitness.model.SearchResultsModel_response;
import com.example.fitness.webservices.INewYorkTimesAPI;
import com.example.fitness.webservices.Utils;
import com.example.fitness.webservices.Utils.DIRECTION;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 *
 *    ---------  Copyright 2018, Oke Uwechue, All rights reserved. ---------
 */

/**
 * MVVM View model class
 */
public class DataViewModel extends ViewModel {

    private static int pageNum = -1;

    public static void resetSearch(){
        pageNum = -1;
    }

    public static int getPageNum(){
        return(pageNum);
    }

    /**
     * Ensures that negative page numbers are never used and that page numbers above 200 are never used
     */
    private void sanitizePagenumber()
    {
        if(pageNum<0)
            pageNum = 0;

        if(pageNum > 200)
            pageNum = 200;
    }

    public DataViewModel(){ }

    // create data transformer to extract inner 'response' data node when presented with original JSON data
    // use lambda instead of traditional anon class:
    private final ObservableTransformer<SearchResultsModel_response, SearchResultsModel_parentNode> nodeTransformer = (json)-> json.map(y -> y.getResponse());

    /**
     * Asynchronously fetch the articles data
     *
     * @param searchTerm      keyword(s) to be used for search
     * @return a single observable representing the retrieved model object
     */
    @NonNull
    public Observable<List<SearchResultsModel_documentNode>> doDataFetch(String searchTerm, String apiKey, Utils.DIRECTION direction)
    {
        if(DIRECTION.FWD == direction)
            ++pageNum;
        else
            --pageNum;

        sanitizePagenumber();

        INewYorkTimesAPI webService = Utils.newWebservicesInstance(HttpLoggingInterceptor.Level.BODY, INewYorkTimesAPI.BASE_URL); // add logging of network traffic to facilitate debugging

        Observable<List<SearchResultsModel_documentNode>> asyncData = webService.getSearchResults(searchTerm, pageNum, apiKey)
                .subscribeOn(Schedulers.io())               // network requests are IO-bound so use IO threads here
                .observeOn(Schedulers.computation())
                .compose(nodeTransformer)
                //  .doOnNext(System.out::println)
                .map(datum -> datum.getDocs())             // extract inner nodes of interest from the generated model object
                .observeOn(AndroidSchedulers.mainThread());  // switch back to UI thread before executing the callback methods cos we need to update UI Views

        return(asyncData);
    }
}