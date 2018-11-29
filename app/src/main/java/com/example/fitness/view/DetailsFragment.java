package com.example.fitness.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitness.view.databinding.DetailsviewBinding;
import com.example.fitness.model.SearchResultsModel_documentNode;
import com.example.fitness.viewmodel.SharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

/**
 *
 *    ---------  Copyright 2018, Oke Uwechue, All rights reserved. ---------
 */
public class DetailsFragment extends Fragment {
    SharedViewModel sviewModel;
    private SearchResultsModel_documentNode datanode;
    private DetailsviewBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DetailsviewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        datanode = new SearchResultsModel_documentNode();
        binding.setLifecycleOwner(this);
        binding.setDocument(datanode);
        return (view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = NavHostFragment.findNavController(this);
        Navigation.setViewNavController(binding.fab2, navController);
        binding.fab2.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.screen_home));

        /*
        binding.fab2.setOnClickListener((v)-> {
                                                Log.w("FAB CLICK LISTENER","BUTTON CLICKED---");
                                                NavController n = NavHostFragment.findNavController(this);
                                                SecondFragmentDirections.ActionSecondFragmentToFirstFragment direction = SecondFragmentDirections.actionSecondFragmentToFirstFragment();
                                                n.navigate(direction);
                                            });
        */

        sviewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        sviewModel.getData().observe(getViewLifecycleOwner(), (data) -> {
            datanode.setDocument_type(data.getDocument_type());
            datanode.setScore(data.getScore());
            datanode.set_id(data.get_id());
            datanode.setByline(data.getByline());
            datanode.setHeadline(data.getHeadline());
            datanode.setNews_desk(data.getNews_desk());
            datanode.setPrint_page(data.getPrint_page());
            datanode.setSnippet(data.getSnippet());
            datanode.setWeb_url(data.getWeb_url());
            datanode.setPub_date(data.getPub_date());
            datanode.setSource(data.getSource());
            datanode.setWord_count(data.getWord_count());
            datanode.setType_of_material(data.getType_of_material());
            datanode.setUri(data.getUri());
            //binding.executePendingBindings();     // no need to force this action. data seems to be updating immediately
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // @ToDo
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            // @ToDo
        }
    }

}