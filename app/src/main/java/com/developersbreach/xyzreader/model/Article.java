package com.developersbreach.xyzreader.model;

import com.developersbreach.xyzreader.repository.database.entity.ArticleEntity;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;

import java.util.List;

public class Article extends ArticleEntity {

    private final int mArticleId;
    private final String mArticleTitle;
    private final String mArticleAuthorName;
    private final String mArticleBody;
    private final String mArticleThumbnail;
    private final String mArticlePublishedDate;

    public Article(int id, String title, String authorName, String body, String thumbnail,
                   String publishedDate) {
        super(id, title, authorName, body, thumbnail, publishedDate);

        this.mArticleId = id;
        this.mArticleTitle = title;
        this.mArticleAuthorName = authorName;
        this.mArticleBody = body;
        this.mArticleThumbnail = thumbnail;
        this.mArticlePublishedDate = publishedDate;
    }

    @Override
    public int getArticleId() {
        return mArticleId;
    }

    @Override
    public String getArticleTitle() {
        return mArticleTitle;
    }

    @Override
    public String getArticleAuthorName() {
        return mArticleAuthorName;
    }

    @Override
    public String getArticleBody() {
        return mArticleBody;
    }

    @Override
    public String getArticleThumbnail() {
        return mArticleThumbnail;
    }

    @Override
    public String getArticlePublishedDate() {
        return mArticlePublishedDate;
    }

    public static Article favoriteArticleToArticle(FavoriteEntity favoriteEntity) {
        return new Article(
                favoriteEntity.getArticleId(),
                favoriteEntity.getArticleTitle(),
                favoriteEntity.getArticleAuthorName(),
                favoriteEntity.getArticleBody(),
                favoriteEntity.getArticleThumbnail(),
                favoriteEntity.getArticlePublishedDate());
    }

    public static void articleEntityToArticle(List<ArticleEntity> articleEntityList, List<Article> articleList) {
        for (ArticleEntity articleEntity : articleEntityList) {
            articleList.add(new Article(
                    articleEntity.getArticleId(),
                    articleEntity.getArticleTitle(),
                    articleEntity.getArticleAuthorName(),
                    articleEntity.getArticleBody(),
                    articleEntity.getArticleThumbnail(),
                    articleEntity.getArticlePublishedDate()
            ));
        }
    }

    public static FavoriteEntity articleToFavoriteArticle(Article article) {
        return new FavoriteEntity(
                article.getArticleId(),
                article.getArticleTitle(),
                article.getArticleAuthorName(),
                article.getArticleBody(),
                article.getArticleThumbnail(),
                article.getArticlePublishedDate()
        );
    }
}
