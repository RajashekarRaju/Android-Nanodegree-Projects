package com.developersbreach.xyzreader.utils;

import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.TextView;

import com.developersbreach.xyzreader.bindingAdapter.ArticleListBindingAdapter;
import com.developersbreach.xyzreader.bindingAdapter.FavoriteListBindingAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * Class which has methods to format date from json into readable form.
 */
public class DataFormatting {

    // Object which receives date in format we want to create.
    private static final SimpleDateFormat sDateFormat;
    // Object for date after formatted.
    private static final SimpleDateFormat sOutputFormat;
    // A calender system which provides the standard calendar system used by most of the world.
    private static final GregorianCalendar START_OF_EPOCH;

    static {
        sDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss", Locale.getDefault());
        sOutputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        START_OF_EPOCH = new GregorianCalendar(2, 1, 1);
    }

    /**
     * @param articlePublishedDate gets the date which needs to be formatted.
     * @return returns the formatted date.
     * @see ArticleListBindingAdapter#bindArticleItemPublishedDate(TextView, String)
     * @see FavoriteListBindingAdapter#bindFavoriteArticleItemPublishedDate(TextView, String)
     * for implementations.
     */
    public static Spanned formatDate(String articlePublishedDate) {
        Spanned date;
        Date publishedDate = parsePublishedDate(articlePublishedDate);
        if (!publishedDate.before(START_OF_EPOCH.getTime())) {
            date = Html.fromHtml(
                    DateUtils.getRelativeTimeSpanString(
                            publishedDate.getTime(),
                            System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                            DateUtils.FORMAT_ABBREV_ALL).toString());
        } else {
            date = Html.fromHtml(
                    sOutputFormat.format(publishedDate));
        }
        return date;
    }

    /**
     * @param articlePublishedDate date which needs formatting.
     * @return returns a new date after formatting, else catch the exception.
     */
    private static Date parsePublishedDate(String articlePublishedDate) {
        try {
            return DataFormatting.sDateFormat.parse(articlePublishedDate);
        } catch (ParseException ex) {
            Log.e("DataFormatting", "parsePublishedDate", ex);
            return new Date();
        }
    }
}
