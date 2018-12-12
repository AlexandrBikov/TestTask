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

    Fragment accountFragment;
    Fragment mapFragment;

    View accountView;
    View mapView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    mapView.setVisibility(View.VISIBLE);
                    accountView.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_account:
                    mapView.setVisibility(View.INVISIBLE);
                    accountView.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        accountFragment = getSupportFragmentManager().findFragmentById(R.id.account);
        mapFragment = getSupportFragmentManager().findFragmentById(R.id.map);

        accountView = accountFragment.getView();
        mapView = mapFragment.getView();

        accountView.setVisibility(View.INVISIBLE);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
