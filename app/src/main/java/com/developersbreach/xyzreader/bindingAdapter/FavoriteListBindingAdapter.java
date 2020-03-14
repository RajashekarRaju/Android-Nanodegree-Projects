package com.developersbreach.xyzreader.bindingAdapter;

import android.app.Activity;
import android.text.Spanned;
import android.view.View;
import android.view.animation.Animation;
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
import com.developersbreach.xyzreader.utils.ArticleAnimations;
import com.developersbreach.xyzreader.utils.DataFormatting;
import com.developersbreach.xyzreader.view.detail.ArticleDetailFragment;
import com.developersbreach.xyzreader.view.favorite.ArticleFavoritesFragment;
import com.developersbreach.xyzreader.view.favorite.ArticleFavoritesFragmentDirections;
import com.developersbreach.xyzreader.view.favorite.FavoriteAdapter;
import com.developersbreach.xyzreader.viewModel.ArticleFavoritesViewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;


/**
 * BindingAdapter for fragment class {@link ArticleFavoritesFragment}.
 * This values will be called as attributes in fragment layout {@link R.layout#item_favorite}.
 * Setters for binding from layout is set in adapter class {@link FavoriteAdapter}.
 */
public class FavoriteListBindingAdapter {

    /**
     * When value favoriteArticleItemTitle is used as attribute on TextView, the method
     * bindArticleItemTitle is called.
     *
     * @param textView a view which we use to set a String value to it.
     * @param title    contains String value favorite article title to be set to TextView.
     */
    @BindingAdapter("favoriteArticleItemTitle")
    public static void bindFavoriteArticleItemTitle(TextView textView, String title) {
        textView.setText(title);
    }

    /**
     * When value favoriteArticleItemAuthor is used as attribute on TextView, the method
     * bindFavoriteArticleItemAuthor is called.
     *
     * @param textView a view which we use to set a String value to it.
     * @param author   contains String value favorite author of the article to be set to TextView.
     */
    @BindingAdapter("favoriteArticleItemAuthor")
    public static void bindFavoriteArticleItemAuthor(TextView textView, String author) {
        textView.setText(author);
    }

    /**
     * When value favoriteArticleItemPublishedDate is used as attribute on TextView, the method
     * bindFavoriteArticleItemPublishedDate is called.
     *
     * @param textView      a view which we use to set a String value to it.
     * @param publishedDate contains String value date to be set to TextView.
     */
    @BindingAdapter("favoriteArticleItemPublishedDate")
    public static void bindFavoriteArticleItemPublishedDate(TextView textView, String publishedDate) {
        // Use object Spanned to receive markup values. In this case, receive a date os type string
        // which is formatted into readable form.
        Spanned formattedPublishedDate = DataFormatting.formatDate(publishedDate);
        textView.setText(formattedPublishedDate);
    }

    /**
     * When value favoriteArticleItemThumbnail is used as attribute on TextView, the method
     * bindFavoriteArticleItemThumbnail is called.
     *
     * @param imageView a view which we use to set thumbnail to it.
     * @param thumbnail contains URL String value for favorite article image to be set to ImageView
     *                  using glide library.
     */
    @BindingAdapter("favoriteArticleItemThumbnail")
    public static void bindFavoriteArticleItemThumbnail(ImageView imageView, String thumbnail) {
        Glide.with(imageView.getContext()).load(thumbnail).into(imageView);
    }

    /**
     * When value favoriteListToDetailClickListener is used as attribute on MaterialCardView, the
     * method bindFavoriteArticleListToDetailClickListener is called. This navigates user to other
     * fragment, {@link ArticleFavoritesFragment} to {@link ArticleDetailFragment} using a action
     * declared in {@link R.navigation#navigation_graph} assigned to {@link NavDirections} using
     * {@link Navigation} library.
     * <p>
     * {@link ArticleFavoritesFragmentDirections#articleFavoritesToArticleDetailFragment(Article, String)}
     * which takes two args. First one contains data for selected data, while other argument takes
     * name of the fragment which is navigation from to destination. This is important to change
     * the behaviour of {@link ArticleDetailFragment} class.
     *
     * @param cardView       a view which we use to set click listener to perform this action.
     * @param favoriteEntity contains data article which is passed as argument using SafeArgs.
     */
    @BindingAdapter("favoriteListToDetailClickListener")
    public static void bindFavoriteArticleListToDetailClickListener(MaterialCardView cardView,
                                                                    FavoriteEntity favoriteEntity) {
        cardView.setOnClickListener(view -> {
            // Assign fragment class name as a string.
            final String className = ArticleFavoritesFragment.class.getSimpleName();
            // Call this method which takes FavoriteEntity and returns new Article data values so
            // that we can pass same argument to DetailFragment just like ArticleListFragment class.
            final Article article = Article.favoriteArticleToArticle(favoriteEntity);
            // Get directions to navigate to or from fragment using Actions which mapped in
            // NavGraph. This methods are auto-generated by NavigationComponent library only
            // after successful gradle build.
            // Since this actions takes article as argument, pass article with directions.
            NavDirections direction = ArticleFavoritesFragmentDirections
                    .articleFavoritesToArticleDetailFragment(article, className);
            // Find NavController with view and navigate to destination using directions.
            Navigation.findNavController(view).navigate(direction);
        });
    }

