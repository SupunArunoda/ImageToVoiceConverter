
package com.yalantis.cameramodule.activity;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.yalantis.cameramodule.R;
import com.yalantis.cameramodule.fragment.PhotoCropFragment;
import com.yalantis.cameramodule.interfaces.PhotoCroppedCallback;
import com.yalantis.cameramodule.interfaces.PhotoSavedListener;
import com.yalantis.cameramodule.manager.ImageManager;

public class PhotoCropActivity extends BasePhotoActivity implements PhotoCroppedCallback {

    private PhotoCropFragment cropFragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.photo_crop_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void apply(MenuItem ite) {
        cropFragment.applyCrop();
    }

    public void cancel(MenuItem ite) {
        finish();
    }

    @Override
    protected void showPhoto(Bitmap bitmap) {
        if (cropFragment == null) {
            cropFragment = PhotoCropFragment.newInstance(bitmap);
            setFragment(cropFragment);
        } else {
            cropFragment.setBitmap(bitmap);
        }
    }

    @Override
    public void onPhotoCropped(int width, int height, Bitmap croppedBitmap, RectF cropRect) {
        ImageManager.i.cropBitmap(path, width, height, croppedBitmap, cropRect, new PhotoSavedListener() {

            @Override
            public void photoSaved(String path, String name) {
                setResult(EXTRAS.RESULT_EDITED, setIntentData());
                finish();
            }
        });
    }

}
