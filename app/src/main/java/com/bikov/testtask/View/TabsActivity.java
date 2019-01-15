package com.bikov.testtask.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.bikov.testtask.R;

import java.util.ArrayList;

public class TabsActivity extends AppCompatActivity {

    private View accountView;
    private View mapView;
    private View listView;
    private ArrayList<View> fragmentViews;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    changeVisibility(mapView);
                    return true;
                case R.id.navigation_account:
                    changeVisibility(accountView);
                    return true;
                case R.id.navigation_markers:
                    changeVisibility(listView);
                    return true;
            }
            return false;
        }
    };

    private void changeVisibility(View visible){
        for(View view:fragmentViews){
            if(view.getVisibility() == View.VISIBLE){
                view.setVisibility(View.INVISIBLE);
            }
        }
        visible.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        Fragment accountFragment = getSupportFragmentManager().findFragmentById(R.id.account_fragment);
        Fragment mapFragment = getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        Fragment listFragment = getSupportFragmentManager().findFragmentById(R.id.list_fragment);

        accountView = accountFragment.getView();
        mapView = mapFragment.getView();
        listView = listFragment.getView();

        fragmentViews = new ArrayList<>(3);
        fragmentViews.add(accountView);
        fragmentViews.add(mapView);
        fragmentViews.add(listView);

        accountView.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.INVISIBLE);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
