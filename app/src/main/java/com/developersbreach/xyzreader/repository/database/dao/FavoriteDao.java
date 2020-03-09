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

    @Query("SELECT * FROM favourite_table")
    LiveData<List<FavoriteEntity>> loadFavouriteArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorites(FavoriteEntity article);

    @Delete
    void deleteFavorite(FavoriteEntity article);

    @Query("DELETE FROM favourite_table")
    void deleteAllFavorites();
}
