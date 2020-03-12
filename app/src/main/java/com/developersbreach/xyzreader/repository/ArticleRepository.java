package com.developersbreach.xyzreader.repository;

import androidx.lifecycle.LiveData;
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
    private static ArticleDatabase mDatabase;
    private final MediatorLiveData<List<ArticleEntity>> mObservableArticles;
    private final MediatorLiveData<List<FavoriteEntity>> mObservableFavoriteList;

    private ArticleRepository(ArticleDatabase database) {
        mDatabase = database;
        mObservableArticles = new MediatorLiveData<>();
        mObservableFavoriteList = new MediatorLiveData<>();

        mObservableArticles.addSource(database.articleDao().loadAllArticles(),
                mObservableArticles::postValue);

        mObservableFavoriteList.addSource(database.favoriteDao().loadFavouriteArticles(),
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
    public LiveData<List<ArticleEntity>> getArticles() {
        return mObservableArticles;
    }

    public MediatorLiveData<List<FavoriteEntity>> getFavoriteList() {
        return mObservableFavoriteList;
    }

    public void insertFavoriteArticle(FavoriteEntity article) {
        AppExecutors.getInstance().databaseThread().execute(() -> mDatabase.favoriteDao().insertFavorites(article));
    }

    public void deleteFavoriteArticle(FavoriteEntity article) {
        AppExecutors.getInstance().databaseThread().execute(() -> mDatabase.favoriteDao().deleteFavorite(article));
    }

    public void refreshData() {
        AppExecutors.getInstance().databaseThread().execute(() -> {
            try {
                String responseString = ResponseBuilder.startResponse();
                List<ArticleEntity> articleEntityList = JsonUtils.fetchArticleJsonData(responseString);
                mDatabase.favoriteDao().deleteAllFavorites();
                mDatabase.articleDao().deleteAllArticles();
                mDatabase.articleDao().insertArticles(articleEntityList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
