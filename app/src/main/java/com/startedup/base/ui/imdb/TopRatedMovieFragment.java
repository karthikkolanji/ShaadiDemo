package com.startedup.base.ui.imdb;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.startedup.base.R;
import com.startedup.base.model.movies.TopRatedMovieResponse;
import com.startedup.base.ui.base.BaseFragment;
import com.startedup.base.ui.base.BaseView;
import com.startedup.base.utils.CommonUtil;
import com.startedup.base.utils.ResourceFinder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

public class TopRatedMovieFragment extends BaseFragment implements BaseView {

    @BindView(R.id.rv_top_rated_movies)
    RecyclerView rvTopRatedMovies;
    Unbinder unbinder;
    @BindView(R.id.bt_load)
    Button btLoad;
    // Views
    private View mView;

    private ProgressDialog progressDialog;

    // Presenter
    private TopRatedMoviePresenter mPresenter;

    // LayoutManager
    private LinearLayoutManager mLinearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_top_rated_movie, container, false);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new TopRatedMoviePresenter(new WeakReference(this));
        initViews();
    }

    private void initViews() {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        rvTopRatedMovies.setLayoutManager(mLinearLayoutManager);
    }


    @Override
    public void showLoading(boolean isDialog, String loadingMessage) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(loadingMessage);
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showError(boolean isDialog, String errorMessage) {
        CommonUtil.showToasLong(getActivity(), errorMessage);
    }

    @Override
    public void hideError() {

    }

    @Override
    public void onSuccess(Object object) {
        TopRatedMovieResponse response = (TopRatedMovieResponse) object;
        TopRatedMovieAdapter movieAdapter = new TopRatedMovieAdapter(response.getResults());
        rvTopRatedMovies.setAdapter(movieAdapter);
        Timber.wtf("onSuccess");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_load)
    public void onLoadClicked() {
        //mPresenter.getTopRatedMovies();
        ArrayList<String> permissionArray = new ArrayList<>();
        permissionArray.add(Manifest.permission.RECORD_AUDIO);
        permissionArray.add(Manifest.permission.CAMERA);
        permissionArray.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        requestMultiplePermission(permissionArray);
    }

    @Override
    protected void onPermissionAllGranted() {
        CommonUtil.showToasLong(getActivity(), ResourceFinder.getString(R.string.permission_granted));
    }

    @Override
    protected void onPermissionGranted() {
        CommonUtil.showToasLong(getActivity(), ResourceFinder.getString(R.string.permission_granted));
    }

    @Override
    protected void onNetworkOn() {

    }

    @Override
    protected void onNetworkOff() {

    }
}
