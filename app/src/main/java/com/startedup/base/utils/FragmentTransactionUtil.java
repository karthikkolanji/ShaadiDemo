package com.startedup.base.utils;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by hrskrs on 7/14/2016.
 */
public final class FragmentTransactionUtil {

    public static void addFragment(FragmentManager fragmentManager,
                                   @NonNull Fragment fragment,
                                   @IdRes int fragmentContainerId,
                                   boolean addToBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragmentContainerId, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.commitAllowingStateLoss();
    }

    public static void replaceFragment(FragmentManager fragmentManager,
                                       @NonNull Fragment fragment,
                                       @IdRes int fragmentContainerId,
                                       boolean addToBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(fragmentContainerId, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.commitAllowingStateLoss();
    }
}
