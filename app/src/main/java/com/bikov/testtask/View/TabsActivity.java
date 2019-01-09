package com.bikov.testtask.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.bikov.testtask.R;

public class TabsActivity extends AppCompatActivity {

    private View accountView;
    private View mapView;
    private View listView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        //TODO method to change visibility
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    mapView.setVisibility(View.VISIBLE);
                    accountView.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_account:
                    mapView.setVisibility(View.INVISIBLE);
                    accountView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_markers:
                    mapView.setVisibility(View.INVISIBLE);
                    accountView.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        Fragment accountFragment = getSupportFragmentManager().findFragmentById(R.id.account);
        Fragment mapFragment = getSupportFragmentManager().findFragmentById(R.id.map);
        Fragment listFragment = getSupportFragmentManager().findFragmentById(R.id.list);

        accountView = accountFragment.getView();
        mapView = mapFragment.getView();
        listView = listFragment.getView();

        accountView.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.INVISIBLE);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
