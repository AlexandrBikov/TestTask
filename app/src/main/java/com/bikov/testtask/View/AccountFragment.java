package com.bikov.testtask.View;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bikov.testtask.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment/* implements View.OnClickListener*/ {

    private AppBarLayout appBarLayout;
  //  private CircleImageView image;
    private Toolbar toolbar;
    private TextInputLayout name;
    private FloatingActionButton editButton;
    private int imageX;
    private int totalScrollRange;
    private int imageMargin;
    private int toolbarHeight;
    private int imageStartSize;
    private int imageSize;
    private float imageScale;
    private ViewGroup.MarginLayoutParams imageMarginParams;
    private boolean editModeOn = false;
    private boolean imageAdded = false;

    private static final int READ_REQUEST_CODE = 42;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, null);
/*
        name = view.findViewById(R.id.name);
        toolbar = view.findViewById(R.id.toolbar);
        image = view.findViewById(R.id.profile_image);
        appBarLayout = view.findViewById(R.id.appbar);
        editButton = view.findViewById(R.id.edit_button);

        imageMarginParams = (ViewGroup.MarginLayoutParams) image.getLayoutParams();

//        editButton.setOnClickListener(this);
//        image.setOnClickListener(this);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                float offset = i;
                totalScrollRange = appBarLayout.getTotalScrollRange();
                imageMargin = imageMarginParams.topMargin;

                toolbarHeight = toolbar.getHeight();
                imageStartSize = image.getHeight();

                imageScale = offset / totalScrollRange + 1;
                imageSize = (int) (imageStartSize * imageScale);
                imageX = imageMargin - imageSize + (int) (imageSize * imageScale);

                if (imageSize > toolbarHeight * 0.75) {
                    name.setPadding(imageSize, 0, 0, 0);
                    image.setX(imageX);
                    image.setScaleX(imageScale);
                    image.setScaleY(imageScale);
                }
            }
        });*/
        return view;
    }
/*
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_button:
                editButtonClicked();
                break;
            case R.id.profile_image:
                System.out.println("Good");
                changeProfileImage();
                break;
        }
    }

    private void editButtonClicked() {
        if (editModeOn) {
            editModeOn = false;
            editButton.setImageResource(R.drawable.edit_button_icon);
            if (!imageAdded) {
                image.setImageResource(R.drawable.no_photo_icon);
            }
        } else {
            editModeOn = true;
            editButton.setImageResource(R.drawable.done_button_icon);
            if (!imageAdded) {
                image.setImageResource(R.drawable.add_photo_icon);
            }
        }
    }

    private void changeProfileImage() {
        if (editModeOn) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, READ_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
            }
            imageAdded = true;
            image.setImageURI(uri);
        }

    }*/
}