    /**
     * When value deleteFavoriteArticleClickListener is used as attribute on ImageView, the method
     * bindDeleteFavoriteClickListener is called. When user clicks this view, we delete the article
     * data from database table called [favorite_table] see {@link FavoriteEntity} class
     * for table name declaration with value.
     * <p>
     * With value favoriteViewModel is being called on attribute, we call viewModel class
     * {@link ArticleFavoritesViewModel} as setter which is passed in adapter class
     * {@link FavoriteAdapter} with binding variable.
     * <p>
     * With value activityFavoriteList is being called on attribute, we call {@link Activity} from
     * fragment class {@link ArticleFavoritesFragment} which behaves as {@link FragmentActivity}.
     *
     * @param imageView      view which represents to delete article from favorites.
     * @param favoriteEntity contains data for selected article value to delete from favorites table.
     * @param viewModel      we need this declaration so that we can access viewModel class to call
     *                       method
     *                       {@link ArticleFavoritesViewModel#deleteFavoriteArticleData(FavoriteEntity)}
     *                       for adding article to favorites.
     * @param activity       get access to current fragments window to show {@link Snackbar} which shows
     *                       message that favorites deleted from table after the view being clicked.
     */
    @BindingAdapter({"deleteFavoriteArticleClickListener", "favoriteViewModel", "activityFavoriteList",
            "favoriteCardView"})
    public static void bindDeleteFavoriteClickListener(
            ImageView imageView, FavoriteEntity favoriteEntity, ArticleFavoritesViewModel viewModel,
            Activity activity, MaterialCardView cardView) {
        imageView.setOnClickListener(view -> {
            // Create a simple fade out animation to let user know the selected article has been
            // removed after clicking the imageView.
            final Animation animation = ArticleAnimations
                    .fadeOutCardAnimation(cardView.getContext(), cardView);
            deleteAfterAnimation(animation, viewModel, favoriteEntity, activity);
        });
    }

    /**
     * Let the delete operation only take place once animation is being finished.
     * If first we call delete method, there will be no view to perform animation on.
     *
     * @param cardFadeOutAnimation this is view which has article and needs to be animated.
     * @param viewModel gets delete method from class.
     * @param favoriteEntity gets article data to work with.
     * @param activity gets reference to fragment for showing snackBar.
     */
    private static void deleteAfterAnimation(
            Animation cardFadeOutAnimation, ArticleFavoritesViewModel viewModel,
            FavoriteEntity favoriteEntity, Activity activity) {
        // Set appropriate duration to animate.
        cardFadeOutAnimation.setDuration(500L);
        // Attach a listener and perform delete operation.
        cardFadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {

            /**
             * At this point we reached to finish animation. So,
             * 1. Delete article
             * 2. Get window for current fragment being displayed.
             * 3. Show snackBar message while data is being deleted by user.
             *
             * <p>Notifies the end of the animation. This callback is not invoked
             * for animations with repeat count set to INFINITE.</p>
             *
             * @param animation The animation which reached its end.
             */
            @Override
            public void onAnimationEnd(Animation animation) {
                viewModel.deleteFavoriteArticleData(favoriteEntity);
                View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
                Snackbar.make(rootView, R.string.removed_favorite, Snackbar.LENGTH_SHORT).show();
            }

            /**
             * <p>Notifies the start of the animation.</p>
             *
             * @param animation The started animation.
             */
            @Override
            public void onAnimationStart(Animation animation) {
            }

            /**
             * <p>Notifies the repetition of the animation.</p>
             *
             * @param animation The animation which was repeated.
             */
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
