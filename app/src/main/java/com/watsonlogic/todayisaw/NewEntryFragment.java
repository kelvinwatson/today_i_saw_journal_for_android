package com.watsonlogic.todayisaw;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewEntryFragment extends Fragment implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "NewEntryFragment";
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ImageButton selectImgBtn;
    private ImageView galleryImgView;
    private ImageController imageController;

    public NewEntryFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static NewEntryFragment newInstance(int sectionNumber) {
        NewEntryFragment fragment = new NewEntryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_entry, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getWidgets();
        imageController = new ImageController(getActivity(), selectImgBtn, galleryImgView);
    }

    private void getWidgets() {
        selectImgBtn = (ImageButton) getActivity().findViewById(R.id.select_image_btn);
        selectImgBtn.setOnClickListener(this);
        galleryImgView = (ImageView) getActivity().findViewById(R.id.gallery_img);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_image_btn: {
                Log.d(TAG, "select image button clicked!");
                Intent getImgIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
                getImgIntent.setType("image/*");
                startActivityForResult(getImgIntent, PICK_IMAGE_REQUEST);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_REQUEST: {
                if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                    setImage(getActivity(), data);
                } else {
                    Log.d(TAG, "RESULT CANCELLED");
                    return;
                }
                break;
            }
            default: {
                break;
            }

        }
    }

    public void setImageUIVisibilities(ImageButton ib, ImageView iv) {
        ib.setVisibility(View.GONE);
        iv.setVisibility(View.VISIBLE);
    }

    private void setImage(Activity a, Intent data) {
        setImageUIVisibilities(selectImgBtn, galleryImgView);
        Uri imageUri = data.getData();
        InputStream imageStream;
        try {
            imageStream = a.getContentResolver().openInputStream(imageUri);
            Bitmap bm = decodeBitmap(imageUri);
            setImageIntoView(bm);
            if (imageStream != null) {
                imageStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setImageIntoView(Bitmap bm) {
        galleryImgView.setImageBitmap(bm);
    }

    public Bitmap decodeBitmap(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 100;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o2);
    }
}
