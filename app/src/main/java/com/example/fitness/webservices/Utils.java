package com.example.fitness.webservices;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 *
 *    ---------  Copyright 2018, Oke Uwechue, All rights reserved. ---------
 */
public final class Utils {

    private Utils(){}  // no instantiation permitted

    public enum DIRECTION {
        FWD, BACK
    }

    //region    DATALOADING COUNTER MECHANISM
    private static int loadingCounter = 0;
    private final static int MIN_ITEMS_LOAD = 5;    // this represents 50% of the number(10) of items per results page

    /**
     * This method can be accessed by asynchronous threads, so make it synchronized.
     * Increments loading counter.
     *
     * @param maxItems      max number of items being fetched
     *
     * @return
     */
    public synchronized static boolean IncrementAndTestLoadingCounter(int maxItems)
    {
        int threshold = maxItems > MIN_ITEMS_LOAD ? MIN_ITEMS_LOAD : maxItems;
        return (++loadingCounter >= threshold);

    }

    /**
     * This method involves a variable that can be modified by multiple threads, so make it synchronized
     */
    public synchronized static void ResetLoadingCounter()
    {
        loadingCounter = 0;
    }
    //endregion

    /**
     * Determines whether or not there is a working internet connection
     *
     * @param ctx   caller's context
     * @return TRUE is connectivity good, otherwise FALSE
     */
    public static boolean isNetworkAvailable(Context ctx)
    {
        ConnectivityManager cm = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();

        return(isConnected);
    }

    /**
     * Hide the softkeyboard
     *
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     *  Generates a new Retrofit instance for invoking queries against the NYT API
     *
     * @param logLevel      the desired logging level
     * @return
     */
    public static INewYorkTimesAPI newWebservicesInstance(HttpLoggingInterceptor.Level logLevel, String base_url)
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // add network logging capability
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(logLevel);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return(retrofit.create(INewYorkTimesAPI.class));
    }
}
