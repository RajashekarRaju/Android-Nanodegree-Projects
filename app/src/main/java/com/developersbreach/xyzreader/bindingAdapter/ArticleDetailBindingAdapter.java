package com.developersbreach.xyzreader.bindingAdapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

public class ArticleDetailBindingAdapter {

    /**
     * When recipeName is used on TextView, the method bindRecipeName is called.
     *
     * @param textView   a view which we use to set a String value to it.
     * @param authorDetailName contains String value to be set to TextView.
     */
    @BindingAdapter("authorDetailName")
    public static void bindAuthorDetailName(TextView textView, String authorDetailName) {
        textView.setText(authorDetailName);
    }

    @BindingAdapter("detailThumbnail")
    public static void bindDetailThumbnail(ImageView imageView, String thumbnail) {

        Glide.with(imageView.getContext())
                .asBitmap()
                .load(thumbnail)
                .into(imageView);
    }
}
