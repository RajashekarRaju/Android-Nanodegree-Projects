package com.developersbreach.xyzreader.bindingAdapter;

import android.app.Activity;
import android.text.Spanned;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;
import com.developersbreach.xyzreader.utils.DataFormatting;
import com.developersbreach.xyzreader.view.favorite.ArticleFavoritesFragment;
import com.developersbreach.xyzreader.view.favorite.ArticleFavoritesFragmentDirections;
import com.developersbreach.xyzreader.viewModel.ArticleFavoritesViewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

public class FavoriteListBindingAdapter {

    @BindingAdapter("favoriteArticleItemTitle")
    public static void bindFavoriteArticleItemTitle(TextView textView, String title) {
        textView.setText(title);
    }

    @BindingAdapter("favoriteArticleItemAuthorName")
    public static void bindFavoriteArticleItemAuthorName(TextView textView, String authorName) {
        textView.setText(authorName);
    }

    @BindingAdapter("favoriteArticleItemPublishedDate")
    public static void bindFavoriteArticleItemPublishedDate(TextView textView, String publishedDate) {
        Spanned result = DataFormatting.formatDate(publishedDate);
        textView.setText(result);
    }

    @BindingAdapter("favoriteArticleItemThumbnail")
    public static void bindFavoriteArticleItemThumbnail(ImageView imageView, String thumbnail) {

        Glide.with(imageView.getContext())
                .asBitmap()
                .load(thumbnail)
                .into(imageView);
    }

    @BindingAdapter("favoriteListToDetailClickListener")
    public static void bindFavoriteArticleClickListener(MaterialCardView cardView,
                                                        FavoriteEntity favoriteEntity) {
        cardView.setOnClickListener(view -> {
            final String className = ArticleFavoritesFragment.class.getSimpleName();

            Article article = new Article(
                    favoriteEntity.getArticleId(),
                    favoriteEntity.getArticleTitle(),
                    favoriteEntity.getArticleAuthorName(),
                    favoriteEntity.getArticleBody(),
                    favoriteEntity.getArticleThumbnail(),
                    favoriteEntity.getArticlePublishedDate());

            NavDirections direction = ArticleFavoritesFragmentDirections
                    .articleFavoritesToArticleDetailFragment(article,
                            className);
            Navigation.findNavController(view).navigate(direction);
        });
    }

    @BindingAdapter({"deleteFavoriteClickListener", "favoriteViewModel", "activityFavoriteList",
            "favoriteCardView"})
    public static void bindDeleteFavoriteClickListener(
            ImageView imageView, FavoriteEntity favoriteEntity, ArticleFavoritesViewModel viewModel,
            Activity activity, MaterialCardView cardView) {

        imageView.setOnClickListener(view -> {
            Animation animFadeOut = AnimationUtils.loadAnimation(cardView.getContext(),
                    R.anim.fragment_fade_exit);
            cardView.startAnimation(animFadeOut);
            deleteAfterAnimation(animFadeOut, viewModel, favoriteEntity, activity);
        });
    }

    private static void deleteAfterAnimation(Animation animation, ArticleFavoritesViewModel viewModel,
                                             FavoriteEntity favoriteEntity, Activity activity) {

        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {

            /**
             * <p>Notifies the end of the animation. This callback is not invoked
             * for animations with repeat count set to INFINITE.</p>
             *
             * @param animation The animation which reached its end.
             */
            @Override
            public void onAnimationEnd(Animation animation) {
                viewModel.deleteFavoriteData(favoriteEntity);
                View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
                Snackbar.make(rootView, "Deleted", Snackbar.LENGTH_SHORT).show();
            }

            /**
             * <p>Notifies the start of the animation.</p>
             *
             * @param animation The started animation.
             */
            @Override
            public void onAnimationStart(Animation animation) {
            }

            /**
             * <p>Notifies the repetition of the animation.</p>
             *
             * @param animation The animation which was repeated.
             */
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
