package com.developersbreach.xyzreader.utils;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.bindingAdapter.FavoriteListBindingAdapter;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;
import com.developersbreach.xyzreader.view.MainActivity;
import com.developersbreach.xyzreader.view.favorite.ArticleFavoritesFragment;
import com.developersbreach.xyzreader.view.list.ArticleListFragment;
import com.developersbreach.xyzreader.view.search.SearchArticleFragment;
import com.developersbreach.xyzreader.viewModel.ArticleFavoritesViewModel;
import com.google.android.material.card.MaterialCardView;


/**
 * Simple animations for app at one place.
 */
public class ArticleAnimations {

    /**
     * @param rootView pass any view to this method for effect. This will create a circular reveal
     *                 effect for view from left side and bottom of the screen.
     * @see MainActivity for implementation.
     */
    public static void startBottomCircularEffect(View rootView) {

        // get the left bottom corner for the clipping circle
        int cx = rootView.getLeft();
        int cy = rootView.getBottom();
        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);
        // create the animator for this view (the start radius is zero)
        Animator animator = ViewAnimationUtils.createCircularReveal(rootView, cx, cy, 0f,
                finalRadius);
        // Starts the animation.
        animator.start();
    }

    /**
     * @param view needs to be called with a view to apply animation.
     * @see ArticleListFragment#onResume()
     * @see ArticleFavoritesFragment#onResume()
     * @see SearchArticleFragment#onResume() for implementations.
     * <p>
     * This creates a linear transform of views. In our case items and cardViews inside RecyclerView
     * which moves from bottom to top with properties assigned to view.
     */
    public static void startLinearAnimation(View view) {
        // Set the vertical location to begin with.
        view.setTranslationY(800f);
        // Call this object with an view which needs animation.
        final ViewPropertyAnimator viewPropertyAnimator = view.animate();
        // This causes to animate the present view with given properties by cancelling existing.
        viewPropertyAnimator.translationY(0f);
        // Set duration for animation to take place on view.
        viewPropertyAnimator.setDuration(300L);
        // Starts the animation.
        viewPropertyAnimator.start();
    }

    /**
     * @param context  gets access for resources.
     * @param cardView this is the view which we are animating to.
     * @return returns the view with type of animation assigned to perform with.
     * @see FavoriteListBindingAdapter#bindDeleteFavoriteClickListener(ImageView, FavoriteEntity,
     * ArticleFavoritesViewModel, Activity, MaterialCardView) for imlementation.
     * <p>
     * When user clicks the button the animation starts and fades out the cardView by applying
     * properties inside XML.
     * @see R.anim#fragment_fade_exit
     */
    public static Animation fadeOutCardAnimation(Context context, MaterialCardView cardView) {
        Animation animFadeOut = AnimationUtils.loadAnimation(context, R.anim.fragment_fade_exit);
        cardView.startAnimation(animFadeOut);
        return animFadeOut;
    }
}
