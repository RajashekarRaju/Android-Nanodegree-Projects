package com.developersbreach.xyzreader.bindingAdapter;

import android.app.Activity;
import android.text.Spanned;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;
import com.developersbreach.xyzreader.utils.ArticleAnimations;
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

    @BindingAdapter("favoriteArticleItemAuthor")
    public static void bindFavoriteArticleItemAuthor(TextView textView, String author) {
        textView.setText(author);
    }

    @BindingAdapter("favoriteArticleItemPublishedDate")
    public static void bindFavoriteArticleItemPublishedDate(TextView textView, String publishedDate) {
        Spanned formattedPublishedDate = DataFormatting.formatDate(publishedDate);
        textView.setText(formattedPublishedDate);
    }

    @BindingAdapter("favoriteArticleItemThumbnail")
    public static void bindFavoriteArticleItemThumbnail(ImageView imageView, String thumbnail) {
        Glide.with(imageView.getContext()).load(thumbnail).into(imageView);
    }

    @BindingAdapter("favoriteListToDetailClickListener")
    public static void bindFavoriteArticleListToDetailClickListener(MaterialCardView cardView,
                                                                    FavoriteEntity favoriteEntity) {
        cardView.setOnClickListener(view -> {
            final String className = ArticleFavoritesFragment.class.getSimpleName();
            final Article article = Article.favoriteArticleToArticle(favoriteEntity);
            NavDirections direction = ArticleFavoritesFragmentDirections
                    .articleFavoritesToArticleDetailFragment(article, className);
            Navigation.findNavController(view).navigate(direction);
        });
    }

    @BindingAdapter({"deleteFavoriteArticleClickListener", "favoriteViewModel", "activityFavoriteList",
            "favoriteCardView"})
    public static void bindDeleteFavoriteClickListener(
            ImageView imageView, FavoriteEntity favoriteEntity, ArticleFavoritesViewModel viewModel,
            Activity activity, MaterialCardView cardView) {

        imageView.setOnClickListener(view -> {
            final Animation animation = ArticleAnimations.fadeOutCardAnimation(cardView.getContext(), cardView);
            deleteAfterAnimation(animation, viewModel, favoriteEntity, activity);
        });
    }

    private static void deleteAfterAnimation(
            Animation cardFadeOutAnimation, ArticleFavoritesViewModel viewModel,
            FavoriteEntity favoriteEntity, Activity activity) {

        cardFadeOutAnimation.setDuration(500L);
        cardFadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {

            /**
             * <p>Notifies the end of the animation. This callback is not invoked
             * for animations with repeat count set to INFINITE.</p>
             *
             * @param animation The animation which reached its end.
             */
            @Override
            public void onAnimationEnd(Animation animation) {
                viewModel.deleteFavoriteArticleData(favoriteEntity);
                View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
                Snackbar.make(rootView, R.string.removed_favorite, Snackbar.LENGTH_SHORT).show();
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
