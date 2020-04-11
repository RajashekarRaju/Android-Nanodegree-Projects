package com.developersbreach.xyzreader.model;

import com.developersbreach.xyzreader.repository.database.entity.ArticleEntity;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;

import java.util.List;

public class DataObjectConverter {

    /**
     * @param favoriteEntity takes class {@link FavoriteEntity} to get access for all objects inside.
     * @return returns new Object values for this existing class.
     * {@link FavoriteEntity} to {@link Article}.
     */
    public static Article favoriteArticleToArticle(FavoriteEntity favoriteEntity) {
        return new Article(
                favoriteEntity.getArticleId(),
                favoriteEntity.getArticleTitle(),
                favoriteEntity.getArticleAuthorName(),
                favoriteEntity.getArticleBody(),
                favoriteEntity.getArticleThumbnail(),
                favoriteEntity.getArticlePublishedDate());
    }

    /**
     * @param articleEntityList takes list of {@link ArticleEntity} to get access for all objects.
     * @param articleList       takes list of {@link Article} to get access for all objects.
     *                          {@link List <ArticleEntity>} to {@link List<Article>}. This method
     *                          won't return any value.
     */
    public static void articleEntityToArticle(List<ArticleEntity> articleEntityList,
                                              List<Article> articleList) {
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

    /**
     * @param article takes class {@link Article} to get access for all objects inside.
     * @return returns new Object values from this existing class to {@link FavoriteEntity}.
     * {@link Article} to {@link FavoriteEntity}.
     */
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
