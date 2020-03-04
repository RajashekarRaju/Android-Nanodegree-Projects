package com.developersbreach.xyzreader.bindingAdapter;

import android.graphics.Bitmap;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.developersbreach.xyzreader.utils.DataFormatting;
import com.google.android.material.card.MaterialCardView;

public class ArticleListBindingAdapter {

    /**
     * When recipeName is used on TextView, the method bindRecipeName is called.
     *
     * @param textView   a view which we use to set a String value to it.
     * @param authorName contains String value to be set to TextView.
     */
    @BindingAdapter("authorName")
    public static void bindAuthorName(TextView textView, String authorName) {
        textView.setText(authorName);
    }

    @BindingAdapter("articleTitle")
    public static void bindArticleTitle(TextView textView, String title) {
        textView.setText(title);
    }

    @BindingAdapter("articlePublishedDate")
    public static void bindArticlePublishedDate(TextView textView, String publishedDate) {
        Spanned result = DataFormatting.formatDate(publishedDate);
        textView.setText(result);
    }

    @BindingAdapter(value = {"thumbnail", "cardViewBackground", "mainTitleColor"}, requireAll = false)
    public static void bindThumbnail(ImageView imageView, String thumbnail,
                                     MaterialCardView cardView, TextView mainTitle) {

        Glide.with(imageView.getContext())
                .asBitmap()
                .load(thumbnail)
                .into(new TargetImageView(imageView, cardView, mainTitle));
    }

    private static class TargetImageView extends BitmapImageViewTarget  {

        private ImageView mImageView;
        private MaterialCardView mCardView;
        private TextView mTitleTextView;

        TargetImageView(ImageView imageView, MaterialCardView cardView, TextView title) {
            super(imageView);
            this.mImageView = imageView;
            this.mCardView = cardView;
            this.mTitleTextView = title;
        }

        @Override
        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            super.onResourceReady(resource, transition);
            mImageView.setImageBitmap(resource);
            // Request to generate palette as async
            Palette.from(resource).generate(palette -> {
                // Only apply if palette has resources generated successfully.
                if (palette != null) {
                    // Set color from palette to background of main list we show by calling holder.

//                    Palette.Swatch mutedSwatch = palette.getMutedSwatch();
//                    if (mutedSwatch != null)
//                        mCardView.setCardBackgroundColor(mutedSwatch.getRgb());
//
//                    Palette.Swatch darkMutedSwatch = palette.getDarkVibrantSwatch();
//                    if (darkMutedSwatch != null)
//                        mTitleTextView.setTextColor(darkMutedSwatch.getRgb());
                }
            });
        }
    }
}
