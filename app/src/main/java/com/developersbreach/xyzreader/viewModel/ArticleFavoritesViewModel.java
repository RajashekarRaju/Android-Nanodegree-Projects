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
    private final MutableLiveData<List<FavoriteEntity>> mFavoriteList;

    public ArticleFavoritesViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((XYZReaderApp) application).getRepository();
        mFavoriteList = mRepository.getFavoriteList();
    }

    public LiveData<List<FavoriteEntity>> getFavoriteList() {
        return mFavoriteList;
    }

    public void deleteFavoriteData(FavoriteEntity favoriteEntity) {
        mRepository.deleteFavoriteArticle(favoriteEntity);
    }
}
