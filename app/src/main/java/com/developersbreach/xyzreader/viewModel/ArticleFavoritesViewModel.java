package com.developersbreach.xyzreader.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.developersbreach.xyzreader.XYZReaderApp;
import com.developersbreach.xyzreader.repository.ArticleRepository;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;

import java.util.List;


public class ArticleFavoritesViewModel extends AndroidViewModel {

    private final ArticleRepository mRepository;
    private final MutableLiveData<List<FavoriteEntity>> _mMutableFavoriteList;

    public LiveData<List<FavoriteEntity>> favorites() {
        return getFavoriteList();
    }

    public ArticleFavoritesViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((XYZReaderApp) application).getRepository();
        _mMutableFavoriteList = mRepository.getObservableFavoriteList();
    }

    private LiveData<List<FavoriteEntity>> getFavoriteList() {
        return _mMutableFavoriteList;
    }

    public void deleteFavoriteArticleData(FavoriteEntity favoriteEntity) {
        mRepository.deleteFavoriteArticle(favoriteEntity);
    }
}
