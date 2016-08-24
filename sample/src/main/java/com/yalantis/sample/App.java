

package com.yalantis.sample;

import android.app.Application;
import com.yalantis.cameramodule.ManagerInitializer;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ManagerInitializer.i.init(getApplicationContext());
    }

}
