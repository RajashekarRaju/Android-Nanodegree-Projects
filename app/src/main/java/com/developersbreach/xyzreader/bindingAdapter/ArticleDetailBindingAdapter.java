package com.developersbreach.xyzreader.bindingAdapter;

import android.content.Intent;
import android.content.res.Resources;
import android.text.PrecomputedText;
import android.text.util.Linkify;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.text.PrecomputedTextCompat;
import androidx.core.widget.TextViewCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.repository.AppExecutors;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ArticleDetailBindingAdapter {

    /**
     * When recipeName is used on TextView, the method bindRecipeName is called.
     *
     * @param textView         a view which we use to set a String value to it.
     * @param title contains String value to be set to TextView.
     */
    @BindingAdapter("articleDetailItemTitle")
    public static void bindArticleDetailItemTitle(TextView textView, String title) {
        textView.setText(title);
    }

    @BindingAdapter("articleDetailItemAuthorName")
    public static void bindArticleDetailItemAuthorName(TextView textView, String author) {
        textView.setText(author);
    }

    @BindingAdapter("articleDetailItemThumbnail")
    public static void bindArticleDetailItemThumbnail(ImageView imageView, String thumbnail) {
        Glide.with(imageView.getContext()).load(thumbnail).into(imageView);
    }

    @BindingAdapter("asyncArticleDetailItemBody")
    public static void bindAsyncArticleDetailItemBody(TextView textView, String body) {

        AppExecutors.getInstance().backgroundThread().execute(() -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                PrecomputedText.Params precomputedText = textView.getTextMetricsParams();
                PrecomputedText text = PrecomputedText.create(body, precomputedText);
                AppExecutors.getInstance().mainThread().execute(() -> textView.setText(text));
            } else {
                PrecomputedTextCompat.Params params = TextViewCompat.getTextMetricsParams(textView);
                PrecomputedTextCompat precomputedTextCompat =
                        PrecomputedTextCompat.create(body, params);
                AppExecutors.getInstance().mainThread().execute(() ->
                        textView.setText(precomputedTextCompat));
            }
            textView.setAutoLinkMask(Linkify.WEB_URLS);
        });
    }

    @BindingAdapter("fabClickListener")
    public static void bindFabClickListener(FloatingActionButton fab, Article article) {
        fab.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            final Resources resources = fab.getContext().getResources();
            String body = resources.getString(R.string.fab_body_builder_title) +
                    article.getArticleTitle() + "\n" +
                    resources.getString(R.string.fab_body_builder_by) + article.getArticleAuthorName();
            sharingIntent.putExtra(Intent.EXTRA_TEXT, body);
            fab.getContext().startActivity(sharingIntent);
        });
    }

    @BindingAdapter({"detailAppBarLayout", "detailCollapsingToolbarLayout"})
    public static void bindDetailAppBarLayout(AppBarLayout appBarLayout, Article article,
                                              CollapsingToolbarLayout collapsingToolbarLayout) {
        // Using a listener to get state of CollapsingToolbar and Toolbar to set properties.
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    // Show title until layout won't collapse.
                    collapsingToolbarLayout.setTitle(article.getArticleTitle());
                    isShow = true;
                } else if (isShow) {
                    // When completely scrolled remove title.
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });
    }
}
