package com.developersbreach.xyzreader.bindingAdapter;

import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.developersbreach.xyzreader.utils.DataFormatting;

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
}
