

package com.yalantis.cameramodule.interfaces;

public interface PhotoActionsCallback {

    public void onOpenPhotoPreview(String path, String name);

    public void onAddNote(int zpid, String name);

    public void onRetake(String name);

    public void onDeletePhoto(String name);

    public void onDeleteHomePhotos(int zpid);

    public void onDeleteAddressPhotos(String address);

}
