package com.developersbreach.xyzreader.utils;

import android.animation.Animator;
import android.view.View;
import android.view.ViewAnimationUtils;

public class CircularEffect {

    public static void startBottomCircularEffect(View rootView) {

        // get the center for the clipping circle
        int cx = rootView.getLeft();
        int cy = rootView.getBottom();

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(rootView, cx, cy, 0f, finalRadius);
        anim.start();
    }
}
