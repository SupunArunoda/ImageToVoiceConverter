
package com.yalantis.cameramodule.interfaces;

import android.graphics.Bitmap;

import com.yalantis.cameramodule.util.ManagedTarget;

public interface StorageCallback {

    public void setBitmap(String path, Bitmap bitmap);

    public void addTarget(ManagedTarget target);

    public void removeTarget(ManagedTarget target);

}
