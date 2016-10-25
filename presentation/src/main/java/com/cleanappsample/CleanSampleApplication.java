package com.cleanappsample;

import android.app.Application;

import io.techery.presenta.mortar.DaggerService;
import mortar.MortarScope;
import com.cleanappsample.network.components.ApplicationComponent;
import com.cleanappsample.network.components.DaggerApplicationComponent;
import com.cleanappsample.network.modules.ApplicationModule;
import com.cleanappsample.network.NetworkModule;

public class CleanSampleApplication extends Application {

    private ApplicationComponent component;
    private MortarScope rootScope;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerService.createComponent(ApplicationComponent.class);
        component.inject(this);
    }


    @Override public Object getSystemService(String name) {
        if (rootScope == null) {
            rootScope = MortarScope.buildRootScope()
                    .withService(DaggerService.SERVICE_NAME, component)
                    .build("root");
        }
        if (rootScope.hasService(name)) return rootScope.getService(name);

        return super.getSystemService(name);
    }

    public ApplicationComponent getApplicationComponent() {
        return component;
    }
}
