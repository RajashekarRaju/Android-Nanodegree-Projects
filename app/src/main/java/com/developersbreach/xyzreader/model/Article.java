package com.developersbreach.xyzreader.model;

import com.developersbreach.xyzreader.repository.database.ArticleEntity;

public class Article extends ArticleEntity {

    private int mArticleId;
    private String mArticleAuthorName;

    public Article(int id, String authorName) {
        super(id, authorName);
        this.mArticleId = id;
        this.mArticleAuthorName = authorName;
    }

    @Override
    public int getArticleId() {
        return mArticleId;
    }

    @Override
    public String getArticleAuthorName() {
        return mArticleAuthorName;
    }
}
