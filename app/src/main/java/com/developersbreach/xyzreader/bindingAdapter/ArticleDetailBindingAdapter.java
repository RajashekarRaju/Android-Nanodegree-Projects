package com.developersbreach.xyzreader.bindingAdapter;

import android.text.PrecomputedText;
import android.text.util.Linkify;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.text.PrecomputedTextCompat;
import androidx.core.widget.TextViewCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.developersbreach.xyzreader.repository.AppExecutors;


public class ArticleDetailBindingAdapter {

    /**
     * When recipeName is used on TextView, the method bindRecipeName is called.
     *
     * @param textView         a view which we use to set a String value to it.
     * @param authorDetailName contains String value to be set to TextView.
     */
    @BindingAdapter("authorDetailName")
    public static void bindAuthorDetailName(TextView textView, String authorDetailName) {
        textView.setText(authorDetailName);
    }

    @BindingAdapter("titleDetailName")
    public static void bindTitleDetailName(TextView textView, String titleDetailName) {
        textView.setText(titleDetailName);
    }

    @BindingAdapter("detailThumbnail")
    public static void bindDetailThumbnail(ImageView imageView, String thumbnail) {

        Glide.with(imageView.getContext())
                .asBitmap()
                .load(thumbnail)
                .into(imageView);
    }


    @BindingAdapter("asyncArticleBodyTitle")
    public static void bindBodyDetailName(TextView textView, String articleBodyTitle) {

        AppExecutors.getInstance().backgroundThread().execute(() -> {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {

                PrecomputedText.Params precomputedText = textView.getTextMetricsParams();
                PrecomputedText text = PrecomputedText.create(articleBodyTitle, precomputedText);
                AppExecutors.getInstance().mainThread().execute(() -> {
                    textView.setText(text);
                });

            } else {

                PrecomputedTextCompat.Params params = TextViewCompat.getTextMetricsParams(textView);
                PrecomputedTextCompat precomputedTextCompat =
                        PrecomputedTextCompat.create(articleBodyTitle, params);
                AppExecutors.getInstance().mainThread().execute(() -> {
                    textView.setText(precomputedTextCompat);
                });
            }

            textView.setAutoLinkMask(Linkify.WEB_URLS);
        });

    }
}
