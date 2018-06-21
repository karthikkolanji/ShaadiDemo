package com.startedup.base.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.startedup.base.R;

public class AnimUtil {
  public static void animateItemRemove(Context context,View itemView){
    Animation animation= AnimationUtils.loadAnimation(context, R.anim.item_to_left);
    itemView.startAnimation(animation);
  }
}
