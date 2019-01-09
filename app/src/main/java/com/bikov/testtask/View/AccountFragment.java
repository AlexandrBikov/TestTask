package com.bikov.testtask.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bikov.testtask.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private AppBarLayout appBarLayout;
    private CircleImageView image;
    private Toolbar toolbar;
    private EditText nameField;
    private EditText loginField;
    private EditText bioField;
    private EditText phoneField;
    private ImageButton backButton;
    private FloatingActionButton editButton;
    private View rootView;
    private float imageX;
    private float imageY;
    private float imageStartY;
    private int totalScrollRange;
    private int imageMargin;
    private int toolbarHeight;
    private int imageStartSize;
    private int imageSize;
    private int backButtonSize;
    private float imageScale;
    private ViewGroup.MarginLayoutParams imageMarginParams;
    private boolean editModeOn = false;
    private boolean imageAdded = false;

    private static final int READ_REQUEST_CODE = 42;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_account, null);

        initViewVariables();

        imageMarginParams = (ViewGroup.MarginLayoutParams) image.getLayoutParams();

        editButton.setOnClickListener(this);
        image.setOnClickListener(this);

        makeFieldsNonEditable();
        setImageAnimation();

        return rootView;
    }

    private void setImageAnimation() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                float offset = i;
                totalScrollRange = appBarLayout.getTotalScrollRange();
                imageMargin = imageMarginParams.topMargin;

                toolbarHeight = toolbar.getHeight();
                imageStartSize = image.getHeight();
                backButtonSize = backButton.getWidth();
                if (imageStartY == 0) {
                    imageStartY = image.getY();
                }

                imageScale = offset / totalScrollRange + 1;
                imageSize = (int) (imageStartSize * imageScale);
                imageX = imageMargin * 2 + (backButtonSize - imageSize + imageMargin) * imageScale;
                imageY = imageStartY + imageStartSize - imageSize;

                if (imageSize > toolbarHeight * 0.75) {
                    image.setY(imageY);
                    image.setX(imageX);
                    image.setScaleX(imageScale);
                    image.setScaleY(imageScale);
                }
            }
        });
    }

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

    private void initViewVariables() {
        toolbar = rootView.findViewById(R.id.toolbar);
        image = rootView.findViewById(R.id.profile_image);
        appBarLayout = rootView.findViewById(R.id.appbar);
        editButton = rootView.findViewById(R.id.edit_button);
        nameField = rootView.findViewById(R.id.name_field);
        phoneField = rootView.findViewById(R.id.phone_field);
        bioField = rootView.findViewById(R.id.bio_field);
        loginField = rootView.findViewById(R.id.login_field);
        backButton = rootView.findViewById(R.id.back_button);
    }

    private void editButtonClicked() {
        if (editModeOn) {
            editModeOn = false;
            setIconsToNonEditMode();
            makeFieldsNonEditable();
            hideKeyboard(getContext(), nameField);
        } else {
            editModeOn = true;
            setIconsToEditMode();
            makeFieldsEditable();
        }
    }

    private void setIconsToEditMode() {
        editButton.setImageResource(R.drawable.done_button_icon);
        if (!imageAdded) {
            image.setImageResource(R.drawable.add_photo_icon);
        }
    }

    private void setIconsToNonEditMode() {
        editButton.setImageResource(R.drawable.edit_button_icon);
        if (!imageAdded) {
            image.setImageResource(R.drawable.no_photo_icon);
        }
    }

    private void makeFieldsEditable() {
        nameField.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        bioField.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        loginField.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        phoneField.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
    }

    private void makeFieldsNonEditable() {
        nameField.setInputType(InputType.TYPE_NULL);
        bioField.setInputType(InputType.TYPE_NULL);
        loginField.setInputType(InputType.TYPE_NULL);
        phoneField.setInputType(InputType.TYPE_NULL);
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

    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }
}
