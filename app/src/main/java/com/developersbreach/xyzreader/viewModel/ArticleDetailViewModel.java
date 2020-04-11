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
import com.developersbreach.xyzreader.model.DataObjectConverter;
import com.developersbreach.xyzreader.repository.ArticleRepository;
import com.developersbreach.xyzreader.repository.database.entity.ArticleEntity;
import com.developersbreach.xyzreader.view.detail.ArticleDetailFragment;
import com.developersbreach.xyzreader.view.detail.DetailViewPagerAdapter;
import com.developersbreach.xyzreader.view.favorite.ArticleFavoritesFragment;
import com.developersbreach.xyzreader.view.list.ArticleListFragment;
import com.developersbreach.xyzreader.view.search.SearchArticleFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * This viewModel is responsible for preparing and managing the data for fragment associated class
 * {@link ArticleDetailFragment} which shows user searchable articles.
 * This ViewModel expose the data for user selected Article via {@link LiveData} and fragment
 * observes the changes.
 * This helps us save fragment data and keeps UI simple.
 * <p>
 * This class performs gets data from {@link ArticleRepository} class, for that reason we are
 * extending this to {@link AndroidViewModel} get be aware of application context. And get reference
 * to repository and resources we need access to.
 * <p>
 * This viewModel is set to data binding, setters are declared in {@link R.layout#item_article_adapter}
 * and also called in {@link DetailViewPagerAdapter}.
 */
public class ArticleDetailViewModel extends AndroidViewModel {

    /**
     * @return returns externally exposed {@link LiveData} object of List of type {@link Article}
     * allowing fragment to observe changes. Data is observed once changes will be done internally.
     * ViewPager2 uses this list to scroll horizontally between articles.
     */
    public LiveData<List<Article>> articles() {
        return getArticleList();
    }

    /**
     * @return returns externally exposed {@link LiveData} object of List of type {@link Boolean}
     * allowing fragment to observe changes. Data is observed once changes will be done internally.
     * If fragment we are looking for is true, the this method returns true. Else returns false.
     */
    public LiveData<Boolean> fragmentName() {
        return getFragmentName();
    }

    /**
     * @return returns externally exposed {@link LiveData} object of List of type {@link Article}
     * allowing fragment to observe changes. Data is observed once changes will be done internally.
     * ViewPager2 will be disabled, and simultaneously fragment shows only single article data from
     * where user selected this article tto show up.
     */
    public LiveData<Article> selectedArticle() {
        return getSelectedArticle();
    }

    /**
     * This field is encapsulated, we used {@link MutableLiveData} because when the data is being
     * changed we will be updating StepsDetail with new values. And any externally exposed LiveData
     * can observe this changes for the current article shown from list.
     */
    private MutableLiveData<Article> _mMutableSelectedArticle;

    /**
     * This field is encapsulated, we used {@link LiveData} because when the data is being
     * changed we will be updating StepsDetail with new values. And any externally exposed LiveData
     * can observe this changes for the current article shown from list.
     */
    private LiveData<List<Article>> _mMutableArticleList;

    /**
     * This field is encapsulated, we used {@link MutableLiveData} because when the data is being
     * changed we will be updating StepsDetail with new values. And any externally exposed LiveData
     * can observe this changes for the current article shown from list.
     */
    private MutableLiveData<Boolean> _mMutableFragmentName;

    /**
     * @param application  provides application context for ViewModel.
     * @param article      parcel Article object with data for user selected article from
     *                     {@link ArticleListFragment}, {@link ArticleFavoritesFragment} and
     *                     {@link SearchArticleFragment}.
     * @param fragmentName name contains string value which is name of the fragment.
     */
    public ArticleDetailViewModel(@NonNull Application application, Article article, String fragmentName) {
        super(application);
        // Declare repository and get reference to it for calling methods inside it with application
        // in this class AndroidViewModel constructor.
        // Get a new instance from this app repository using application context.
        final ArticleRepository repository = ((XYZReaderApp) application).getRepository();
        getMutableArticleDetailListData(repository);
        getMutableFragmentNameData(fragmentName);
        getMutableSelectedArticleDetailsData(article);
    }

    /**
     * @param repository get access to source value and return it to internal liveData.
     */
    private void getMutableArticleDetailListData(ArticleRepository repository) {
        // Get valid source from repository which are observable return types.
        final MediatorLiveData<List<ArticleEntity>> source = repository.getObservableArticleList();
        // Using switchMaps transform the object with required return type with source.
        _mMutableArticleList = Transformations.switchMap(source, articleEntityList -> {
            MutableLiveData<List<Article>> mutableArticlesLiveData = new MutableLiveData<>();
            // Declare a new array list to add values from source using maps.
            List<Article> articleList = new ArrayList<>();
            // Convert objects from ArticleEntityList to ArticleList.
            DataObjectConverter.articleEntityToArticle(articleEntityList, articleList);
            // Since the value has been changed after mapping list. Pass the result for liveData.
            mutableArticlesLiveData.postValue(articleList);
            // Return the new list of articles of type MutableLiveData.
            return mutableArticlesLiveData;
        });
    }

    /**
     * @param fragmentName contains string value, which is name of the fragment.
     *                     If the fragment name which we are looking for is found, we return true.
     *                     Else we return false.
     */
    private void getMutableFragmentNameData(String fragmentName) {
        _mMutableFragmentName = new MutableLiveData<>();
        if (fragmentName.equals(ArticleListFragment.class.getSimpleName())) {
            _mMutableFragmentName.postValue(true);
        } else if (fragmentName.equals(SearchArticleFragment.class.getSimpleName())) {
            _mMutableFragmentName.postValue(false);
        } else if (fragmentName.equals(ArticleFavoritesFragment.class.getSimpleName())) {
            _mMutableFragmentName.postValue(false);
        }
    }

    /**
     * Create a new {@link MutableLiveData} after checking if is is empty, otherwise no need make
     * changes by adding new values.
     *
     * @param article has data for user selected Article.
     */
    private void getMutableSelectedArticleDetailsData(Article article) {
        if (_mMutableSelectedArticle == null) {
            _mMutableSelectedArticle = new MutableLiveData<>();
            // Add list to internally exposed data of ArticleDetails by calling postValue.
            _mMutableSelectedArticle.postValue(article);
        }
    }

    /**
     * @return returns internally exposed list data of type liveData, any external objects can use
     * this return value.
     */
    private LiveData<List<Article>> getArticleList() {
        return _mMutableArticleList;
    }

    /**
     * @return returns internally exposed boolean value data of type liveData, any external objects
     * can use this return value.
     */
    private MutableLiveData<Boolean> getFragmentName() {
        return _mMutableFragmentName;
    }

    /**
     * @return returns internally exposed selected article data of type liveData, any external
     * objects can use this return value.
     */
    private LiveData<Article> getSelectedArticle() {
        return _mMutableSelectedArticle;
    }
}
