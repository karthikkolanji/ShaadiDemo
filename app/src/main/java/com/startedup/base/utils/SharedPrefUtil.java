package com.startedup.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.startedup.base.ui.App;

/**
 * Created by karthik on 09/6/18.
 */

public class SharedPrefUtil {

    private static String SHARED_PREF_FILE_NAME = "BaseApp";
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;

    private static int DEFAULT_INT = 0;
    private static String DEFAULT_STRING = "";
    private static boolean DEFAULT_BOOLEAN = false;
    private static long DEFAULT_LONG = 0;


    public static SharedPreferences getInstance() {
        if (mSharedPreferences == null) {
            mSharedPreferences = App.getContext().getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
            mEditor = mSharedPreferences.edit();
        }
        return mSharedPreferences;

    }

    // ------------------------------------------ int ---------------------------------------------------------------------
    public static void put(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.apply();
    }

    public static int getInt(String key) {
        return getInstance().getInt(key, DEFAULT_INT);
    }


    // ------------------------------------------ String ---------------------------------------------------------------------
    public static void put(String key, String value) {
        mEditor.putString(key, value);
        mEditor.apply();
    }

    public static String getString(String key) {
        return getInstance().getString(key, DEFAULT_STRING);
    }

    // ------------------------------------------ boolean ---------------------------------------------------------------------
    public static void put(String key, boolean value) {

        mEditor.putBoolean(key, value);
        mEditor.apply();
    }

    public static boolean getBoolean(String key) {
        return getInstance().getBoolean(key, DEFAULT_BOOLEAN);
    }


    // ------------------------------------------ long ---------------------------------------------------------------------
    public static void put(String key, long value) {

        mEditor.putLong(key, value);
        mEditor.apply();
    }

    public static long getLong(String key) {
        return getInstance().getLong(key, DEFAULT_LONG);
    }

    // ------------------------------------------ clearing keys ---------------------------------------------------------------------
    public static void remove(String key) {

        mEditor.remove(key);
        mEditor.apply();
    }

    public static void clearAll() {
        mEditor.clear();
        mEditor.apply();
    }

}

