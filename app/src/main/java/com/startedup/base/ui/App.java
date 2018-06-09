package com.startedup.base.ui;

import android.app.Application;
import android.content.Context;

import com.startedup.base.utils.AppExceptionHandler;

import timber.log.Timber;

public class App extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Timber.plant(new Timber.DebugTree());
        setupCrashHandler();
    }

    public static Context getContext() {
        return mContext;
    }

    private void setupCrashHandler() {
        // 1. Get the system handler.
        Thread.UncaughtExceptionHandler systemHandler = Thread.getDefaultUncaughtExceptionHandler();

        // 2. Set the default handler as a dummy (so that crashlytics fallbacks to this one, once set)
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                /* do nothing */
            }
        });

        // 3. Setup crashlytics so that it becomes the default handler (and fallbacking to our dummy handler)
        //Fabric.with(this, Crashlytics())

        Thread.UncaughtExceptionHandler fabricExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

        // 4. Setup our handler, which tries to restart the app.
        Thread.setDefaultUncaughtExceptionHandler(new AppExceptionHandler(systemHandler, fabricExceptionHandler, this));
    }

}
