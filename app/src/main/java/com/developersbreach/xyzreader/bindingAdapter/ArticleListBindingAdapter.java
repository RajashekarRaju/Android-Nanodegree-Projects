package com.developersbreach.xyzreader.bindingAdapter;

import android.app.Activity;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;
import com.developersbreach.xyzreader.utils.DataFormatting;
import com.developersbreach.xyzreader.utils.SnackbarBuilder;
import com.developersbreach.xyzreader.view.detail.ArticleDetailFragment;
import com.developersbreach.xyzreader.view.list.ArticleAdapter;
import com.developersbreach.xyzreader.view.list.ArticleListFragment;
import com.developersbreach.xyzreader.view.list.ArticleListFragmentDirections;
import com.developersbreach.xyzreader.viewModel.ArticleListViewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;


/**
 * BindingAdapter for fragment class {@link ArticleListFragment}.
 * This values will be called as attributes in fragment layout {@link R.layout#item_article}.
 * Setters for binding from layout is set in adapter class {@link ArticleAdapter}.
 */
public class ArticleListBindingAdapter {

    /**
     * When value articleItemTitle is used as attribute on TextView, the method bindArticleItemTitle
     * is called.
     *
     * @param textView a view which we use to set a String value to it.
     * @param title    contains String value article title to be set to TextView.
     */
    @BindingAdapter("articleItemTitle")
    public static void bindArticleItemTitle(TextView textView, String title) {
        textView.setText(title);
    }

    /**
     * When value articleItemAuthorName is used as attribute on TextView, the method
     * bindArticleItemAuthorName is called.
     *
     * @param textView a view which we use to set a String value to it.
     * @param author   contains String value author of the article to be set to TextView.
     */
    @BindingAdapter("articleItemAuthorName")
    public static void bindArticleItemAuthorName(TextView textView, String author) {
        textView.setText(author);
    }

    /**
     * When value articleItemPublishedDate is used as attribute on TextView, the method
     * bindArticleItemPublishedDate is called.
     *
     * @param textView      a view which we use to set a String value to it.
     * @param publishedDate contains String value date to be set to TextView.
     */
    @BindingAdapter("articleItemPublishedDate")
    public static void bindArticleItemPublishedDate(TextView textView, String publishedDate) {
        // Use object Spanned to receive markup values. In this case, receive a date os type string
        // which is formatted into readable form.
        Spanned formattedPublishedDate = DataFormatting.formatDate(publishedDate);
        textView.setText(formattedPublishedDate);
    }

    /**
     * When value articleItemThumbnail is used as attribute on TextView, the method
     * bindArticleItemThumbnail is called.
     *
     * @param imageView a view which we use to set thumbnail to it.
     * @param thumbnail contains URL String value for article image to be set to ImageView using
     *                  glide library.
     */
    @BindingAdapter("articleItemThumbnail")
    public static void bindArticleItemThumbnail(ImageView imageView, String thumbnail) {
        Glide.with(imageView.getContext()).load(thumbnail).into(imageView);
    }

    /**
     * When value articleListToDetailClickListener is used as attribute on MaterialCardView, the
     * method bindArticleListToDetailClickListener is called. This navigates user to other fragment,
     * {@link ArticleListFragment} to {@link ArticleDetailFragment} using a action declared in
     * {@link R.navigation#navigation_graph} assigned to {@link NavDirections} using
     * {@link Navigation} library.
     * <p>
     * {@link ArticleListFragmentDirections#articleListToArticleDetailFragment(Article, String)}
     * which takes two args. First one contains data for selected data, while other argument takes
     * name of the fragment which is navigation from to destination. This is important to change
     * the behaviour of {@link ArticleDetailFragment} class.
     *
     * @param cardView a view which we use to set click listener to perform this action.
     * @param article  contains data article which is passed as argument using SafeArgs.
     */
    @BindingAdapter("articleListToDetailClickListener")
    public static void bindArticleListToDetailClickListener(MaterialCardView cardView, Article article) {
        cardView.setOnClickListener(view -> {
            // Assign fragment class name as a string.
            final String className = ArticleListFragment.class.getSimpleName();
            // Get directions to navigate to or from fragment using Actions which mapped in
            // NavGraph. This methods are auto-generated by NavigationComponent library only
            // after successful gradle build.
            // Since this actions takes article as argument, pass article with directions.
            NavDirections direction = ArticleListFragmentDirections
                    .articleListToArticleDetailFragment(article, className);
            // Find NavController with view and navigate to destination using directions.
            Navigation.findNavController(view).navigate(direction);
        });
    }


    /**
     * When value insertFavoriteArticleClickListener is used as attribute on ImageView, the method
     * bindArticleFavoriteClickListener is called. When user clicks this view, we insert the article
     * data into separate database table called [favorite_table] see {@link FavoriteEntity} class
     * for table name declaration with value.
     * <p>
     * With value viewModelArticleList is being called on attribute, we call viewModel class
     * {@link ArticleListViewModel} as setter which is passed in adapter class {@link ArticleAdapter}
     * with binding variable.
     * <p>
     * With value activityArticleList is being called on attribute, we call {@link Activity} from
     * fragment class {@link ArticleListFragment} which behaves as {@link FragmentActivity}.
     *
     * @param imageView view which represents to add article to favorites.
     * @param article   contains data for selected article value to add into favorites table.
     * @param viewModel we need this declaration so that we can access viewModel class to call
     *                  method {@link ArticleListViewModel#insertFavoriteArticleData(Article)} for
     *                  adding article to favorites.
     * @param activity  get access to current fragments window to show {@link Snackbar} which shows
     *                  message that favorites have been added to table after the view being clicked.
     */
    @BindingAdapter({"insertFavoriteArticleClickListener", "viewModelArticleList", "activityArticleList"})
    public static void bindArticleFavoriteClickListener(
            ImageView imageView, Article article, ArticleListViewModel viewModel, Activity activity) {

        final int ID = article.getArticleId() + 1;
        final boolean isFavorite = viewModel.isFavorite(ID);
        if (isFavorite) {
            imageView.setImageResource(R.drawable.ic_delete_favorite);
        } else {
            imageView.setImageResource(R.drawable.ic_add_favorite_filled);
        }

        imageView.setOnClickListener(view -> {
            if (!isFavorite) {
                viewModel.insertFavoriteArticleData(article);
                imageView.setImageResource(R.drawable.ic_delete_favorite);
            } else {
                viewModel.deleteFavoriteArticleData(article);
                imageView.setImageResource(R.drawable.ic_add_favorite_filled);
            }

            final String message = imageView.getContext().getString(R.string.snackbar_added_to_favorites_message);
            SnackbarBuilder.showSnackBar(message, activity);
        });
    }
}
