package com.purefaithstudio.shopbiz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by harsimran singh on 18-10-2015.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    ImageSwitcher ims;
    int type=0;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }
    public DownloadImageTask(ImageSwitcher bmImage) {
        this.ims = bmImage;
        type=1;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error",""+ e.getStackTrace());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        if(type==0)
            bmImage.setImageBitmap(result);
        else
            ims.setImageDrawable( new BitmapDrawable(result));
    }
}