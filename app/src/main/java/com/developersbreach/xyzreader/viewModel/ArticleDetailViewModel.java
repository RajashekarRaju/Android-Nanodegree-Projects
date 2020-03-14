package com.developersbreach.xyzreader.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.developersbreach.xyzreader.XYZReaderApp;
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.repository.ArticleRepository;
import com.developersbreach.xyzreader.repository.database.entity.ArticleEntity;
import com.developersbreach.xyzreader.view.favorite.ArticleFavoritesFragment;
import com.developersbreach.xyzreader.view.list.ArticleListFragment;
import com.developersbreach.xyzreader.view.search.SearchArticleFragment;

import java.util.ArrayList;
import java.util.List;

public class ArticleDetailViewModel extends AndroidViewModel {

    /**
     * This field is encapsulated, we used {@link MutableLiveData} because when the data is being
     * changed we will be updating StepsDetail with new values. And any externally exposed LiveData
     * can observe this changes.
     */
    private LiveData<List<Article>> _mMutableArticleList;
    private MutableLiveData<Boolean> _mMutableFragmentName;
    private MutableLiveData<Article> _mMutableSelectedArticle;

    public LiveData<List<Article>> articles() {
        return getMutableArticleList();
    }

    public LiveData<Boolean> fragmentName() {
        return getMutableFragmentName();
    }

    public LiveData<Article> selectedArticle() {
        return getSelectedArticle();
    }

    /**
     * @param application provides application context for ViewModel.
     * @param article     parcel Recipe object with data for user selected recipe from
     *                    {@link ArticleListFragment}
     * @param fragmentName name
     */
    public ArticleDetailViewModel(@NonNull Application application, Article article, String fragmentName) {
        super(application);
        final ArticleRepository repository = ((XYZReaderApp) application).getRepository();
        getMutableArticleDetailListData(repository);
        getMutableFragmentNameData(fragmentName);
        getMutableSelectedArticleDetailsData(article);
    }

    private void getMutableArticleDetailListData(ArticleRepository repository) {
        final MediatorLiveData<List<ArticleEntity>> source = repository.getObservableArticleList();
        _mMutableArticleList = Transformations.switchMap(source, articleEntityList -> {
            MutableLiveData<List<Article>> mutableArticlesLiveData = new MutableLiveData<>();
            List<Article> articleList = new ArrayList<>();
            Article.articleEntityToArticle(articleEntityList, articleList);
            mutableArticlesLiveData.postValue(articleList);
            return mutableArticlesLiveData;
        });
    }

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
     * @param article has data for user selected Recipe with id.
     */
    private void getMutableSelectedArticleDetailsData(Article article) {
        if (_mMutableSelectedArticle == null) {
            _mMutableSelectedArticle = new MutableLiveData<>();
            // Add list to internally exposed data of RecipeDetails by calling postValue.
            _mMutableSelectedArticle.postValue(article);
        }
    }

    /**
     * fragment to observe changes. Data is observed once changes will be done internally.
     */
    private LiveData<List<Article>> getMutableArticleList() {
        return _mMutableArticleList;
    }

    private MutableLiveData<Boolean> getMutableFragmentName() {
        return _mMutableFragmentName;
    }
    private LiveData<Article> getSelectedArticle() {
        return _mMutableSelectedArticle;
    }
}
