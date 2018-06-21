package com.startedup.base.ui.shaadi;

import android.annotation.SuppressLint;
import com.startedup.base.api.ApiService;
import com.startedup.base.retrofit.RetrofitCallbackWrapper;
import com.startedup.base.retrofit.RetrofitClient;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.lang.ref.WeakReference;

public class UserPresenter {

  private WeakReference<UserFragment> view;

  public UserPresenter(WeakReference<UserFragment> fragment) {
    view = fragment;
  }

  @SuppressLint("CheckResult")
  public void getUsers(String userCount) {

    Observable observable =
        RetrofitClient.getRetrofit().create(ApiService.class).getShaadiUsers(userCount);

    observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new RetrofitCallbackWrapper(view.get(), true) {
        });
  }
}
