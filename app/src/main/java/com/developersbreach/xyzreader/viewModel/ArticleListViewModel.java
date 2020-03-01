package com.developersbreach.xyzreader.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.developersbreach.xyzreader.XYZReaderApp;
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.repository.ArticleRepository;
import com.developersbreach.xyzreader.repository.database.ArticleEntity;

import java.util.ArrayList;
import java.util.List;

public class ArticleListViewModel extends AndroidViewModel {

    private LiveData<List<Article>> mArticleList;

    public ArticleListViewModel(@NonNull Application application) {
        super(application);
        final ArticleRepository repository = ((XYZReaderApp) application).getRepository();
        LiveData<List<ArticleEntity>> liveArticleEntityData = repository.getArticles();

        mArticleList = Transformations.switchMap(liveArticleEntityData, new Function<List<ArticleEntity>,
                LiveData<List<Article>>>() {
            @Override
            public LiveData<List<Article>> apply(List<ArticleEntity> input) {
                MutableLiveData<List<Article>> listLiveData = new MutableLiveData<>();
                List<Article> articleList = new ArrayList<>();
                for (ArticleEntity articleEntity : input) {
                    articleList.add(new Article(articleEntity.getArticleId(), articleEntity.getArticleAuthorName()));
                }
                listLiveData.postValue(articleList);
                return listLiveData;
            }
        });
    }

    public LiveData<List<Article>> getArticleList() {
        return mArticleList;
    }
}
