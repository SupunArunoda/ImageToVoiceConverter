
package com.yalantis.cameramodule.activity;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yalantis.cameramodule.R;
import com.yalantis.cameramodule.manager.ImageManager;

public abstract class BasePhotoActivity extends BaseActivity {

    protected String path;
    protected String name;
    protected Bitmap bitmap;

    protected View progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showActionBar();
        showBack();
        setContentView(R.layout.activity_photo);

        if (getIntent().hasExtra(EXTRAS.PATH)) {
            path = getIntent().getStringExtra(EXTRAS.PATH);
        } else {
            throw new RuntimeException("There is no path to image in extras");
        }
        if (getIntent().hasExtra(EXTRAS.NAME)) {
            name = getIntent().getStringExtra(EXTRAS.NAME);
        } else {
            throw new RuntimeException("There is no image name in extras");
        }

        progressBar = findViewById(R.id.progress);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bitmap == null || bitmap.isRecycled()) {
            loadPhoto();
        }
    }

    protected abstract void showPhoto(Bitmap bitmap);

    protected void rotatePhoto(float angle) {
        synchronized (bitmap) {
            bitmap = ImageManager.i.rotatePhoto(path, angle);
            showPhoto(bitmap);
        }
        setResult(EXTRAS.RESULT_EDITED, setIntentData());
    }

    protected void deletePhoto() {
        setResult(EXTRAS.RESULT_DELETED, setIntentData());
        finish();
    }

    protected void loadPhoto() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        ImageManager.i.loadPhoto(path, metrics.widthPixels, metrics.heightPixels, loadingTarget);
    }

    protected void setFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_content, fragment)
                .commit();
    }

    private Target loadingTarget = new Target() {

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            progressBar.setVisibility(View.GONE);
            BasePhotoActivity.this.bitmap = bitmap;
            showPhoto(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            progressBar.setVisibility(View.GONE);
            bitmap = null;
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            progressBar.setVisibility(View.VISIBLE);
        }

    };

    protected Intent setIntentData() {
        return setIntentData(null);
    }

    protected Intent setIntentData(Intent intent) {
        if (intent == null) {
            intent = new Intent();
        }
        intent.putExtra(EXTRAS.PATH, path);
        intent.putExtra(EXTRAS.NAME, name);
        return intent;
    }

    public static final class EXTRAS {

        public static final String PATH = "path";

        public static final String NAME = "name";

        public static final String FROM_CAMERA = "from_camera";

        public static final int REQUEST_PHOTO_EDIT = 7338;

        public static final int RESULT_EDITED = 338;

        public static final int RESULT_DELETED = 3583;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
