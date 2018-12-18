package com.bikov.testtask.View;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bikov.testtask.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    private AppBarLayout appBarLayout;
    private CircleImageView image;
    private Toolbar toolbar;
    private int imageX;
    private int imageY;
    private int imageStartSize;
    private int imageCurrentSize;
    private int imageMargin;
    private int halfToolbarHeight;
    private float imageScale;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, null);

        toolbar = view.findViewById(R.id.toolbar);
        image = view.findViewById(R.id.profile_image);
        appBarLayout = view.findViewById(R.id.appbar);

        imageStartSize = image.getHeight();
        halfToolbarHeight = toolbar.getHeight()/2;

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                float offset = i;
                imageScale = (offset+311)/312;
                imageY = -i/2 + halfToolbarHeight;
                System.out.println(offset);
                if (imageY>halfToolbarHeight){
                    image.setY(imageY);
                }

                if(offset>-288) {
                    image.setScaleX(imageScale);
                    image.setScaleY(imageScale);
                }
            }
        });
            return view;
    }

}
