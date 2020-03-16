package com.developersbreach.xyzreader.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.developersbreach.xyzreader.R.layout;
import com.developersbreach.xyzreader.XYZReaderApp;
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.repository.ArticleRepository;
import com.developersbreach.xyzreader.repository.database.entity.ArticleEntity;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;
import com.developersbreach.xyzreader.view.list.ArticleAdapter;
import com.developersbreach.xyzreader.view.list.ArticleListFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * This viewModel is responsible for preparing and managing the data for fragment associated class
 * {@link ArticleListFragment}
 * This ViewModel expose the data Articles List via {@link LiveData} and fragment observes the changes.
 * This helps us save fragment data and keeps UI simple.
 * <p>
 * This class performs gets data from {@link ArticleRepository} class, for that reason we are
 * extending this to {@link AndroidViewModel} get be aware of application context. And get reference
 * to repository and resources we need access to.
 * <p>
 * This viewModel is set to data binding, setters are declared in {@link layout#item_article} and
 * also called in {@link ArticleAdapter}.
 */
public class ArticleListViewModel extends AndroidViewModel {


    /**
     * @return returns externally exposed {@link LiveData} object of List of type {@link Article}
     * allowing fragment to observe changes. Data is observed once changes will be done internally.
     */
    public LiveData<List<Article>> articles() {
        return getArticleList();
    }

    /**
     * This field is encapsulated, When the data is being changed we use this LiveData object to
     * update Articles with new values. And any externally exposed LiveData can observe the value
     * of this return type..
     */
    private final LiveData<List<Article>> _mMutableArticleList;

    /**
     * Declare repository and get reference to it for calling methods inside it with application
     * in this class AndroidViewModel constructor.
     */
    private final ArticleRepository mRepository;

    /**
     * @param application provides application context for ViewModel. With this context get reference
     *                    to other classes like repository.
     */
    public ArticleListViewModel(@NonNull Application application) {
        super(application);
        // Get a new instance from this app repository using application context.
        mRepository = ((XYZReaderApp) application).getRepository();
        // Get valid source from repository which are observable return types.
        LiveData<List<ArticleEntity>> source = mRepository.getObservableArticleList();
        // Using switchMaps transform the object with required return type with source.
        _mMutableArticleList = Transformations.switchMap(source, articleEntityList -> {
            MutableLiveData<List<Article>> mutableArticlesLiveData = new MutableLiveData<>();
            // Declare a new array list to add values from source using maps.
            List<Article> articleList = new ArrayList<>();
            // Convert objects from ArticleEntityList to ArticleList.
            Article.articleEntityToArticle(articleEntityList, articleList);
            // Since the value has been changed after mapping list. Pass the result for liveData.
            mutableArticlesLiveData.postValue(articleList);
            // Return the new list of articles of type MutableLiveData.
            return mutableArticlesLiveData;
        });
    }

    /**
     * @return returns internally exposed list data, any external objects can use this return value.
     */
    private LiveData<List<Article>> getArticleList() {
        return _mMutableArticleList;
    }

    /**
     * @param article has data for single article where user has selected. With that data we add
     *                article to FavoriteEntity with {@link Article#articleToFavoriteArticle(Article)}
     *                method. Later we call repository for inserting that data into database
     *                which takes favorite article.
     * @see ArticleRepository#insertFavoriteArticle(FavoriteEntity) for implementation.
     */
    public void insertFavoriteArticleData(Article article) {
        FavoriteEntity favoriteEntity = Article.articleToFavoriteArticle(article);
        mRepository.insertFavoriteArticle(favoriteEntity);
    }
}
