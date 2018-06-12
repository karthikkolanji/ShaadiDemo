package com.startedup.base.ui.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

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
import com.startedup.base.utils.ResourceFinder;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFragment extends Fragment implements NetworkStateReceiver.NetworkStateReceiverListener {

    private BaseActivity mBaseActivity;
    private NetworkStateReceiver mNetworkStateReceiver;

    protected abstract void onPermissionAllGranted();

    protected abstract void onPermissionGranted();

    protected abstract void onNetworkOn();

    protected abstract void onNetworkOff();

    private AlertDialog.Builder builder;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBaseActivity = (BaseActivity) context;

    }

    @Override
    public void onResume() {
        super.onResume();
        registerNetworkState();
    }

    protected void addFragment(@NonNull Fragment fragment, @IdRes int fragmentContainer, boolean addToBackStack) {
        if (mBaseActivity != null) {
            mBaseActivity.addFragment(fragment, fragmentContainer, addToBackStack);
        }
    }

    protected void replaceFragment(@NonNull Fragment fragment, @IdRes int fragmentContainer, boolean addToBackStack) {
        if (mBaseActivity != null) {
            mBaseActivity.replaceFragment(fragment, fragmentContainer, addToBackStack);
        }
    }

    protected void requestMultiplePermission(ArrayList<String> permissionList) {
        if (mBaseActivity != null) {
            Dexter.withActivity(mBaseActivity)
                    .withPermissions(permissionList)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.isAnyPermissionPermanentlyDenied()) {
                                showEnablePermissionDialog();

                            } else if (report.areAllPermissionsGranted()) {
                                onPermissionAllGranted();
                            } else {
                                CommonUtil.showToasLong(mBaseActivity, ResourceFinder.getString(R.string.permission_denied));
                            }


                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).withErrorListener(new PermissionRequestErrorListener() {
                @Override
                public void onError(DexterError error) {
                    CommonUtil.showToasLong(mBaseActivity, error.toString());
                }
            }).check();
        }

    }

    protected void requestSinglePermission(String permission) {
        if (mBaseActivity != null) {
            Dexter.withActivity(mBaseActivity)
                    .withPermission(permission)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            mBaseActivity.onPermissionGranted();
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            if (response.isPermanentlyDenied()) {
                                showEnablePermissionDialog();
                            } else {
                                CommonUtil.showToasLong(mBaseActivity, ResourceFinder.getString(R.string.permission_denied));
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).withErrorListener(new PermissionRequestErrorListener() {
                @Override
                public void onError(DexterError error) {
                    CommonUtil.showToasLong(mBaseActivity, error.toString());
                }
            });
        }

    }

    private void showEnablePermissionDialog() {
        builder = DialogUtils.getAlertDialog(mBaseActivity, R.string.permission_title, R.string.permission_needed, true);
        AlertDialog dialog = null;
        builder.setPositiveButton("Open setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + mBaseActivity.getPackageName()));
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

    private void registerNetworkState() {
        if (mBaseActivity != null) {
            mNetworkStateReceiver = new NetworkStateReceiver();
            mNetworkStateReceiver.addListener(this);
            mBaseActivity.registerReceiver(mNetworkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        }
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
    public void onDestroy() {
        super.onDestroy();
        if (mBaseActivity != null) {
            mBaseActivity.unregisterReceiver(mNetworkStateReceiver);
        }
    }
}
