package com.developersbreach.xyzreader.utils;

import android.animation.Animator;
import android.content.Context;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.developersbreach.xyzreader.R;
import com.google.android.material.card.MaterialCardView;

public class ArticleAnimations {

    public static void startBottomCircularEffect(View rootView) {

        // get the center for the clipping circle
        int cx = rootView.getLeft();
        int cy = rootView.getBottom();

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)
        Animator animator = ViewAnimationUtils.createCircularReveal(rootView, cx, cy, 0f,
                finalRadius);
        animator.start();
    }

    public static void startLinearAnimation(View view) {
        view.setTranslationY(800f);
        final ViewPropertyAnimator viewPropertyAnimator = view.animate();
        viewPropertyAnimator.translationY(0f);
        viewPropertyAnimator.setDuration(300L);
        viewPropertyAnimator.start();
    }

    public static Animation fadeOutCardAnimation(Context context, MaterialCardView cardView) {
        Animation animFadeOut = AnimationUtils.loadAnimation(context, R.anim.fragment_fade_exit);
        cardView.startAnimation(animFadeOut);
        return animFadeOut;
    }
}
