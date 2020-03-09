package com.developersbreach.xyzreader.repository.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.developersbreach.xyzreader.repository.database.entity.ArticleEntity;

import java.util.List;

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM articles_table")
    LiveData<List<ArticleEntity>> loadAllArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticles(List<ArticleEntity> articleEntityList);

    @Query("DELETE FROM articles_table")
    void deleteAllArticles();
}
