package com.developersbreach.xyzreader.repository;

import androidx.lifecycle.MediatorLiveData;

import com.developersbreach.xyzreader.repository.database.ArticleDatabase;
import com.developersbreach.xyzreader.repository.database.entity.ArticleEntity;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;
import com.developersbreach.xyzreader.repository.network.JsonUtils;
import com.developersbreach.xyzreader.repository.network.ResponseBuilder;

import java.io.IOException;
import java.util.List;

public class ArticleRepository {

    private static ArticleRepository sINSTANCE;
    private static ArticleDatabase sDatabase;
    private MediatorLiveData<List<ArticleEntity>> mObservableArticleList;
    private MediatorLiveData<List<FavoriteEntity>> mObservableFavoriteList;

    private ArticleRepository(ArticleDatabase database) {
        sDatabase = database;
        getObservableArticleData(database);
        getObservableFavoriteData(database);
    }

    private void getObservableArticleData(ArticleDatabase database) {
        mObservableArticleList = new MediatorLiveData<>();
        mObservableArticleList.addSource(database.articleDao().loadAllArticles(),
                mObservableArticleList::postValue);
    }

    private void getObservableFavoriteData(ArticleDatabase database) {
        mObservableFavoriteList = new MediatorLiveData<>();
        mObservableFavoriteList.addSource(database.favoriteDao().loadAllFavoriteArticles(),
                mObservableFavoriteList::postValue);
    }

    public static ArticleRepository getRepositoryInstance(final ArticleDatabase database) {
        if (sINSTANCE == null) {
            synchronized (ArticleRepository.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = new ArticleRepository(database);
                }
            }
        }
        return sINSTANCE;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public MediatorLiveData<List<ArticleEntity>> getObservableArticleList() {
        return mObservableArticleList;
    }

    public MediatorLiveData<List<FavoriteEntity>> getObservableFavoriteList() {
        return mObservableFavoriteList;
    }

    public void insertFavoriteArticle(FavoriteEntity article) {
        AppExecutors.getInstance().databaseThread().execute(() ->
                sDatabase.favoriteDao().insertFavoriteArticle(article));
    }

    public void deleteFavoriteArticle(FavoriteEntity article) {
        AppExecutors.getInstance().databaseThread().execute(() ->
                sDatabase.favoriteDao().deleteFavoriteArticle(article));
    }

    public void refreshData() {
        AppExecutors.getInstance().databaseThread().execute(() -> {
            try {
                String responseString = ResponseBuilder.startResponse();
                List<ArticleEntity> articleEntityList = JsonUtils.fetchArticleJsonData(responseString);
                sDatabase.favoriteDao().deleteAllFavorites();
                sDatabase.articleDao().deleteAllArticles();
                sDatabase.articleDao().insertArticles(articleEntityList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
