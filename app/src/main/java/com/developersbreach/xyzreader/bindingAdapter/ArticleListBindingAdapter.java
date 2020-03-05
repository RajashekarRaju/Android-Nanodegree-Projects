package com.developersbreach.xyzreader.bindingAdapter;

import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.developersbreach.xyzreader.utils.DataFormatting;

public class ArticleListBindingAdapter {

    /**
     * When recipeName is used on TextView, the method bindRecipeName is called.
     *
     * @param textView   a view which we use to set a String value to it.
     * @param authorName contains String value to be set to TextView.
     */
    @BindingAdapter("authorName")
    public static void bindAuthorName(TextView textView, String authorName) {
        textView.setText(authorName);
    }

    @BindingAdapter("articleTitle")
    public static void bindArticleTitle(TextView textView, String title) {
        textView.setText(title);
    }

    @BindingAdapter("articlePublishedDate")
    public static void bindArticlePublishedDate(TextView textView, String publishedDate) {
        Spanned result = DataFormatting.formatDate(publishedDate);
        textView.setText(result);
    }

    @BindingAdapter("articleThumbnail")
    public static void bindThumbnail(ImageView imageView, String thumbnail) {

        Glide.with(imageView.getContext())
                .asBitmap()
                .load(thumbnail)
                .into(imageView);
    }
}
