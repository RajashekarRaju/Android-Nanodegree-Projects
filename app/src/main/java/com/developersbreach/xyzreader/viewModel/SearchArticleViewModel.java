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
import com.developersbreach.xyzreader.repository.AppExecutors;
import com.developersbreach.xyzreader.repository.ArticleRepository;
import com.developersbreach.xyzreader.repository.database.entity.ArticleEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchArticleViewModel extends AndroidViewModel {

    private final LiveData<List<Article>> _mLiveDataSearchList;
    private final MutableLiveData<List<Article>> _mMutableSearchList;
    private List<Article> mArticleList;

    public LiveData<List<Article>> articles() {
        return getSearchList();
    }

    public LiveData<List<Article>> filter(String query) {
        return onFilterChanged(query);
    }

    public SearchArticleViewModel(@NonNull Application application) {
        super(application);
        ArticleRepository repository = ((XYZReaderApp) application).getRepository();
        final MediatorLiveData<List<ArticleEntity>> source = repository.getObservableArticleList();
        _mMutableSearchList = new MutableLiveData<>();
        mArticleList = new ArrayList<>();
        _mLiveDataSearchList = Transformations.switchMap(source, articleEntityList -> {
            MutableLiveData<List<Article>> mutableArticleLiveData = new MutableLiveData<>();
            List<Article> articleList = new ArrayList<>();
            Article.articleEntityToArticle(articleEntityList, articleList);
            mArticleList = articleList;
            mutableArticleLiveData.postValue(articleList);
            return mutableArticleLiveData;
        });
    }

    private void onQueryChanged(String filterQuery) {
        AppExecutors.getInstance().backgroundThread().execute(() -> {
            List<Article> articlesList = new ArrayList<>();
            for (Article article : mArticleList) {
                if (article.getArticleTitle().toLowerCase(Locale.getDefault()).contains(filterQuery)) {
                    articlesList.add(article);
                }
            }
            _mMutableSearchList.postValue(articlesList);
        });
    }

    private LiveData<List<Article>> getSearchList() {
        return _mLiveDataSearchList;
    }

    private MutableLiveData<List<Article>> onFilterChanged(String filterQuery) {
        onQueryChanged(filterQuery);
        return _mMutableSearchList;
    }
}
