package com.developersbreach.xyzreader.repository.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.developersbreach.xyzreader.repository.database.ArticleDatabase;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;

import java.util.List;


/**
 * Separate dao interface class for table named "favorites_table". It is recommended to use separate
 * dao class for each table declared in database.
 * <p>
 * Mark this class {@link Dao} for accessing Data Objects from app database {@link ArticleDatabase}.
 * We access objects from database table named as "favorites_table".
 *
 * @see FavoriteEntity for table declaration.
 */
@Dao
public interface FavoriteDao {

    /**
     * @return queries list of  favorite articles of type {@link LiveData} from favorites_table.
     */
    @Query("SELECT * FROM favorites_table")
    LiveData<List<FavoriteEntity>> loadAllFavoriteArticles();

    @Query("SELECT * FROM favorites_table WHERE column_favorite_id = :favoriteId")
    int getFavoriteById(int favoriteId);

    /**
     * @param favoriteEntity inserts single favorite article into table favorites_table.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteArticle(FavoriteEntity favoriteEntity);

    /**
     * @param favoriteEntity deletes single favorite article into table favorites_table.
     */
    @Delete
    void deleteFavoriteArticle(FavoriteEntity favoriteEntity);

    /**
     * Queries all data from favorites_table to perform delete operation.
     */
    @Query("DELETE FROM favorites_table")
    void deleteAllFavorites();

    @Query("SELECT * FROM favorites_table")
    List<FavoriteEntity> getFavoriteList();
}
