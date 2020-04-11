package com.developersbreach.xyzreader.model;

import android.os.Parcel;
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
public class Article implements Parcelable {

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
    public int getArticleId() {
        return mArticleId;
    }

    public String getArticleTitle() {
        return mArticleTitle;
    }

    public String getArticleAuthorName() {
        return mArticleAuthorName;
    }

    public String getArticleBody() {
        return mArticleBody;
    }

    public String getArticleThumbnail() {
        return mArticleThumbnail;
    }

    public String getArticlePublishedDate() {
        return mArticlePublishedDate;
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
    public static final DiffUtil.ItemCallback<Article>
            DIFF_ITEM_CALLBACK = new DiffUtil.ItemCallback<Article>() {

        @Override
        public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.getArticleId() == newItem.getArticleId();
        }
    };

    private Article(Parcel in) {
        mArticleId = in.readInt();
        mArticleTitle = in.readString();
        mArticleAuthorName = in.readString();
        mArticleBody = in.readString();
        mArticleThumbnail = in.readString();
        mArticlePublishedDate = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mArticleId);
        dest.writeString(mArticleTitle);
        dest.writeString(mArticleAuthorName);
        dest.writeString(mArticleBody);
        dest.writeString(mArticleThumbnail);
        dest.writeString(mArticlePublishedDate);
    }
}
