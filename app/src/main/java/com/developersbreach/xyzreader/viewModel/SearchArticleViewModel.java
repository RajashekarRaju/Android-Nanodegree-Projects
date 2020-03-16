package com.developersbreach.xyzreader.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.XYZReaderApp;
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.repository.AppExecutors;
import com.developersbreach.xyzreader.repository.ArticleRepository;
import com.developersbreach.xyzreader.repository.database.entity.ArticleEntity;
import com.developersbreach.xyzreader.view.favorite.FavoriteAdapter;
import com.developersbreach.xyzreader.view.search.SearchArticleFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * This viewModel is responsible for preparing and managing the data for fragment associated class
 * {@link SearchArticleFragment} which shows user searchable articles.
 * This ViewModel expose the data Searched results Articles List via {@link LiveData} added by the
 * user and fragment observes the changes.
 * This helps us save fragment data and keeps UI simple.
 * <p>
 * This class performs gets data from {@link ArticleRepository} class, for that reason we are
 * extending this to {@link AndroidViewModel} get be aware of application context. And get reference
 * to repository and resources we need access to.
 * <p>
 * This viewModel is set to data binding, setters are declared in {@link R.layout#item_search_article}
 * and also called in {@link FavoriteAdapter}.
 */
public class SearchArticleViewModel extends AndroidViewModel {


    /**
     * @return returns externally exposed {@link LiveData} object of List of type {@link Article}
     * allowing fragment to observe changes. Data is observed once changes will be done internally.
     * This contains whole list from database used to run search query on.
     */
    public LiveData<List<Article>> articles() {
        return getSearchList();
    }

    /**
     * @return returns externally exposed {@link LiveData} object of List of type {@link Article}
     * allowing fragment to observe changes. Data is observed once changes will be done internally.
     * After getting all data from repository to liveData object articles(). We then run query on
     * data to filter the results and update the list with new changes every-time user types query.
     */
    public LiveData<List<Article>> filter(String query) {
        return onFilterChanged(query);
    }

    /**
     * This field is encapsulated, When the data is being changed we use this LiveData object to
     * update search with new values. And any externally exposed LiveData can observe the value
     * of this return type..
     */
    private final MutableLiveData<List<Article>> _mMutableSearchList;

    /**
     * This field is encapsulated, this liveData object has list for which we perform search query
     * on. The results will be sent to internal filed _mMutableSearchList.
     */
    private final LiveData<List<Article>> _mLiveDataSearchList;

    /**
     * Variable which has properties of articles for searching. We get data from {@link ArticleEntity}
     * to {@link Article}.
     */
    private List<Article> mArticleList;

    /**
     * @param application provides application context for ViewModel. With this context get reference
     *                    to other classes like repository.
     */
    public SearchArticleViewModel(@NonNull Application application) {
        super(application);
        // Declare repository and get reference to it for calling methods inside it with application
        // in this class AndroidViewModel constructor.
        // Get a new instance from this app repository using application context.
        ArticleRepository repository = ((XYZReaderApp) application).getRepository();
        // Get valid source from repository which are observable return types.
        final MediatorLiveData<List<ArticleEntity>> source = repository.getObservableArticleList();
        _mMutableSearchList = new MutableLiveData<>();
        mArticleList = new ArrayList<>();
        // Using switchMaps transform the object with required return type with source.
        _mLiveDataSearchList = Transformations.switchMap(source, articleEntityList -> {
            MutableLiveData<List<Article>> mutableArticleLiveData = new MutableLiveData<>();
            // Declare a new array list to add values from source using maps.
            List<Article> articleList = new ArrayList<>();
            // Convert objects from ArticleEntityList to ArticleList.
            Article.articleEntityToArticle(articleEntityList, articleList);
            // Store the data temporarily in new array list to perform search query later.
            mArticleList = articleList;
            // Since the value has been changed after mapping list. Pass the result for liveData.
            mutableArticleLiveData.postValue(articleList);
            // Return the new list of articles of type MutableLiveData.
            return mutableArticleLiveData;
        });
    }

    /**
     * We run search query on existing database in background thread and return those values.
     *
     * @param filterQuery contains query given by user in editText field.
     */
    private void onQueryChanged(String filterQuery) {
        AppExecutors.getInstance().backgroundThread().execute(() -> {
            // A new array list o add valid search elements which match the query.
            List<Article> articlesList = new ArrayList<>();
            // Loop through each element for match.
            for (Article article : mArticleList) {
                if (article.getArticleTitle().toLowerCase(Locale.getDefault()).contains(filterQuery)) {
                    articlesList.add(article);
                }
            }
            // Return values to mutable data.
            _mMutableSearchList.postValue(articlesList);
        });
    }

    /**
     * @return returns internally exposed list data of type liveData, any external objects can use
     * this return value.
     */
    private LiveData<List<Article>> getSearchList() {
        return _mLiveDataSearchList;
    }

    /**
     * @param filterQuery get user query and perform search operation using onQueryChanged() method
     *                    using the return value of liveData object from {@link #getSearchList()}.
     * @return return matching search results internally.
     */
    private MutableLiveData<List<Article>> onFilterChanged(String filterQuery) {
        onQueryChanged(filterQuery);
        return _mMutableSearchList;
    }
}
