package com.developersbreach.xyzreader.repository.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM articles_table")
    LiveData<List<ArticleEntity>> loadAllArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllArticles(List<ArticleEntity> articleEntityList);

    @Query("SELECT * FROM articles_table WHERE column_article_id = :articleId")
    LiveData<ArticleEntity> getArticleById(int articleId);
}
