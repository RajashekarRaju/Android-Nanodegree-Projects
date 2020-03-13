package com.developersbreach.xyzreader.repository.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorites_table")
    LiveData<List<FavoriteEntity>> loadAllFavoriteArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteArticle(FavoriteEntity article);

    @Delete
    void deleteFavoriteArticle(FavoriteEntity article);

    @Query("DELETE FROM favorites_table")
    void deleteAllFavorites();
}
