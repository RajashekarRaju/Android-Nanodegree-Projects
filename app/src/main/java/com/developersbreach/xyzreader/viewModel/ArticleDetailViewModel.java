package com.developersbreach.xyzreader.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.view.list.ArticleListFragment;

public class ArticleDetailViewModel extends AndroidViewModel {

    /**
     * This field is encapsulated, we used {@link MutableLiveData} because when the data is being
     * changed we will be updating StepsDetail with new values. And any externally exposed LiveData
     * can observe this changes.
     */
    private MutableLiveData<Article> _mMutableArticle;

    /**
     * fragment to observe changes. Data is observed once changes will be done internally.
     */
    public LiveData<Article> selectedArticle() {
        return _mMutableArticle;
    }

    /**
     * @param application provides application context for ViewModel.
     * @param article      parcel Recipe object with data for user selected recipe from
     *                    {@link ArticleListFragment}
     */
    public ArticleDetailViewModel(@NonNull Application application, Article article) {
        super(application);
        getMutableArticleDetailsData(article);
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
