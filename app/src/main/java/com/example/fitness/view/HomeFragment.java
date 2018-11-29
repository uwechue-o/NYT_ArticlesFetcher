package com.example.fitness.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitness.IArticlePicture;
import com.example.fitness.adapter.DataAdapter;
import com.example.fitness.model.DataUIModel;
import com.example.fitness.model.SearchResultsModel_documentNode;
import com.example.fitness.model.SearchResultsModel_multimediaNode;
import com.example.fitness.viewmodel.DataViewModel;
import com.example.fitness.viewmodel.SharedViewModel;
import com.example.fitness.webservices.INewYorkTimesAPI;
import com.example.fitness.webservices.Utils;
import com.example.fitness.webservices.Utils.DIRECTION;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.fitness.webservices.Utils.isNetworkAvailable;

/**
 *
 *    ---------  Copyright 2018, Oke Uwechue, All rights reserved. ---------
 */
public class HomeFragment extends ObserverFragment implements SearchView.OnQueryTextListener{

    private String apiKey;
    private Disposable disposable;
    private CompositeDisposable compositeDisposable;
    private View UIcontainer;
    private Resources res;
    private ArrayList<DataUIModel> articlesList;
    private DataAdapter adapter;
    private RecyclerView recyclerView;
    private INewYorkTimesAPI webserviceObj;
    private final String TAG="HomeFragment";
    private DIRECTION direction = DIRECTION.FWD;
    private String searchTerm;
    private FloatingActionButton fab;
    private ProgressBar spinner;
    private int numberOfThumbnails=0;

    //private SearchView searchView = null;
    //private SearchView.OnQueryTextListener queryTextListener;

    @NonNull
    private final DataViewModel viewModel = new DataViewModel();
    private SharedViewModel sviewModel;

    // JNI is used for hiding away sensitive credentials e.g. api keys
    static {
        System.loadLibrary("datastore");
    }

    public native String getApiKey();

    //region    INITIALIZATION ROUTINES
    /**
     * initialize and configure web services if internet connection available
     *
     * @return TRUE if netwk available, FALSE otherwise
     */
    private boolean initializeIfNetworkAvailable()
    {
        boolean activeNetwork = isNetworkAvailable(getActivity());
        if(activeNetwork) {
            apiKey = getApiKey();
            webserviceObj = Utils.newWebservicesInstance(HttpLoggingInterceptor.Level.BODY, INewYorkTimesAPI.BASE_URL);
        }

        return(activeNetwork);
    }

    /**
     * set up recyclerview
     */
    private void initRecyclerView(View v)
    {
        recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Pagination
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
    }

