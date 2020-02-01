package kz.maltabu.app.maltabukz.helpers;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

public  class CustomAnimator {
    public static void animateViewBound(View refresh){
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.8f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.8f, 1f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(refresh, scaleX, scaleY);
        objectAnimator.setInterpolator(new BounceInterpolator());
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
    }
    public static void animateHotViewLinear(View refresh){
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.95f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.95f, 1f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(refresh, scaleX, scaleY);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
    }

    public static void animateUpsideDown(View view){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", 0f, 180f);
        objectAnimator.setDuration(300);
        objectAnimator.start();
    }

    public static void animateDownsideUp(View view){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", 180f, 0f);
        objectAnimator.setDuration(300);
        objectAnimator.start();
    }
}
