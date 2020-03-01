package com.developersbreach.xyzreader.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.developersbreach.xyzreader.repository.database.ArticleDatabase;
import com.developersbreach.xyzreader.repository.database.ArticleEntity;

import java.util.List;

public class ArticleRepository {

    private static ArticleRepository sINSTANCE;
    private final ArticleDatabase mDatabase;
    private MediatorLiveData<List<ArticleEntity>> mObservableArticles;

    public ArticleRepository(ArticleDatabase database) {
        this.mDatabase = database;
        mObservableArticles = new MediatorLiveData<>();

        mObservableArticles.addSource(mDatabase.articleDao().loadAllArticles(), new Observer<List<ArticleEntity>>() {
            @Override
            public void onChanged(List<ArticleEntity> articleEntityList) {
                mObservableArticles.postValue(articleEntityList);
            }
        });
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

    public LiveData<ArticleEntity> getArticleById(final int articleId) {
        return mDatabase.articleDao().getArticleById(articleId);
    }
}
