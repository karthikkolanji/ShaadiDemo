package com.startedup.base.utils;

import android.support.v7.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.startedup.base.constants.PermissionConstant;
import com.startedup.base.listner.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {

    public static void requestMultiplePermission(AppCompatActivity appCompatActivity,
                                             ArrayList<String> permissionList,
                                             final PermissionListener permissionListener) {


        Dexter.withActivity(appCompatActivity)
                .withPermissions(permissionList)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            permissionListener.onPermissionResult(PermissionConstant.ALWAYS_DENIED);
                        } else if (report.areAllPermissionsGranted()) {
                            permissionListener.onPermissionResult(PermissionConstant.ALL_GRANTED);
                        } else {
                            permissionListener.onPermissionResult(PermissionConstant.DENIED);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        permissionListener.onPermissionResult(PermissionConstant.SHOW_RATIONALE);
                    }
                });
    }


    public static void requestSinglePermission(AppCompatActivity appCompatActivity, String permission,
                                           final PermissionListener permissionListener) {

        Dexter.withActivity(appCompatActivity)
                .withPermission(permission)
                .withListener(new com.karumi.dexter.listener.single.PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        permissionListener.onPermissionResult(PermissionConstant.GRANTED);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        permissionListener.onPermissionResult(PermissionConstant.DENIED);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        permissionListener.onPermissionResult(PermissionConstant.SHOW_RATIONALE);
                    }
                });
    }
}
