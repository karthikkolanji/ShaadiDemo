package com.startedup.base.ui;

public interface BaseView {
    void showLoading(String loadingMessage);

    void hideLoading();

    void showError(String errorMessage);

    void hideError();
}
