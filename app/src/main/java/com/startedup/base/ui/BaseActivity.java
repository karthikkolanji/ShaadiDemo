package com.startedup.base.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.startedup.base.constants.IntentExtraConstant;
import com.startedup.base.constants.PermissionConstant;
import com.startedup.base.utils.FragmentTransactionUtil;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void addFragment(@NonNull Fragment fragment, @IdRes int fragmentContainer, boolean addToBackStack) {
        FragmentTransactionUtil.addFragment(getSupportFragmentManager(), fragment, fragmentContainer, addToBackStack);
    }

    protected void replaceFragment(@NonNull Fragment fragment, @IdRes int fragmentContainer, boolean addToBackStack) {
        FragmentTransactionUtil.replaceFragment(getSupportFragmentManager(), fragment, fragmentContainer, addToBackStack);
    }

    protected void requestMultiplePermission(final int permissionType, ArrayList<String> permissionList) {

        final Intent persmissionResultIntent = new Intent();

        Dexter.withActivity(this)
                .withPermissions(permissionList)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            persmissionResultIntent.putExtra(IntentExtraConstant.PERMISSION_TYPE, permissionType);
                            persmissionResultIntent.putExtra(IntentExtraConstant.PERMISSION_RESULT, PermissionConstant.ALWAYS_DENIED);
                        } else if (report.areAllPermissionsGranted()) {
                            persmissionResultIntent.putExtra(IntentExtraConstant.PERMISSION_TYPE, permissionType);
                            persmissionResultIntent.putExtra(IntentExtraConstant.PERMISSION_RESULT, PermissionConstant.ALL_GRANTED);
                        } else {
                            persmissionResultIntent.putExtra(IntentExtraConstant.PERMISSION_TYPE, permissionType);
                            persmissionResultIntent.putExtra(IntentExtraConstant.PERMISSION_RESULT, PermissionConstant.DENIED);
                        }
                        LocalBroadcastManager.getInstance(BaseActivity.this).sendBroadcast(persmissionResultIntent);

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        persmissionResultIntent.putExtra(IntentExtraConstant.PERMISSION_TYPE, permissionType);
                        persmissionResultIntent.putExtra(IntentExtraConstant.PERMISSION_RESULT, PermissionConstant.SHOW_RATIONALE);
                        LocalBroadcastManager.getInstance(BaseActivity.this).sendBroadcast(persmissionResultIntent);
                    }
                });
    }

    protected void requestSinglePermission(final int permissionType, String permission) {

        final Intent persmissionResultIntent = new Intent();

        Dexter.withActivity(this)
                .withPermission(permission)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        persmissionResultIntent.putExtra(IntentExtraConstant.PERMISSION_TYPE, permissionType);
                        persmissionResultIntent.putExtra(IntentExtraConstant.PERMISSION_RESULT, PermissionConstant.ALL_GRANTED);
                        LocalBroadcastManager.getInstance(BaseActivity.this).sendBroadcast(persmissionResultIntent);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        persmissionResultIntent.putExtra(IntentExtraConstant.PERMISSION_TYPE, permissionType);
                        persmissionResultIntent.putExtra(IntentExtraConstant.PERMISSION_RESULT, PermissionConstant.DENIED);
                        LocalBroadcastManager.getInstance(BaseActivity.this).sendBroadcast(persmissionResultIntent);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        persmissionResultIntent.putExtra(IntentExtraConstant.PERMISSION_TYPE, permissionType);
                        persmissionResultIntent.putExtra(IntentExtraConstant.PERMISSION_RESULT, PermissionConstant.SHOW_RATIONALE);
                        LocalBroadcastManager.getInstance(BaseActivity.this).sendBroadcast(persmissionResultIntent);
                    }
                });
    }
}
