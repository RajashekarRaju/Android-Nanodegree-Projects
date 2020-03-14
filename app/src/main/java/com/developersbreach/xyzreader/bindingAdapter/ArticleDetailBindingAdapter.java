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
import com.developersbreach.xyzreader.view.detail.ArticleDetailFragment;
import com.developersbreach.xyzreader.view.detail.DetailViewPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * BindingAdapter for fragment class {@link ArticleDetailFragment}.
 * This values will be called as attributes in fragment layout {@link R.layout#item_article_adapter}.
 * Setters for binding from layout is set in adapter class {@link DetailViewPagerAdapter}.
 */
public class ArticleDetailBindingAdapter {

    /**
     * When value articleDetailItemTitle is used as attribute on TextView, the method
     * bindArticleDetailItemTitle is called.
     *
     * @param textView a view which we use to set a String value to it.
     * @param title    contains String value article title to be set to TextView.
     */
    @BindingAdapter("articleDetailItemTitle")
    public static void bindArticleDetailItemTitle(TextView textView, String title) {
        textView.setText(title);
    }

    /**
     * When value articleDetailItemAuthorName is used as attribute on TextView, the method
     * bindArticleDetailItemAuthorName is called.
     *
     * @param textView a view which we use to set a String value to it.
     * @param author   contains String value author of the article to be set to TextView.
     */
    @BindingAdapter("articleDetailItemAuthorName")
    public static void bindArticleDetailItemAuthorName(TextView textView, String author) {
        textView.setText(author);
    }

    /**
     * When value articleDetailItemThumbnail is used as attribute on TextView, the method
     * bindArticleDetailItemThumbnail is called.
     *
     * @param imageView a view which we use to set thumbnail to it.
     * @param thumbnail contains URL String value for article image to be set to ImageView using
     *                  glide library.
     */
    @BindingAdapter("articleDetailItemThumbnail")
    public static void bindArticleDetailItemThumbnail(ImageView imageView, String thumbnail) {
        Glide.with(imageView.getContext()).load(thumbnail).into(imageView);
    }

    /**
     * When value asyncArticleDetailItemBody is used as attribute on TextView, the method
     * bindAsyncArticleDetailItemBody is called.
     *
     * @param textView a view which we use to set to article body.
     * @param body     contains URL String value for article image to be set to ImageView.
     * @see PrecomputedText.Params A text object that contains the character metrics data and can be
     * used to improve the performance of text layout operations.
     */
    @BindingAdapter("asyncArticleDetailItemBody")
    public static void bindAsyncArticleDetailItemBody(TextView textView, String body) {

        // Start this operation in a background thread since raw text data is large.
        AppExecutors.getInstance().backgroundThread().execute(() -> {
            // Check for version. Ff pie or later, use PrecomputedText.Params for operation.
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                // Get required text layout metrics for operation from TextView.
                PrecomputedText.Params precomputedText = textView.getTextMetricsParams();
                // Pass CharSequence and Params to create a with object PrecomputedText.
                PrecomputedText text = PrecomputedText.create(body, precomputedText);
                // Get computed result and set the text data to textView in MainThread which is
                // called inside background thread, so that app won't crash.
                AppExecutors.getInstance().mainThread().execute(() -> textView.setText(text));
                // If version is below and earlier pie use Compat version of Precomputed and do
                // the same operation.
            } else {
                // Use compat to get text layout metrics for operation from TextView.
                PrecomputedTextCompat.Params params = TextViewCompat.getTextMetricsParams(textView);
                // Pass CharSequence and Params to create a with object PrecomputedTextCompat.
                PrecomputedTextCompat precomputedTextCompat =
                        PrecomputedTextCompat.create(body, params);
                // Get computed result and set the text data to textView in MainThread which is
                // called inside background thread, so that app won't crash.
                AppExecutors.getInstance().mainThread().execute(() ->
                        textView.setText(precomputedTextCompat));
            }
            // This will look for text inside the body to change it's behaviour to URL if aby found,
            // which is clickable and styled.
            textView.setAutoLinkMask(Linkify.WEB_URLS);
        });
    }

    /**
     * When value fabClickListener is used as attribute on FloatingActionButton, the method
     * bindFabClickListener is called.
     *
     * @param fab     a view which we use to set a click listener to it.
     * @param article contains article data value, we are using to get article title and author of
     *                the selected article by the user when clicked and will be shared to apps
     *                using {@link Intent} with {@link Intent#ACTION_SEND}.
     */
    @BindingAdapter("fabClickListener")
    public static void bindFabClickListener(FloatingActionButton fab, Article article) {
        fab.setOnClickListener(v -> {
            // Create new sharable intent.
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            // Sett type
            sharingIntent.setType("text/plain");
            // Get context for getting resources.
            final Resources resources = fab.getContext().getResources();
            // Format the data which we send to other apps.
            String body = resources.getString(R.string.fab_body_builder_title) +
                    article.getArticleTitle() + "\n" +
                    resources.getString(R.string.fab_body_builder_by) + article.getArticleAuthorName();
            sharingIntent.putExtra(Intent.EXTRA_TEXT, body);
            // Start the intent.
            fab.getContext().startActivity(sharingIntent);
        });
    }

    /**
     * When value detailAppBarLayout is used as attribute on {@link AppBarLayout}, the method
     * bindDetailAppBarLayout is called.
     * <p>
     * When value detailCollapsingToolbarLayout is used as attribute on {@link CollapsingToolbarLayout},
     * the method bindDetailAppBarLayout is called.
     *
     * @param appBarLayout            with this var call offsetListener to change the behaviour of appBar.
     * @param article                 contains data values for setting title and thumbnail in collapsingToolBar
     *                                for {@link ArticleDetailFragment}.
     * @param collapsingToolbarLayout get data from selected article and set the title to it.
     */
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
                    // Show title when completely collapsed.
                    collapsingToolbarLayout.setTitle(article.getArticleTitle());
                    isShow = true;
                } else if (isShow) {
                    // Hide title when collapsedToolBar is completely visible using empty string.
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });
    }
}