    /**
     * Determines what to do when user tries to scroll beyond top/bottom of current list
     */
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (dy > 0) {
                // Recycle view scrolling down...
                if(!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)){ // if bottom reached, fetch NEXT data screenful
                    Log.w("ON SCROLLED", "SCROLLING DOWN --- BOTTOM REACHED...");
                    direction = DIRECTION.FWD;
                    startQuery(searchTerm, direction);
                }

            } else if (dy < 0) {
                // Recycle view scrolling up...
                int firstVisibleItemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                if(firstVisibleItemPosition==0){        // if top reached, fetch PREVIOUS data screenful
                    Log.w("ON SCROLLED", "SCROLLING UP --- TOP REACHED...");
                    direction = DIRECTION.BACK;
                    startQuery(searchTerm, direction);
                }
            }

        }
    };
    //endregion

    /**
     * Retrieves an article's thumbnail and binds it to the data object
     *
     * @param url       location of image
     * @param dataObj   data object that will hold the retrieved bitmap
     * @param adapter   the bridge between the View and the underlying data for that view
     */
    private <T extends RecyclerView.Adapter> void fetchArticleImage(String url,
                                                                    final IArticlePicture dataObj,
                                                                    final T adapter)
    {
        Call<ResponseBody> call = Utils.newWebservicesInstance(HttpLoggingInterceptor.Level.NONE, INewYorkTimesAPI.WEB_URL).fetchImage(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // render the image in UI
                        Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
                        dataObj.setArticlePic(bm);
                        adapter.notifyDataSetChanged(); // force image to display immediately
                        Log.w("fetchArticleImage", "SUCCESS image cached in bitmap from: ["+url+"]");
                    } else {
                        Snackbar.make(UIcontainer, res.getString(R.string.fail_no_image_data), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(UIcontainer, res.getString(R.string.fail_image_webservicecall), Snackbar.LENGTH_LONG).show();
                }

                if(Utils.IncrementAndTestLoadingCounter(numberOfThumbnails) && spinner.isShown())
                {
                    // hide progress bar after most of data has loaded
                    spinner.setVisibility(ProgressBar.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // @ToDo
                Log.w("fetchArticleImage", "Error Message: " + t.getMessage());
            }
        });
    }

    // region CREATION CYCLE
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);    // without this, Searchview will not display in fragment
    }

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.homeview, container, false);

        initRecyclerView(v);

        fab = v.findViewById(R.id.fab);
        fab.setOnClickListener( vue -> { Snackbar.make(vue, res.getString(R.string.back_to_first_page), Snackbar.LENGTH_LONG).setAction("Action",null).show();
            DataViewModel.resetSearch();
            startQuery(searchTerm, DIRECTION.FWD);});

        return(v);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        sviewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        // initialize system, or else exit if no active internet connection
        if(!initializeIfNetworkAvailable()){

            getActivity().finish();     // exit the app
            return;
        }

        compositeDisposable = new CompositeDisposable();

        UIcontainer = getActivity().findViewById(android.R.id.content);

        spinner = getView().findViewById(R.id.spin_wait);

        res = getResources();   // pre-fetch the Resources instance to avoid multiple method calls

        Intent intent = getActivity().getIntent();
        handleIntent(intent);

    }

    //endregion

    //region    EXTRACT AND PROCESS SEARCH TERMS
    void handleIntent(Intent intent) {

        Log.w("HandleIntent", "Just entered method...");

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Log.w("HandleIntent", "Search Term = "+query);

            searchTerm = query;     // cache it for Prev/Next scroll mechanism

            startQuery(query, direction);
        }
    }
    //endregion

    /**
     * Initialize search capabilities
     *
     * @param menu
     * @return  TRUE if fully handled here, otherwise FALSE
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    //region  SUBSCRIBE & UNSUBSCRIBE FOR OBSERVERS
    @Override
    void bindToObservables()
    {
        // do nothing here. in this case, user initiates from UI
    }

    @Override
    void unbindFromObservables()
    {
        if(compositeDisposable!=null)
            compositeDisposable.clear();
    }
    //endregion

    //region    SEARCH INTERFACE METHODS
    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.w(TAG, "onQueryTextSubmit: query->"+query);

        searchTerm = query;     // cache it for Prev/Next scroll mechanism
        startQuery(query, direction);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.w(TAG, "onQueryTextChange: newText->" + newText);
        //
        return true;
    }
    //endregion

    /**
     * Invoke async call to execute web service request for data
     *
     * @param searchTerm    the keyword user wants to search against
     */
    private void startQuery(String searchTerm, DIRECTION direction)
    {
        if (searchTerm!=null && !searchTerm.isEmpty()) {

            // execute async webservice call
            disposable = viewModel.doDataFetch(searchTerm, apiKey, direction).subscribe(this::handleResponse, this::handleError);
            compositeDisposable.add(disposable);

        } else {
            Snackbar.make(UIcontainer, res.getString(R.string.blank_searchterm), Snackbar.LENGTH_LONG).show();
        }

        // hide the softkeyboard
        Utils.hideKeyboard(getActivity());

    }

    //region HANDLE WEB SERVICE CALL RESULTS
    /**
     * Process the fetched NYT documents data
     *
     * @ToDo : convert this entire method into more succinct Java8 syntax using streams
     *
     * @param l     list of JSON child nodes
     */
    private void handleResponse(List<SearchResultsModel_documentNode> l)
    {
        if(l.size() < 1) {
            Snackbar.make(UIcontainer, res.getString(R.string.empty_results), Snackbar.LENGTH_LONG).show();
        }
        else{      // if resultset is not empty, display spinner

            // display progress bar while data is loading and reset loading counter
            spinner.setVisibility(ProgressBar.VISIBLE);
            Utils.ResetLoadingCounter();

            Log.w("IN handleResponse--- ","FLAG 1 ------");
            // update page number on FAB
            TextView fabTextView = getView().findViewById(R.id.fabtext);
            int increment = direction==DIRECTION.FWD? 1 : -1;
            int newPageNum = DataViewModel.getPageNum()+increment;
            if(newPageNum<0)
                newPageNum=0;

            Log.w("IN handleResponse--- ","FLAG 2 ------");
            fabTextView.setText(String.valueOf(newPageNum));

            articlesList = new ArrayList<>();
            adapter = new DataAdapter(articlesList,getContext(), sviewModel, Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment));

            Log.w("IN handleResponse--- ","FLAG 3 ------");

            numberOfThumbnails=0;

            for(SearchResultsModel_documentNode child : l)
            {
                DataUIModel d = new DataUIModel();
                d.setDescrip(child.getHeadline().getMain());
                d.setNodeData(child);

                Log.w("IN handleResponse--- ","FLAG 4 ------"+d.getDescrip());

                List<SearchResultsModel_multimediaNode> mmNodes = child.getMultimedia();

                Log.w("IN handleResponse--- ","FLAG 5 ------");
                if(mmNodes!=null && mmNodes.size()>0) {                                 // avoid IndexOutOfBoundsException by first checking for Null and size of list
                    SearchResultsModel_multimediaNode mmNode = mmNodes.get(0);          // because multimedia nodes are sometimes just empty array labels
                    if (mmNode != null) {

                        Log.w("IN handleResponse--- ","FLAG 5 ------ URL");
                        d.setPicUrl(mmNode.getUrl());
                        ++numberOfThumbnails;
                        // populate the user image asynchronously
                        fetchArticleImage(d.getPicUrl(), d, adapter);
                    }
                }
                articlesList.add(d);

                int extent = adapter.getItemCount();

                IntStream.range(0, 10).forEach(x -> Log.w("FLAG 7 -- adapter pos"+x, String.valueOf(adapter.getItemId(x))));

            }   // end for

            recyclerView.setAdapter(adapter);
        }   // end else
    }

    /**
     * Handle any errors coming from the
     *
     * @param error
     */
    private void handleError(Throwable error) {

        // always hide progress bar after data/network error
        spinner.setVisibility(ProgressBar.INVISIBLE);

        Snackbar.make(UIcontainer, res.getString(R.string.fail_data_fetch), Snackbar.LENGTH_SHORT).show();
        Log.w("HandleError:datafetch ","Error: "+error.getStackTrace());
        error.printStackTrace();
    }
    //endregion
}

