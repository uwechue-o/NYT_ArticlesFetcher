package com.example.fitness.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitness.view.R;
import com.example.fitness.model.DataUIModel;
import com.example.fitness.model.SearchResultsModel_documentNode;
import com.example.fitness.viewmodel.SharedViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

/**
 *
 *    ---------  Copyright 2018, Oke Uwechue, All rights reserved. ---------
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private final ArrayList<DataUIModel> dataList;
    private final WeakReference<SharedViewModel> svm;
    //public static final String SerializationKey = "com.example.fitness.adapter.DataAdapter";
    private final WeakReference<NavController> nav;

    public DataAdapter(ArrayList<DataUIModel> dataList, SharedViewModel svm, NavController nav) {
        this.dataList = dataList;
        this.svm = new WeakReference<>(svm);
        this.nav = new WeakReference<>(nav);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_basic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.description.setText(dataList.get(position).getDescrip());
        holder.thumbnail.setImageBitmap(dataList.get(position).getArticlePic());
        holder.data = dataList.get(position).getNodeData();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView description;
        private final ImageView thumbnail;
        private SearchResultsModel_documentNode data;

        ViewHolder(View view) {
            super(view);

            description = view.findViewById(R.id.headline);
            thumbnail = view.findViewById(R.id.article_picture);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            // transmit new data to the destination fragment
            if(svm.get()!=null && nav.get()!=null) {
                svm.get().setData(data);
                // navigate to the destination fragment
                nav.get().navigate(R.id.action_screen_home_to_screen_details);
            }

        }
    }
}