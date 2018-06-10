package com.startedup.base.ui.base;

public interface BasePresenter<V extends BaseView> {
    void onAttach(V view);

    void onDetach();
}
