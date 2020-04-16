package com.developersbreach.xyzreader.repository;

import androidx.lifecycle.MediatorLiveData;
import androidx.work.WorkManager;

import com.developersbreach.xyzreader.repository.database.ArticleDatabase;
import com.developersbreach.xyzreader.repository.database.entity.ArticleEntity;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;
import com.developersbreach.xyzreader.repository.network.JsonUtils;
import com.developersbreach.xyzreader.repository.network.ResponseBuilder;
import com.developersbreach.xyzreader.worker.ArticleRefreshWorker;

import java.io.IOException;
import java.util.List;


/**
 * This class acts as a layer between this app database data and network data.
 */
public class ArticleRepository {

    // Helps to create single instance for this class.
    private static ArticleRepository sINSTANCE;
    // Declare database variable to get access to abstract dao from the class.
    private static ArticleDatabase sDatabase;
    // List of ArticleEntity data of type MediatorLiveData to observe values from database.
    private MediatorLiveData<List<ArticleEntity>> mObservableArticleList;
    // List of FavoriteEntity data of type MediatorLiveData to observe values from database.
    private MediatorLiveData<List<FavoriteEntity>> mObservableFavoriteList;

    private boolean mFavoriteItemsSize;

    /**
     * @param database to get access to abstract dao from the database class.
     */
    private ArticleRepository(ArticleDatabase database) {
        sDatabase = database;
        getObservableArticleData(database);
        getObservableFavoriteData(database);
    }

    /**
     * Call {@link ArticleDatabase#articleDao()} from database and get all articles using query.
     * Convert those values to usable list of {@link ArticleEntity} by passing the source.
     * Finally post the value into observable field to observe changes.
     */
    private void getObservableArticleData(ArticleDatabase database) {
        mObservableArticleList = new MediatorLiveData<>();
        mObservableArticleList.addSource(database.articleDao().loadAllArticles(),
                mObservableArticleList::postValue);
    }

    /**
     * Call {@link ArticleDatabase#favoriteDao()} ()} from database and get all favorite articles
     * using query.
     * Convert those values to usable list of {@link FavoriteEntity} by passing the source.
     * Finally post the value into observable field to observe changes.
     */
    private void getObservableFavoriteData(ArticleDatabase database) {
        mObservableFavoriteList = new MediatorLiveData<>();
        mObservableFavoriteList.addSource(database.favoriteDao().loadAllFavoriteArticles(),
                mObservableFavoriteList::postValue);
    }

    /**
     * @param database get database to create an instance this repository class. Perform null check
     *                 and do not perform asynchronous, finally create single instance.
     * @return return a new single instance for this class.
     */
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
     * Get the list of articles from the database and get notified when the data changes.
     */
    public MediatorLiveData<List<ArticleEntity>> getObservableArticleList() {
        return mObservableArticleList;
    }

    /**
     * Get the list of favorite articles from the database and get notified when the data changes.
     */
    public MediatorLiveData<List<FavoriteEntity>> getObservableFavoriteList() {
        return mObservableFavoriteList;
    }

    /**
     * @param favoriteEntity contains data for article to insert a new article in to favorites table.
     *                       Only insert the data in background database thread without blocking the
     *                       UI and pass selected article for insertion.
     */
    public void insertFavoriteArticle(FavoriteEntity favoriteEntity) {
        AppExecutors.getInstance().databaseThread().execute(() ->
                sDatabase.favoriteDao().insertFavoriteArticle(favoriteEntity));
    }

    /**
     * @param favoriteEntity contains data for article to delete from the favorites table.
     *                       Only delete the data in background database thread without blocking the
     *                       UI and pass selected article for deleting.
     */
    public void deleteFavoriteArticle(FavoriteEntity favoriteEntity) {
        AppExecutors.getInstance().databaseThread().execute(() ->
                sDatabase.favoriteDao().deleteFavoriteArticle(favoriteEntity));
    }

    public void deleteAllFavoriteArticle() {
        AppExecutors.getInstance().databaseThread().execute(() ->
                sDatabase.favoriteDao().deleteAllFavorites());
    }

    public boolean getTotalFavoriteItems() {
        AppExecutors.getInstance().databaseThread().execute(() -> {
            final List<FavoriteEntity> list = sDatabase.favoriteDao().getFavoriteList();
            mFavoriteItemsSize = list.size() >= 1;
        });
        return mFavoriteItemsSize;
    }

    /**
     * This method gets all initial data necessary for the app.
     * 1. Start a new database thread {@link AppExecutors#databaseThread()} for this operation.
     * 2. Get json data from {@link JsonUtils#fetchArticleJsonData(String)} and assign those values
     * to list of {@link ArticleEntity}.
     * 3. Delete all existing data inside "favorites_table" if available using dao.
     * 4. Delete all existing data inside "articles_table" if available using dao.
     * 5. Insert new articles data fetched from the json.
     * <p>
     * All this operation is being handled by {@link WorkManager}.
     *
     * @see ArticleRefreshWorker#doWork(), where this method is being called.
     */
    public void refreshData() {
        AppExecutors.getInstance().databaseThread().execute(() -> {
            try {
                String responseString = ResponseBuilder.startResponse();
                List<ArticleEntity> articleEntityList = JsonUtils.fetchArticleJsonData(responseString);
                sDatabase.articleDao().deleteAllArticles();
                sDatabase.articleDao().insertArticles(articleEntityList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
