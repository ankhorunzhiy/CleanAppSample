package com.cleanappsample.di.modules;

import com.cleanappsample.di.PerActivity;
import com.cleanappsample.domain.interactor.GetUserList;
import com.cleanappsample.domain.interactor.UseCase;

import javax.inject.Named;

import dagger.Provides;

/**
 * Created by Lubenets Vladyslav on 12/7/16.
 */

public class UserModule {

    @Provides @PerActivity @Named("userList")
    UseCase provideGetUserListUseCase(GetUserList getUserList) {
        return getUserList;
    }

}
