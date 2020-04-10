package com.developersbreach.xyzreader.model;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.DiffUtil;

import com.developersbreach.xyzreader.repository.database.entity.ArticleEntity;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;
import com.developersbreach.xyzreader.repository.network.JsonUtils;
import com.developersbreach.xyzreader.view.detail.DetailViewPagerAdapter;
import com.developersbreach.xyzreader.view.list.ArticleAdapter;
import com.developersbreach.xyzreader.view.search.SearchAdapter;

import java.util.List;
import java.util.Objects;


/**
 * {@link ArticleEntity} --> {@link Article} --> {@link FavoriteEntity}
 * <p>
 * Data class which gets data from {@link ArticleEntity}, which returns objects of article properties.
 * This class is extended to get values into object properties which are received first by entity
 * class {@link ArticleEntity} from json {@link JsonUtils#fetchArticleJsonData(String)}.
 * <p>
 * The purpose of this class is to pass as argument between fragments using SafeArgs by avoiding
 * database object properties declared in entity classes. This class behaves as Parcelable since
 * the extended class ArticleEntity has been implemented as {@link Parcelable} already.
 * <p>
 * Passed as list of properties and are set to adapter classes @See {@link ArticleAdapter},
 * {@link SearchAdapter} and {@link DetailViewPagerAdapter}.
 * <p>
 * This class also contains object converters. Which transforms from
 * {@link FavoriteEntity} to {@link Article},
 * {@link List<ArticleEntity>} to {@link List<Article>}
 * {@link Article} to {@link FavoriteEntity}. using with or without
 * {@link Transformations#switchMap(LiveData, Function)}
 */
public class Article extends ArticleEntity {

    // Article property of type int with unique article id.
    private final int mArticleId;
    // Article property of type String, title of the article.
    private final String mArticleTitle;
    // Article property of type String, author of the article.
    private final String mArticleAuthorName;
    // Article property of type String, body of the article.
    private final String mArticleBody;
    // Article property of type String, thumbnail of the article.
    private final String mArticleThumbnail;
    // Article property of type String, published date of the article.
    private final String mArticlePublishedDate;

    /**
     * Class constructor for making data class into reusable objects of articles.
     */
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

    ////////////////////////// GETTERS ///////////////////////////////

    /**
     * @return returns data of each article property which are generated here since we extended this
     * class from {@link ArticleEntity}
     */
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

    ////////////////////////// SETTERS ///////////////////////////////

    @Override
    public void setArticleId(int id) {
        super.setArticleId(id);
    }

    @Override
    public void setArticleTitle(String articleTitle) {
        super.setArticleTitle(articleTitle);
    }

    @Override
    public void setArticleAuthorName(String authorName) {
        super.setArticleAuthorName(authorName);
    }

    @Override
    public void setArticleBody(String articleBody) {
        super.setArticleBody(articleBody);
    }

    @Override
    public void setArticleThumbnail(String articleThumbnail) {
        super.setArticleThumbnail(articleThumbnail);
    }

    @Override
    public void setArticlePublishedDate(String articlePublishedDate) {
        super.setArticlePublishedDate(articlePublishedDate);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Article article = (Article) obj;
        return Objects.equals(mArticleId, article.mArticleId)
                && Objects.equals(mArticleTitle, article.mArticleTitle)
                && Objects.equals(mArticleAuthorName, article.mArticleAuthorName)
                && Objects.equals(mArticleBody, article.mArticleBody)
                && Objects.equals(mArticleThumbnail, article.mArticleThumbnail)
                && Objects.equals(mArticlePublishedDate, article.mArticlePublishedDate);
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the list of {@link Article}
     * has been updated.
     */
    public static final DiffUtil.ItemCallback<Article> DIFF_ITEM_CALLBACK =
            new DiffUtil.ItemCallback<Article>() {

                @Override
                public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
                    return oldItem.equals(newItem);
                }

                @Override
                public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
                    return oldItem.getArticleId() == newItem.getArticleId();
                }
            };

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
     *                          {@link List<ArticleEntity>} to {@link List<Article>}. This method
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
