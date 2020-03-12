package com.developersbreach.xyzreader.bindingAdapter;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.view.search.SearchArticleFragment;
import com.developersbreach.xyzreader.view.search.SearchArticleFragmentDirections;

public class SearchListBindingAdapter {

    @BindingAdapter("searchArticleClickListener")
    public static void bindSearchArticleClickListener(TextView textView, Article article) {
        textView.setOnClickListener(view -> {
            NavDirections direction = SearchArticleFragmentDirections
                    .actionSearchArticleFragmentToArticleDetailFragment(article, SearchArticleFragment.class.getSimpleName());
            // Find NavController with view and navigate to destination using directions.
            Navigation.findNavController(view).navigate(direction);
        });
    }
}
