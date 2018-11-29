package com.example.fitness.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class ContainerActivity extends AppCompatActivity {

    // region CREATION CYCLE
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mainview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

    }
    //endregion

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Fragment currentChild = getCurrentChild();

        if(currentChild!=null && (currentChild instanceof HomeFragment))
        {
            Log.w("OnNewIntent", "Just entered method...");
            ((HomeFragment)currentChild).handleIntent(intent);
        }

    }

    /**
     * Returns fragment currently being displayed
     *
     * @return
     */
    private Fragment getCurrentChild(){
        Fragment currentChild;

        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
        currentChild = navHostFragment.getChildFragmentManager().getFragments().get(0);

        return(currentChild);
    }

}