package com.startedup.base.ui.base;

public interface BaseView {
    void showLoading(boolean isDialog, String loadingMessage);

    void hideLoading();

    void showError(boolean isDialog, String errorMessage);

    void hideError();

    void onSuccess(Object object);
}
