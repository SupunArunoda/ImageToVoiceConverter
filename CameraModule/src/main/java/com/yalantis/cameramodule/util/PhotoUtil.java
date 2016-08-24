

package com.yalantis.cameramodule.util;

import java.io.File;

public class PhotoUtil {

    public static void deletePhoto(String path) {
        File file = new File(path);
        file.delete();
    }

}
