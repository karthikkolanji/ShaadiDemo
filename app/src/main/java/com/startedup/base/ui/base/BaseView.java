package com.startedup.base.ui.base;

public interface BaseView {
    void showLoading(boolean isDialog, String loadingMessage);

    void hideLoading();

    void showNetworkError(boolean isDialog, String errorMessage);
    void showUnknownError(boolean isDialog, String errorMessage);

    void hideError();

    void onSuccess(Object object);
}
