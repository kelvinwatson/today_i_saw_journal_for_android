package com.watsonlogic.todayisaw;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ImageController {
    private ImageButton selectImgBtn;
    private ImageView galleryImgView;
    private Activity activity;

    public ImageController(){

    }

    public ImageController(Activity activity, ImageButton selectImgBtn, ImageView galleryImgView){
        this.activity = activity;
        this.selectImgBtn = selectImgBtn;
        this.galleryImgView = galleryImgView;
    }

    public void setImageUIVisibilities(ImageButton ib, ImageView iv){
        ib.setVisibility(View.GONE);
        iv.setVisibility(View.VISIBLE);
    }

    private void setImage(Activity a, Intent data){
        setImageUIVisibilities(selectImgBtn, galleryImgView);
        Uri imageUri = data.getData();
        InputStream imageStream;
        try {
            imageStream = a.getContentResolver().openInputStream(imageUri);
            Bitmap bm = decodeBitmap(imageUri);
            setImageIntoView(bm);
            if (imageStream != null){
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
        BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(selectedImage), null, o);
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
        return BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(selectedImage), null, o2);
    }
}
