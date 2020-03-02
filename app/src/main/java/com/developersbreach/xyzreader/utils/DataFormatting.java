package com.developersbreach.xyzreader.utils;

import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DataFormatting {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss", Locale.getDefault());
    private static SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private static GregorianCalendar START_OF_EPOCH = new GregorianCalendar(2, 1, 1);

    public static Spanned formatDate(String articlePublishedDate) {
        Spanned date;

        Date publishedDate = parsePublishedDate(articlePublishedDate, dateFormat);

        if (!publishedDate.before(START_OF_EPOCH.getTime())) {

            date = Html.fromHtml(
                    DateUtils.getRelativeTimeSpanString(
                            publishedDate.getTime(),
                            System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                            DateUtils.FORMAT_ABBREV_ALL).toString());

        } else {

            date = Html.fromHtml(
                    outputFormat.format(publishedDate));
        }
        return date;
    }

    private static Date parsePublishedDate(String articlePublishedDate, SimpleDateFormat dateFormat) {
        try {
            return dateFormat.parse(articlePublishedDate);
        } catch (ParseException ex) {
            Log.e("DataFormatting", "parsePublishedDate", ex);
            return new Date();
        }
    }
}
