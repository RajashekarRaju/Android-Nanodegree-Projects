package com.developersbreach.xyzreader.utils;

import android.app.Activity;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarBuilder {

    public static void showSnackBar(String message, Activity activity) {
        final View decorView = activity.getWindow().getDecorView();
        View view = decorView.findViewById(android.R.id.content);
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
}
