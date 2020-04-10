package com.developersbreach.xyzreader.view.settings;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.developersbreach.xyzreader.XYZReaderApp;
import com.developersbreach.xyzreader.repository.ArticleRepository;

class SettingsViewModel extends AndroidViewModel {

    /**
     * Declare repository and get reference to it for calling methods inside it with application
     * in this class AndroidViewModel constructor.
     */
    private final ArticleRepository mRepository;

    /**
     * @param application provides application context for ViewModel. With this context get reference
     *                    to other classes like repository.
     */
    SettingsViewModel(@NonNull Application application) {
        super(application);
        // Get a new instance from this app repository using application context.
        mRepository = ((XYZReaderApp) application).getRepository();
    }

    void deleteFavoriteArticleData() {
        mRepository.deleteAllFavoriteArticle();
    }

    boolean checkFavoritesBeforeDelete() {
        return mRepository.getTotalFavoriteItems();
    }

    void refreshData() {
        mRepository.refreshData();
    }
}
