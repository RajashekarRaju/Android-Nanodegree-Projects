package com.developersbreach.xyzreader.bindingAdapter;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

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
}
