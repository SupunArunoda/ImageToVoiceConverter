
package com.yalantis.cameramodule.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edmodo.cropper.CropImageView;
import com.yalantis.cameramodule.R;
import com.yalantis.cameramodule.interfaces.PhotoCroppedCallback;

public class PhotoCropFragment extends BaseFragment {

    private Bitmap bitmap;
    private CropImageView cropView;

    private PhotoCroppedCallback callback;

    public static PhotoCropFragment newInstance(Bitmap bitmap) {
        PhotoCropFragment fragment = new PhotoCropFragment();
        fragment.bitmap = bitmap;

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_crop, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cropView = (CropImageView) view.findViewById(R.id.photo);
        cropView.setImageBitmap(bitmap);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof PhotoCroppedCallback) {
            callback = (PhotoCroppedCallback) activity;
        } else {
            throw new RuntimeException(activity.getClass().getName() + " must implement " + PhotoCroppedCallback.class.getName());
        }
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        cropView.setImageBitmap(bitmap);
    }

    public void applyCrop() {
        callback.onPhotoCropped(bitmap.getWidth(), bitmap.getHeight(), cropView.getCroppedImage(), cropView.getActualCropRect());
    }

}
