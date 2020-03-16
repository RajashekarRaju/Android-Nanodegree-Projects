package com.developersbreach.xyzreader.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.XYZReaderApp;
import com.developersbreach.xyzreader.repository.ArticleRepository;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;
import com.developersbreach.xyzreader.view.favorite.ArticleFavoritesFragment;

import java.util.List;


/**
 * This viewModel is responsible for preparing and managing the data for fragment associated class
 * {@link ArticleFavoritesFragment} which shows user favorites of selected articles.
 * This ViewModel expose the data Favorite Articles List via {@link LiveData} added by the user and
 * fragment observes the changes.
 * This helps us save fragment data and keeps UI simple.
 * <p>
 * This class performs gets data from {@link ArticleRepository} class, for that reason we are
 * extending this to {@link AndroidViewModel} get be aware of application context. And get reference
 * to repository and resources we need access to.
 * <p>
 * This viewModel is set to data binding, setters are declared in {@link R.layout#item_favorite} and
 * also called in {@link com.developersbreach.xyzreader.view.favorite.FavoriteAdapter}.
 */
public class ArticleFavoritesViewModel extends AndroidViewModel {


    /**
     * @return returns externally exposed {@link LiveData} object of List of type {@link FavoriteEntity}
     * allowing fragment to observe changes. Data is observed once changes will be done internally.
     */
    public LiveData<List<FavoriteEntity>> favorites() {
        return getFavoriteList();
    }

    /**
     * This field is encapsulated, When the data is being changed we use this LiveData object to
     * update Favorites with new values. And any externally exposed LiveData can observe the value
     * of this return type..
     */
    private final MutableLiveData<List<FavoriteEntity>> _mMutableFavoriteList;

    /**
     * Declare repository and get reference to it for calling methods inside it with application
     * in this class AndroidViewModel constructor.
     */
    private final ArticleRepository mRepository;

    /**
     * @param application provides application context for ViewModel. With this context get reference
     *                    to other classes like repository.
     */
    public ArticleFavoritesViewModel(@NonNull Application application) {
        super(application);
        // Get a new instance from this app repository using application context.
        mRepository = ((XYZReaderApp) application).getRepository();
        // From repository call source which has observable favorites list data and assign it.
        _mMutableFavoriteList = mRepository.getObservableFavoriteList();
    }

    /**
     * @return returns internally exposed list data, any external objects can use this return value.
     */
    private LiveData<List<FavoriteEntity>> getFavoriteList() {
        return _mMutableFavoriteList;
    }

    /**
     * @param favoriteEntity has data for single article where user has selected. We delete that
     *                       article data from FavoriteEntity table calling repository for deleting
     *                       that data from database.
     * @see ArticleRepository#deleteFavoriteArticle(FavoriteEntity) for implementation.
     */
    public void deleteFavoriteArticleData(FavoriteEntity favoriteEntity) {
        mRepository.deleteFavoriteArticle(favoriteEntity);
    }
}
