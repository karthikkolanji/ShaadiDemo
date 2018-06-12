package com.startedup.base.ui.base;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.startedup.base.R;
import com.startedup.base.broadcast.NetworkStateReceiver;
import com.startedup.base.utils.CommonUtil;
import com.startedup.base.utils.DialogUtils;
import com.startedup.base.utils.FragmentTransactionUtil;
import com.startedup.base.utils.ResourceFinder;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    private NetworkStateReceiver mNetworkStateReceiver;

    protected abstract void onPermissionAllGranted();

    protected abstract void onPermissionGranted();

    protected abstract void onNetworkOn();

    protected abstract void onNetworkOff();

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetworkState();
    }

    public void registerNetworkState() {
        mNetworkStateReceiver = new NetworkStateReceiver();
        mNetworkStateReceiver.addListener(this);
        this.registerReceiver(mNetworkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    protected void addFragment(@NonNull Fragment fragment, @IdRes int fragmentContainer, boolean addToBackStack) {
        FragmentTransactionUtil.addFragment(getSupportFragmentManager(), fragment, fragmentContainer, addToBackStack);
    }

    protected void replaceFragment(@NonNull Fragment fragment, @IdRes int fragmentContainer, boolean addToBackStack) {
        FragmentTransactionUtil.replaceFragment(getSupportFragmentManager(), fragment, fragmentContainer, addToBackStack);
    }

    protected void requestMultiplePermission(ArrayList<String> permissionList) {
        Dexter.withActivity(this)
                .withPermissions(permissionList)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showEnablePermissionDialog();

                        } else if (report.areAllPermissionsGranted()) {
                            onPermissionAllGranted();
                        } else {
                            CommonUtil.showToasLong(BaseActivity.this, ResourceFinder.getString(R.string.permission_denied));
                        }


                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                CommonUtil.showToasLong(BaseActivity.this, error.toString());
            }
        }).check();
    }

    protected void requestSinglePermission(String permission) {

        Dexter.withActivity(this)
                .withPermission(permission)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        BaseActivity.this.onPermissionGranted();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            showEnablePermissionDialog();
                        } else {
                            CommonUtil.showToasLong(BaseActivity.this, ResourceFinder.getString(R.string.permission_denied));
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                CommonUtil.showToasLong(BaseActivity.this, error.toString());
            }
        });
    }

    private void showEnablePermissionDialog() {
        builder = DialogUtils.getAlertDialog(BaseActivity.this, R.string.permission_title, R.string.permission_needed, true);
        AlertDialog dialog = null;
        builder.setPositiveButton("Open setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + BaseActivity.this.getPackageName()));
                startActivity(intent);
            }
        });
        final AlertDialog finalDialog = dialog;
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finalDialog.cancel();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void networkAvailable() {
        onNetworkOn();
    }

    @Override
    public void networkUnavailable() {
        onNetworkOff();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetworkStateReceiver);
    }
}
