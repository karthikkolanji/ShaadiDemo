package com.startedup.base.ui.imdb;

import android.annotation.SuppressLint;

import com.startedup.base.api.ApiService;
import com.startedup.base.constants.ConfigConstant;
import com.startedup.base.retrofit.RetrofitCallbackWrapper;
import com.startedup.base.retrofit.RetrofitClient;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TopRatedMoviePresenter {


    private WeakReference<TopRatedMovieFragment> view;

    public TopRatedMoviePresenter(WeakReference<TopRatedMovieFragment> fragment) {
        view = fragment;
    }

    @SuppressLint("CheckResult")
    public void getTopRatedMovies() {

        Observable observable = RetrofitClient.getRetrofit().create(ApiService.class).getTopRatedMovies(ConfigConstant.IMDB_KEY);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RetrofitCallbackWrapper(view.get(), true) {
                });

    }


}
