
package com.yalantis.cameramodule.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import timber.log.Timber;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Environment;

import com.yalantis.cameramodule.CameraConst;
import com.yalantis.cameramodule.interfaces.PhotoSavedListener;

public class SavingPhotoTask extends AsyncTask<Void, Void, File> {

    private byte[] data;
    private String name;
    private String path;
    private int orientation;
    private PhotoSavedListener callback;

    public SavingPhotoTask(byte[] data, String name, String path, int orientation) {
        this(data, name, path, orientation, null);
    }

    public SavingPhotoTask(byte[] data, String name, String path, int orientation, PhotoSavedListener callback) {
        this.data = data;
        this.name = name;
        this.path = path;
        this.orientation = orientation;
        this.callback = callback;
    }

    @Override
    protected File doInBackground(Void... params) {
        File photo = getOutputMediaFile();
        if (photo == null) {
            Timber.e("Error creating media file, check storage permissions");
            return null;
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(photo);
            if (orientation == ExifInterface.ORIENTATION_UNDEFINED) {
                saveByteArray(fos, data);
            } else {
                saveByteArrayWithOrientation(fos, data, orientation);
            }

        } catch (FileNotFoundException e) {
            Timber.e(e, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Timber.e(e, "File write failure: " + e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                Timber.e(e, e.getMessage());
            }
        }

        return photo;
    }

    private void saveByteArray(FileOutputStream fos, byte[] data) throws IOException {
        long time = System.currentTimeMillis();
        fos.write(data);
        Timber.d("saveByteArray: %1dms", System.currentTimeMillis() - time);
    }

    private void saveByteArrayWithOrientation(FileOutputStream fos, byte[] data, int orientation) {
        long totalTime = System.currentTimeMillis();
        long time = System.currentTimeMillis();

        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Timber.d("decodeByteArray: %1dms", System.currentTimeMillis() - time);

        time = System.currentTimeMillis();
        if (orientation != 0 && bitmap.getWidth() > bitmap.getHeight()) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            Timber.d("createBitmap: %1dms", System.currentTimeMillis() - time);
        }
        time = System.currentTimeMillis();
        bitmap.compress(Bitmap.CompressFormat.JPEG, CameraConst.COMPRESS_QUALITY, fos);
        Timber.d("compress: %1dms", System.currentTimeMillis() - time);

        bitmap.recycle();

        Timber.d("saveByteArrayWithOrientation: %1dms", System.currentTimeMillis() - totalTime);
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        photoSaved(file);
    }

    private void photoSaved(File photo) {
        if (photo != null) {
            if (callback != null) {
                callback.photoSaved(photo.getPath(), photo.getName());
            }
        }
    }

    /**
     * Create a File for saving an image
     */
    private File getOutputMediaFile() {
        // To be safe, we should check that the SDCard is mounted
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Timber.e("External storage " + Environment.getExternalStorageState());
            return null;
        }

        File dir = new File(path);
        // Create the storage directory if it doesn't exist
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Timber.e("Failed to create directory");
                return null;
            }
        }

        return new File(dir.getPath() + File.separator + name);
    }

}
