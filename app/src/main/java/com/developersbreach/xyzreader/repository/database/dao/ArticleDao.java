package com.developersbreach.xyzreader.repository.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.developersbreach.xyzreader.repository.database.ArticleDatabase;
import com.developersbreach.xyzreader.repository.database.entity.ArticleEntity;

import java.util.List;

/**
 * Mark this class {@link Dao} for accessing Data Objects from app database {@link ArticleDatabase}.
 * We access objects from database table named as "articles_table".
 *
 * @see ArticleEntity for table declaration.
 */
@Dao
public interface ArticleDao {

    /**
     * @return queries list of articles of type {@link LiveData} from articles_table.
     */
    @Query("SELECT * FROM articles_table")
    LiveData<List<ArticleEntity>> loadAllArticles();

    /**
     * @param articleEntityList inserts list of articles into table articles_table.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticles(List<ArticleEntity> articleEntityList);

    /**
     * Queries all data from articles_table to perform delete operation.
     */
    @Query("DELETE FROM articles_table")
    void deleteAllArticles();
}
