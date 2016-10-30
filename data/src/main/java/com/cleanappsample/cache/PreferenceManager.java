package com.cleanappsample.cache;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferenceManager extends PreferenceHelper {

    private static final String TAG = "clean_app_sample";

    @Inject
    public PreferenceManager(Context context) {
        super(context, TAG);
    }
}