package com.startedup.base.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Window;


/**
 * Created by hrskrs on 7/16/2016.
 */
public final class DialogUtils {


    public static ProgressDialog showLoading(Context context) {
        return showLoading(context, "", false);
    }

    public static ProgressDialog showLoading(Context context, boolean isCancelable) {
        return showLoading(context, "", isCancelable);
    }

    public static ProgressDialog showLoading(Context context, String message) {
        return showLoading(context, message, false);
    }

    public static ProgressDialog showLoading(Context context, @StringRes int message) {
        return showLoading(context, context.getString(message), false);
    }

    public static ProgressDialog showLoading(Context context,
                                             @StringRes int message,
                                             boolean isCancelable) {
        return showLoading(context, context.getString(message), isCancelable);
    }

    @SuppressLint("InflateParams")
    private static ProgressDialog showLoading(Context context,
                                              String message,
                                              boolean isCancelable) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        Window window = progressDialog.getWindow();
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(isCancelable);
        progressDialog.setCanceledOnTouchOutside(isCancelable);
        return progressDialog;
    }

    public static AlertDialog.Builder getAlertDialog(Context context,
                                                     String message) {
        return getAlertDialog(context, "", message, false);
    }

    public static AlertDialog.Builder getAlertDialog(Context context,
                                                     @StringRes int message) {
        return getAlertDialog(context, "", context.getString(message), false);
    }

    public static AlertDialog.Builder getAlertDialog(Context context,
                                                     String message,
                                                     boolean isCancelable) {
        return getAlertDialog(context, "", message, isCancelable);
    }

    public static AlertDialog.Builder getAlertDialog(Context context,
                                                     @StringRes int message,
                                                     boolean isCancelable) {
        return getAlertDialog(context, "", context.getString(message), isCancelable);
    }

    public static AlertDialog.Builder getAlertDialog(Context context,
                                                     @StringRes int title,
                                                     @StringRes int message,
                                                     boolean isCancelable) {
        return getAlertDialog(context,
                context.getString(title),
                context.getString(message),
                isCancelable);
    }

    private static AlertDialog.Builder getAlertDialog(Context context,
                                                      String title,
                                                      String message,
                                                      boolean isCancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setMessage(message);
        builder.setCancelable(isCancelable);
        return builder;
    }

}
