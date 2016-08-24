
package com.yalantis.cameramodule;

import android.content.Context;

import com.yalantis.cameramodule.interfaces.Initializer;
import com.yalantis.cameramodule.manager.ImageManager;
import com.yalantis.cameramodule.manager.LoggerManager;
import com.yalantis.cameramodule.manager.SharedPrefManager;

public enum ManagerInitializer implements Initializer {
    i;

    @Override
    public void init(Context context) {
        SharedPrefManager.i.init(context);
        LoggerManager.i.init(context);
        ImageManager.i.init(context);
    }

    @Override
    public void clear() {
        SharedPrefManager.i.clear();
        LoggerManager.i.clear();
        ImageManager.i.clear();
    }

}
