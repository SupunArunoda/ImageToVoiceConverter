
package com.yalantis.cameramodule.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import timber.log.Timber;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.os.AsyncTask;

import com.yalantis.cameramodule.CameraConst;
import com.yalantis.cameramodule.interfaces.PhotoSavedListener;

public class CropPhotoTask extends AsyncTask<Void, Void, Void> {

    private String path;
    private int width;
    private int height;
    private RectF rect;
    private PhotoSavedListener callback;

    public CropPhotoTask(String path, int width, int height, RectF rect, PhotoSavedListener callback) {
        this.path = path;
        this.rect = rect;
        this.width = width;
        this.height = height;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Bitmap src = BitmapFactory.decodeFile(path);
        float koefW = (float) width / (float) src.getWidth();
        float koefH = (float) height / (float) src.getHeight();

        rect.top /= koefH;
        rect.left /= koefW;
        rect.right /= koefW;
        rect.bottom /= koefH;
        width = (int) rect.width();
        height = (int) rect.height();

        Bitmap bitmap = Bitmap.createBitmap(src, (int) rect.left, (int) rect.top, width, height);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(path));

            bitmap.compress(Bitmap.CompressFormat.JPEG, CameraConst.COMPRESS_QUALITY, fos);

        } catch (FileNotFoundException e) {
            Timber.e(e, "File not found: " + e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                Timber.e(e, e.getMessage());
            }
        }
        bitmap.recycle();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (callback != null) {
            callback.photoSaved(path, null);
        }
    }
}
