
package com.yalantis.cameramodule.manager;

import timber.log.Timber;
import android.content.Context;

import com.yalantis.cameramodule.CameraConst;
import com.yalantis.cameramodule.interfaces.Initializer;

public enum LoggerManager implements Initializer {
    i;

    private Context context;

    @Override
    public void init(Context context) {
        this.context = context;
        if (CameraConst.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends Timber.DebugTree {

        @Override
        public void d(String message, Object... args) {
        }

        @Override
        public void d(Throwable t, String message, Object... args) {
        }

        @Override
        public void i(String message, Object... args) {
        }

        @Override
        public void i(Throwable t, String message, Object... args) {
        }

        @Override
        public void w(String message, Object... args) {
        }

        @Override
        public void w(Throwable t, String message, Object... args) {
        }

        @Override
        public void e(String message, Object... args) {
            super.e(message, args);
        }

        @Override
        public void e(Throwable t, String message, Object... args) {
            super.e(t, message, args);
        }
    }

    @Override
    public void clear() {
    }

}
