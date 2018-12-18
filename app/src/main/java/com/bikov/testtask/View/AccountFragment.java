package com.bikov.testtask.View;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bikov.testtask.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    private AppBarLayout appBarLayout;
    private CircleImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CoordinatorLayout view =  (CoordinatorLayout) inflater.inflate(R.layout.fragment_account, null);
        image = view.findViewById(R.id.profile_image);

//        image.setScaleX(0.2f);
//        image.setScaleY(0.2f);

        appBarLayout = view.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                float offset = i;
                System.out.println(image.getHeight());
                if(offset>-288) {
                    image.setY(offset + 288);
                    image.setScaleX((offset+311)/312);
                    image.setScaleY((offset+311)/312);
                }
            }
        });
            return view;
    }

}
