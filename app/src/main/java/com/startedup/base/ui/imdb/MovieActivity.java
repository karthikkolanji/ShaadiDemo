package com.startedup.base.ui.imdb;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.startedup.base.R;
import com.startedup.base.listner.FragmentCommunicationListner;
import com.startedup.base.ui.base.BaseActivity;
import com.startedup.base.utils.CommonUtil;
import com.startedup.base.utils.ResourceFinder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MovieActivity extends BaseActivity implements FragmentCommunicationListner {


    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        replaceFragment(new TopRatedMovieFragment(), R.id.fragment_container, true);
    }


    @Override
    public void onFragmentCommunicated(Object object) {

    }

    @Override
    protected void onPermissionAllGranted() {
        CommonUtil.showToasLong(this, ResourceFinder.getString(R.string.permission_granted));
    }

    @Override
    protected void onPermissionGranted() {
        CommonUtil.showToasLong(this, ResourceFinder.getString(R.string.permission_granted));
    }

    @Override
    protected void onNetworkOn() {
        CommonUtil.showToasShort(this, "Network ON");
    }

    @Override
    protected void onNetworkOff() {
        CommonUtil.showToasShort(this, "Network OFF");
    }

    @OnClick(R.id.fragment_container)
    public void onFragmentContainerClicked() {
        ArrayList<String> permissionArray = new ArrayList<>();
        permissionArray.add(Manifest.permission.RECORD_AUDIO);
        permissionArray.add(Manifest.permission.CAMERA);
        permissionArray.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        requestMultiplePermission(permissionArray);
    }
}
