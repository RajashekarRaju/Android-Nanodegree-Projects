package com.developersbreach.xyzreader.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.developersbreach.xyzreader.XYZReaderApp;
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.repository.ArticleRepository;
import com.developersbreach.xyzreader.repository.database.entity.ArticleEntity;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;

import java.util.ArrayList;
import java.util.List;

public class ArticleListViewModel extends AndroidViewModel {

    private final LiveData<List<Article>> _mMutableArticleList;
    private final ArticleRepository mRepository;

    public LiveData<List<Article>> articles() {
        return getArticleList();
    }

    public ArticleListViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((XYZReaderApp) application).getRepository();
        LiveData<List<ArticleEntity>> source = mRepository.getObservableArticleList();

        _mMutableArticleList = Transformations.switchMap(source, articleEntityList -> {
            MutableLiveData<List<Article>> mutableArticlesLiveData = new MutableLiveData<>();
            List<Article> articleList = new ArrayList<>();
            Article.articleEntityToArticle(articleEntityList, articleList);
            mutableArticlesLiveData.postValue(articleList);
            return mutableArticlesLiveData;
        });
    }

    private LiveData<List<Article>> getArticleList() {
        return _mMutableArticleList;
    }

    public void insertFavoriteArticleData(Article article) {
        FavoriteEntity entity = Article.articleToFavoriteArticle(article);
        mRepository.insertFavoriteArticle(entity);
    }
}
