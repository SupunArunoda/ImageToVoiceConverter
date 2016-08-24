

package com.yalantis.cameramodule.manager;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

import com.yalantis.cameramodule.interfaces.Initializer;
import com.yalantis.cameramodule.model.CachedValue;

public enum SharedPrefManager implements Initializer {
    i;

    private static final String NAME = "sharedPrefs";

    public static final String OPEN_PHOTO_PREVIEW = "open_photo_preview";
    public static final String CAMERA_RATIO = "camera_ratio";
    public static final String CAMERA_QUALITY = "camera_quality";
    public static final String CAMERA_FLASH_MODE = "camera_flash_mode";
    public static final String CAMERA_HDR_MODE = "camera_hdr_mode";
    public static final String CAMERA_FOCUS_MODE = "camera_focus_mode";
    public static final String USE_FRONT_CAMERA = "use_front_camera";

    private static SharedPreferences sp;

    private Set<CachedValue> cachedValues;

    private CachedValue<Boolean> openPhotoPreview;
    private CachedValue<Integer> cameraRatio;
    private CachedValue<Integer> cameraQuality;
    private CachedValue<Integer> cameraFlashMode;
    private CachedValue<Integer> isCameraHDRMode;
    private CachedValue<Integer> cameraFocusMode;
    private CachedValue<Boolean> useFrontCamera;

    @Override
    public void init(Context context) {
        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        CachedValue.initialize(sp);
        cachedValues = new HashSet<>();
        cachedValues.add(openPhotoPreview = new CachedValue<>(OPEN_PHOTO_PREVIEW, true, Boolean.class));
        cachedValues.add(isCameraHDRMode = new CachedValue<>(CAMERA_HDR_MODE, 0, Integer.class));
        cachedValues.add(cameraRatio = new CachedValue<>(CAMERA_RATIO, 0, Integer.class));
        cachedValues.add(cameraQuality = new CachedValue<>(CAMERA_QUALITY, 0, Integer.class));
        cachedValues.add(cameraFlashMode = new CachedValue<>(CAMERA_FLASH_MODE, 0, Integer.class));
        cachedValues.add(cameraFocusMode = new CachedValue<>(CAMERA_FOCUS_MODE, 0, Integer.class));
        cachedValues.add(useFrontCamera = new CachedValue<>(USE_FRONT_CAMERA, false, Boolean.class));
    }

    public void setHDRMode(int isHDR) {
        this.isCameraHDRMode.setValue(isHDR);
    }

    public int isHDR() {
        return isCameraHDRMode.getValue();
    }

    public boolean isOpenPhotoPreview() {
        return openPhotoPreview.getValue();
    }

    public void setOpenPhotoPreview(boolean enabled) {
        this.openPhotoPreview.setValue(enabled);
    }

    public int getCameraRatio() {
        return cameraRatio.getValue();
    }

    public void setCameraRatio(int cameraRatio) {
        this.cameraRatio.setValue(cameraRatio);
    }

    public int getCameraQuality() {
        return cameraQuality.getValue();
    }

    public void setCameraQuality(int cameraQuality) {
        this.cameraQuality.setValue(cameraQuality);
    }

    public int getCameraFlashMode() {
        return cameraFlashMode.getValue();
    }

    public void setCameraFlashMode(int cameraFlashMode) {
        this.cameraFlashMode.setValue(cameraFlashMode);
    }

    public int getCameraFocusMode() {
        return cameraFocusMode.getValue();
    }

    public void setCameraFocusMode(int cameraFocusMode) {
        this.cameraFocusMode.setValue(cameraFocusMode);
    }

    public boolean useFrontCamera() {
        return useFrontCamera.getValue();
    }

    public void setUseFrontCamera(boolean frontCamera) {
        this.useFrontCamera.setValue(frontCamera);
    }

    @Override
    public void clear() {
        for (CachedValue value : cachedValues) {
            value.clear();
        }
        sp.edit().clear().commit();
    }

}
