/*
 * Copyright 2013 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cleanappsample.di;

import android.content.Context;

import com.cleanappsample.cache.PreferenceManager;
import com.cleanappsample.cache.UserCache;
import com.cleanappsample.cache.UserCacheImpl;
import com.cleanappsample.di.modules.ActionBarModule;
import com.cleanappsample.net.AutoValueAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import flow.StateParceler;
import io.techery.presenta.addition.flow.util.GsonParceler;

/**
 * Defines app-wide singletons.
 */
@Module(includes = ActionBarModule.class)
public class RootModule {

    @Provides
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(new AutoValueAdapterFactory());
        builder.serializeNulls();
        return builder.create();
    }

    @Provides
    StateParceler provideParcel(Gson gson) {
        return new GsonParceler(gson);
    }

    @Provides
    UserCache provideUserCache(PreferenceManager preferenceManager, Context context, Gson gson) {
        return new UserCacheImpl(preferenceManager, context, gson);
    }

}
