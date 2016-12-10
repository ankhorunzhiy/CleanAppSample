package com.cleanappsample.di.modules;

import android.content.Context;

import com.cleanappsample.CleanSampleApplication;
import com.cleanappsample.cache.PreferenceManager;
import com.cleanappsample.repository.UserDataRepository;

import dagger.Module;
import dagger.Provides;
import io.techery.presenta.di.ApplicationScope;

@Module
public class ApplicationModule {

    private final CleanSampleApplication application;

    public ApplicationModule(CleanSampleApplication application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    public Context provideAppContext(){
        return this.application;
    }

    @Provides
    @ApplicationScope
    PreferenceManager providePreferenceHelper(Context context){
        return new PreferenceManager(context);
    }

}
