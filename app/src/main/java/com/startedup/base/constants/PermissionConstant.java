package com.startedup.base.constants;

import android.Manifest;

public class PermissionConstant {

    // Permission types
    public static final int TYPE_LOCATION = 100;
    public static final int TYPE_STORAGE = 101;
    public static final int TYPE_CALL = 102;

    // Permissions
    public static final String STORAGE_PERMISSION[] = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final String LOCATION_PERMISSION[] = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};


    // Permission results
    public static final int ALL_GRANTED = 0;
    public static final int GRANTED = 1;
    public static final int SHOW_RATIONALE = 2;
    public static final int DENIED = 3;
    public static final int ALWAYS_DENIED = 4;

}
