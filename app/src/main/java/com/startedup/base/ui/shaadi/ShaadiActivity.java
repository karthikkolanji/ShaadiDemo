package com.startedup.base.ui.shaadi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.startedup.base.R;
import com.startedup.base.listner.FragmentCommunicationListner;
import com.startedup.base.ui.base.BaseActivity;
import com.startedup.base.utils.ResourceFinder;

public class ShaadiActivity extends BaseActivity implements FragmentCommunicationListner {

  @BindView(R.id.fragment_container)
  FrameLayout fragmentContainer;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shaadi);
    ButterKnife.bind(this);
    replaceFragment(UserFragment.newInstance(), R.id.fragment_container, false);
  }

  @Override
  public void onFragmentCommunicated(Object object) {

  }

  @Override
  protected void onPermissionAllGranted() {
    showToastShort(ResourceFinder.getString(R.string.permission_granted));
  }

  @Override
  protected void onPermissionGranted() {
    showToastShort(ResourceFinder.getString(R.string.permission_granted));
  }

  @Override
  protected void onNetworkOn() {

  }

  @Override
  protected void onNetworkOff() {

  }
}
