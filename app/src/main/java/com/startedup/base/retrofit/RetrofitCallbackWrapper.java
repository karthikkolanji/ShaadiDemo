package com.startedup.base.retrofit;

import com.startedup.base.ui.BaseResponse;
import com.startedup.base.ui.BaseView;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

// source : https://medium.com/mindorks/rxjava2-and-retrofit2-error-handling-on-a-single-place-8daf720d42d6

public abstract class RetrofitCallbackWrapper<T extends BaseResponse> extends DisposableObserver<T> {

    //BaseView is just a reference of a View in MVP
    private WeakReference<BaseView> weakReference;

    public RetrofitCallbackWrapper(BaseView view) {
        this.weakReference = new WeakReference<>(view);
    }

    protected abstract void onSuccess(T t);

    @Override
    public void onNext(T t) {
        //You can return StatusCodes of different cases from your API and handle it here. I usually include these cases on BaseResponse and iherit it from every Response
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        BaseView view = weakReference.get();
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            view.showError(getErrorMessage(responseBody));
        } else if (e instanceof SocketTimeoutException) {
            view.showError("");
        } else if (e instanceof IOException) {
            view.showError("");
        } else {
            view.showError(e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }


    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}