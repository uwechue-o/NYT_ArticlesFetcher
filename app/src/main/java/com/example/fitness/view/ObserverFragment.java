package com.example.fitness.view;

import androidx.fragment.app.Fragment;

/**
 *  This class encapsulates behaviours common to all activities subscribing to the Observables framework
 *  The abstract methods guarantee proper attachment and detachment to/from async data streams
 *
 *
 *    ---------  Copyright 2018, Oke Uwechue, All rights reserved. ---------
 */
public abstract class ObserverFragment extends Fragment
{
    @Override
    public void onResume() {

        super.onResume();
        bindToObservables();
    }

    @Override
    public void onPause() {

        unbindFromObservables();
        super.onPause();
    }

    abstract void bindToObservables();
    abstract void unbindFromObservables();

}
