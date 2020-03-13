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
import com.developersbreach.xyzreader.utils.DataFormatting;
import com.developersbreach.xyzreader.view.list.ArticleListFragment;
import com.developersbreach.xyzreader.view.list.ArticleListFragmentDirections;
import com.developersbreach.xyzreader.viewModel.ArticleListViewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

public class ArticleListBindingAdapter {

    /**
     * When recipeName is used on TextView, the method bindRecipeName is called.
     *
     * @param textView   a view which we use to set a String value to it.
     * @param title contains String value to be set to TextView.
     */
    @BindingAdapter("articleItemTitle")
    public static void bindArticleItemTitle(TextView textView, String title) {
        textView.setText(title);
    }

    @BindingAdapter("articleItemAuthorName")
    public static void bindArticleItemAuthorName(TextView textView, String authorName) {
        textView.setText(authorName);
    }

    @BindingAdapter("articleItemPublishedDate")
    public static void bindArticleItemPublishedDate(TextView textView, String publishedDate) {
        Spanned result = DataFormatting.formatDate(publishedDate);
        textView.setText(result);
    }

    @BindingAdapter("articleItemThumbnail")
    public static void bindArticleItemThumbnail(ImageView imageView, String thumbnail) {

        Glide.with(imageView.getContext())
                .asBitmap()
                .load(thumbnail)
                .into(imageView);
    }

    @BindingAdapter(value = {"insertFavoriteClickListener", "viewModelArticleList", "activityArticleList"})
    public static void bindArticleFavoriteClickListener(ImageView imageView, Article article,
                                                        ArticleListViewModel viewModel, Activity activity) {
        imageView.setOnClickListener(view -> {
            viewModel.insertFavoriteData(article);
            View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
            Snackbar.make(rootView, article.getArticleAuthorName(), Snackbar.LENGTH_SHORT).show();
        });
    }

    @BindingAdapter("articleListToDetailClickListener")
    public static void bindArticleClickListener(MaterialCardView cardView, Article article) {
        cardView.setOnClickListener(v -> {
            final String className = ArticleListFragment.class.getSimpleName();
            NavDirections direction = ArticleListFragmentDirections
                    .articleListToArticleDetailFragment(article, className);
            Navigation.findNavController(cardView).navigate(direction);
        });
    }
}
