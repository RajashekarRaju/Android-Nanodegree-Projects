package com.developersbreach.xyzreader.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
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

    private final LiveData<List<Article>> mArticleList;
    private final MutableLiveData<List<Article>> mSearchList;
    private List<Article> mList;

    public SearchArticleViewModel(@NonNull Application application) {
        super(application);
        ArticleRepository repository = ((XYZReaderApp) application).getRepository();
        mSearchList = new MutableLiveData<>();
        mList = new ArrayList<>();

        mArticleList = Transformations.switchMap(repository.getArticles(), input -> {
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
            mList = articleList;
            listLiveData.postValue(articleList);
            return listLiveData;
        });
    }

    public LiveData<List<Article>> getArticleList() {
        return mArticleList;
    }

    private void onQueryChanged(String filterQuery) {
        AppExecutors.getInstance().backgroundThread().execute(() -> {
            List<Article> articlesList = new ArrayList<>();
            for (Article currentArticle : mList) {
                if (currentArticle.getArticleTitle().toLowerCase(Locale.getDefault()).contains(filterQuery)) {
                    articlesList.add(currentArticle);
                }
            }
            mSearchList.postValue(articlesList);
        });
    }

    public MutableLiveData<List<Article>> onFilterChanged(String filterQuery) {
        onQueryChanged(filterQuery);
        return mSearchList;
    }
}
