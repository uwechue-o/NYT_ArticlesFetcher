package com.example.fitness.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
// import androidx.lifecycle.ViewModel;

import com.example.fitness.model.SearchResultsModel_documentNode;
import com.example.fitness.model.SearchResultsModel_parentNode;
import com.example.fitness.model.SearchResultsModel_response;
import com.example.fitness.view.R;
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
public class DataViewModel extends AndroidViewModel {

    private static int pageNum = -1;
    public static final int RESET = -1;

    private MutableLiveData<Integer> snackbarMesgId=new MutableLiveData<>();

    public static void resetSearch(){
        pageNum = RESET;
    }

    public static int getPageNum(){
        return(pageNum);
    }

    /**
     * View can subscribe to this message
     * @return
     */
    public MutableLiveData<Integer> getSnackbarMesgId(){
        return(snackbarMesgId);
    }

    /**
     * Update value of message (which triggers the View to display a mesg)
     *
     * @param mesgID
     */
    private void setSnackbarMesgId(int mesgID){
        snackbarMesgId.setValue(mesgID);
        snackbarMesgId.postValue(mesgID);
    }

    /**
     * Ensures that neither page numbers<0 nor page numbers>200 are ever generated
     */
    private void sanitizePagenumber()
    {
        if(pageNum<0) {
            //Log.w("DVModel","page # requested < 0!");
            pageNum = 0;
            setSnackbarMesgId(RESET);       // wrap actual string mesg in RESETs to prevent multiple invocations of snackbar mesg display
            setSnackbarMesgId(R.string.already_at_start);
            setSnackbarMesgId(RESET);
        }

        if(pageNum > 200)
            pageNum = 200;
    }

    public DataViewModel(Application application){
        super(application);
    }

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