package com.developersbreach.xyzreader.bindingAdapter;

import android.app.Activity;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;
import com.developersbreach.xyzreader.utils.DataFormatting;
import com.developersbreach.xyzreader.view.favorite.ArticleFavoritesFragment;
import com.developersbreach.xyzreader.view.favorite.ArticleFavoritesFragmentDirections;
import com.developersbreach.xyzreader.viewModel.ArticleFavoritesViewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

public class FavoriteListBindingAdapter {

    @BindingAdapter("favoriteAuthorName")
    public static void bindFavoriteAuthorName(TextView textView, String authorName) {
        textView.setText(authorName);
    }

    @BindingAdapter("favoriteArticleTitle")
    public static void bindFavoriteArticleTitle(TextView textView, String title) {
        textView.setText(title);
    }

    @BindingAdapter("favoriteArticlePublishedDate")
    public static void bindFavoriteArticlePublishedDate(TextView textView, String publishedDate) {
        Spanned result = DataFormatting.formatDate(publishedDate);
        textView.setText(result);
    }

    @BindingAdapter("favoriteArticleThumbnail")
    public static void bindFavoriteThumbnail(ImageView imageView, String thumbnail) {

        Glide.with(imageView.getContext())
                .asBitmap()
                .load(thumbnail)
                .into(imageView);
    }

    @BindingAdapter("favoriteArticleClickListener")
    public static void bindFavoriteArticleClickListener(MaterialCardView cardView, FavoriteEntity favoriteEntity) {
        cardView.setOnClickListener(view -> {

            Article article = new Article(
                    favoriteEntity.getArticleId(),
                    favoriteEntity.getArticleTitle(),
                    favoriteEntity.getArticleAuthorName(),
                    favoriteEntity.getArticleBody(),
                    favoriteEntity.getArticleThumbnail(),
                    favoriteEntity.getArticlePublishedDate());

            NavDirections direction = ArticleFavoritesFragmentDirections
                    .actionArticleFavoritesFragmentToArticleDetailFragment(article,
                            ArticleFavoritesFragment.class.getSimpleName());
            Navigation.findNavController(view).navigate(direction);
        });
    }

    @BindingAdapter(value = {"deleteFavoriteClickListener", "favoriteViewModel", "activityFavorite"})
    public static void bindDeleteFavoriteClickListener(ImageView imageView, FavoriteEntity favoriteEntity,
                                                        ArticleFavoritesViewModel viewModel, Activity activity) {
        imageView.setOnClickListener(view -> {
            viewModel.deleteFavoriteData(favoriteEntity);
            View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
            Snackbar.make(rootView, "Deleted", Snackbar.LENGTH_SHORT).show();
        });
    }
}
