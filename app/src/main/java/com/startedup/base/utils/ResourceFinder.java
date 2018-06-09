package com.startedup.base.utils;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.startedup.base.ui.App;

public class ResourceFinder {

    public static String getString(int stringId) {
        return App.getContext().getString(stringId);
    }

    public int getColour(int colorId) {
        return ContextCompat.getColor(App.getContext(), colorId);
    }

    public Drawable getDrawable(int drawableId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return App.getContext().getResources().getDrawable(drawableId, null);
        } else {
            return App.getContext().getResources().getDrawable(drawableId);
        }
    }

    public int getDimension(int dimensionId) {
        return (int) App.getContext().getResources().getDimension(dimensionId);
    }

}
