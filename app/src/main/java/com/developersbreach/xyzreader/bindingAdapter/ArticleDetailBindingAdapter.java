package com.developersbreach.xyzreader.bindingAdapter;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

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
}
