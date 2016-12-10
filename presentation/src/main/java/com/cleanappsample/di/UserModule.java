/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cleanappsample.di;

import com.cleanappsample.domain.interactor.GetUserList;
import com.cleanappsample.domain.interactor.UseCase;
import com.cleanappsample.screen.FriendListScreen;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.techery.presenta.di.ScreenScope;

/**
 * Dagger module that provides user related collaborators.
 */
@Module
public class UserModule {

  private int userId = -1;

  public UserModule() {}

  public UserModule(int userId) {
    this.userId = userId;
  }

  @Provides
  @ScreenScope(FriendListScreen.class)
  @Named("userList")
  UseCase provideGetUserListUseCase(
      GetUserList getUserList) {
    return getUserList;
  }

}