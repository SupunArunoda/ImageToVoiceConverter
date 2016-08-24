

package com.yalantis.cameramodule.interfaces;

import android.graphics.Bitmap;
import android.graphics.RectF;

public interface PhotoCroppedCallback {

    /**
     * @param width
     *            width before crop
     * @param height
     *            height before crop
     * @param croppedBitmap
     *            cropped bitmap
     * @param cropRect
     *            cropping rectangle
     */
    public void onPhotoCropped(int width, int height, Bitmap croppedBitmap, RectF cropRect);

}
