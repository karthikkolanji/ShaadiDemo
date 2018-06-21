package com.startedup.base.ui.shaadi;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.startedup.base.R;
import com.startedup.base.model.shaadi.ResultsItem;
import com.startedup.base.model.shaadi.ShaadiUserResponse;
import com.startedup.base.ui.base.BaseFragment;
import com.startedup.base.ui.base.BaseView;
import com.startedup.base.utils.AnimUtil;
import com.startedup.base.utils.ResourceFinder;
import java.lang.ref.WeakReference;
import java.util.List;
import timber.log.Timber;

public class UserFragment extends BaseFragment implements BaseView, UserAdapter.UserRemoveListener {

  // Views
  private View mView;
  @BindView(R.id.rv_users) RecyclerView rvUsers;
  //@BindView(R.id.in_loading) View inLoading;
  @BindView(R.id.pb_loading) ProgressBar pbLoading;
  @BindView(R.id.tv_message) TextView tvMessage;
  //@BindView(R.id.in_error) View inError;
  @BindView(R.id.iv_error) ImageView ivError;
  @BindView(R.id.tv_error) TextView tvError;
  Unbinder unbinder;

  // Presenter
  private UserPresenter mPresenter;

  // LayoutManager
  private LinearLayoutManager mLinearLayoutManager;

  // Data
  private List<ResultsItem> mList;
  private UserAdapter mUserAdapter;

  // values
  private String userCount = "200";

  public UserFragment() {
  }

  public static UserFragment newInstance() {
    UserFragment fragment = new UserFragment();
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mView = inflater.inflate(R.layout.fragment_user, container, false);
    unbinder = ButterKnife.bind(this, mView);
    return mView;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initViews();
    init();
  }

  private void init() {
    mPresenter = new UserPresenter(new WeakReference(this));
    mPresenter.getUsers(userCount);
  }

  private void initViews() {
    mLinearLayoutManager = new LinearLayoutManager(getActivity());
    rvUsers.setLayoutManager(mLinearLayoutManager);
  }

  @Override
  public void showLoading(boolean isDialog, String loadingMessage) {
    pbLoading.setVisibility(View.VISIBLE);
    tvMessage.setVisibility(View.VISIBLE);
    tvMessage.setText(loadingMessage);
  }

  @Override
  public void hideLoading() {
    pbLoading.setVisibility(View.GONE);
    tvMessage.setVisibility(View.GONE);
  }

  @Override public void showNetworkError(boolean isDialog, String errorMessage) {
    ivError.setVisibility(View.VISIBLE);
    tvError.setVisibility(View.VISIBLE);
    ivError.setImageResource(R.drawable.im_no_internet);
    tvError.setText(errorMessage);
  }

  @Override public void showUnknownError(boolean isDialog, String errorMessage) {
    ivError.setVisibility(View.VISIBLE);
    tvError.setVisibility(View.VISIBLE);
    ivError.setImageResource(R.drawable.im_something_went_wrong);
    tvError.setText(errorMessage);
  }

  @Override
  public void hideError() {
    ivError.setVisibility(View.GONE);
    tvError.setVisibility(View.GONE);
  }

  @Override
  public void onSuccess(Object object) {
    ShaadiUserResponse response = (ShaadiUserResponse) object;
    mList = response.getResults();
    mUserAdapter = new UserAdapter(mList, this);
    rvUsers.setAdapter(mUserAdapter);
    Timber.wtf("onSuccess");
  }

  @Override public void onUserRemoved(final int position) {
    RecyclerView.ViewHolder viewHolder = rvUsers.findViewHolderForLayoutPosition(position);
    AnimUtil.animateItemRemove(getActivity(), viewHolder.itemView);
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        mList.remove(position);
        mUserAdapter.notifyItemRemoved(position);
      }
    }, 300);
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

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({ R.id.iv_error, R.id.tv_error })
  public void onViewClicked(View view) {
    mPresenter.getUsers(userCount);
  }
}
