package com.example.fitness.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitness.model.SearchResultsModel_documentNode;

/**
 * Allows fragments in the navigation flow to share data between each other
 *
 */
public class SharedViewModel extends ViewModel {

    private MutableLiveData<SearchResultsModel_documentNode> data = new MutableLiveData<>();

    public void setData(SearchResultsModel_documentNode data){
        this.data.setValue(data);
    }

    public LiveData<SearchResultsModel_documentNode> getData(){
        return(data);
    }

}
