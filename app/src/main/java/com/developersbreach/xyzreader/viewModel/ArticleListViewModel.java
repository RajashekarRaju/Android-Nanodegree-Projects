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

    private final LiveData<List<Article>> mArticleList;
    private ArticleRepository mRepository;

    public ArticleListViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((XYZReaderApp) application).getRepository();
        LiveData<List<ArticleEntity>> liveArticleEntityData = mRepository.getArticles();

        mArticleList = Transformations.switchMap(liveArticleEntityData, input -> {
            MutableLiveData<List<Article>> listLiveData = new MutableLiveData<>();
            List<Article> articleList = new ArrayList<>();
            for (ArticleEntity article : input) {
                articleList.add(new Article(
                        article.getArticleId(),
                        article.getArticleTitle(),
                        article.getArticleAuthorName(),
                        article.getArticleBody(),
                        article.getArticleThumbnail(),
                        article.getArticlePublishedDate()
                ));
            }

            listLiveData.postValue(articleList);

            return listLiveData;
        });
    }

    public LiveData<List<Article>> getArticleList() {
        return mArticleList;
    }

    public void insertFavoriteData(Article article) {
        FavoriteEntity entity = new FavoriteEntity(
                article.getArticleId(),
                article.getArticleTitle(),
                article.getArticleAuthorName(),
                article.getArticleBody(),
                article.getArticleThumbnail(),
                article.getArticlePublishedDate()
        );
        mRepository.insertFavoriteArticle(entity);
    }
}
