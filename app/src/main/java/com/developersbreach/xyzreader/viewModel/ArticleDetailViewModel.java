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
import com.developersbreach.xyzreader.repository.database.ArticleEntity;
import com.developersbreach.xyzreader.utils.DataFormatting;
import com.developersbreach.xyzreader.view.list.ArticleListFragment;

import java.util.ArrayList;
import java.util.List;

public class ArticleDetailViewModel extends AndroidViewModel {

    /**
     * This field is encapsulated, we used {@link MutableLiveData} because when the data is being
     * changed we will be updating StepsDetail with new values. And any externally exposed LiveData
     * can observe this changes.
     */
    private MutableLiveData<Article> _mMutableArticle;
    private LiveData<List<Article>> _mMutableArticleList;

    /**
     * fragment to observe changes. Data is observed once changes will be done internally.
     */
    public LiveData<Article> selectedArticle() {
        return _mMutableArticle;
    }

    public LiveData<List<Article>> getMutableArticleList() {
        return _mMutableArticleList;
    }

    /**
     * @param application provides application context for ViewModel.
     * @param article     parcel Recipe object with data for user selected recipe from
     *                    {@link ArticleListFragment}
     */
    public ArticleDetailViewModel(@NonNull Application application, Article article) {
        super(application);
        final ArticleRepository repository = ((XYZReaderApp) application).getRepository();

        getMutableArticleDetailsData(article);
        getMutableArticleDetailListData(repository);
    }

    private void getMutableArticleDetailListData(ArticleRepository repository) {
        _mMutableArticleList = Transformations.switchMap(repository.getArticles(), input -> {
            MutableLiveData<List<Article>> listLiveData = new MutableLiveData<>();
            List<Article> articleList = new ArrayList<>();
            for (ArticleEntity articleEntity : input) {
                articleList.add(new Article(
                        articleEntity.getArticleId(),
                        articleEntity.getArticleTitle(),
                        articleEntity.getArticleAuthorName(),
                        articleEntity.getArticleBody(),
                        articleEntity.getArticleThumbnail(),
                        articleEntity.getArticlePublishedDate()
                ));
            }

            listLiveData.postValue(articleList);
            return listLiveData;
        });
    }

    /**
     * Create a new {@link MutableLiveData} after checking if is is empty, otherwise no need make
     * changes by adding new values.
     *
     * @param article has data for user selected Recipe with id.
     */
    private void getMutableArticleDetailsData(Article article) {
        if (_mMutableArticle == null) {
            _mMutableArticle = new MutableLiveData<>();
            // Add list to internally exposed data of RecipeDetails by calling postValue.
            _mMutableArticle.postValue(article);
        }
    }
}
