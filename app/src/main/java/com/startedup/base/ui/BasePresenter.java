package com.startedup.base.ui;

public interface BasePresenter<V extends BaseView> {
    void onAttach(V view);

    void onDetach();
}
