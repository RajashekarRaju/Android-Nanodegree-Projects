package com.developersbreach.xyzreader.view.favorite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.developersbreach.xyzreader.XYZReaderApp;
import com.developersbreach.xyzreader.repository.ArticleRepository;
import com.developersbreach.xyzreader.repository.database.FavoriteEntity;

import java.util.List;


public class ArticleFavoritesViewModel extends AndroidViewModel {

    private MutableLiveData<List<FavoriteEntity>> mFavoriteList;
    private ArticleRepository mRepository;

    public ArticleFavoritesViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((XYZReaderApp) application).getRepository();
        mFavoriteList = mRepository.getFavoriteList();
    }

    LiveData<List<FavoriteEntity>> getFavoriteList() {
        return mFavoriteList;
    }

    void deleteFavoriteData(FavoriteEntity favoriteEntity) {
        mRepository.deleteFavoriteArticle(favoriteEntity);
    }
}
