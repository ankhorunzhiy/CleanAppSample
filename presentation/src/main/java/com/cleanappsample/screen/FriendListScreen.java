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
package com.cleanappsample.screen;

import android.os.Bundle;

import com.cleanappsample.MainActivity;
import com.cleanappsample.R;
import com.cleanappsample.actions.UsersAction;
import com.cleanappsample.di.UsersManager;
import com.cleanappsample.di.ScreenComponent;
import com.cleanappsample.di.components.ApplicationComponent;
import com.cleanappsample.net.NetworkModule;
import com.cleanappsample.entity.UserEntity;
import com.cleanappsample.model.User;
import com.cleanappsample.view.FriendListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import flow.Flow;
import flow.path.Path;
import io.techery.janet.helper.ActionStateSubscriber;
import io.techery.presenta.addition.flow.path.Layout;
import io.techery.presenta.di.ScreenScope;
import io.techery.presenta.mortarscreen.component.WithComponent;
import mortar.ViewPresenter;
import rx.android.schedulers.AndroidSchedulers;

@Layout(R.layout.friend_list_view) @WithComponent(FriendListScreen.Component.class)
public class FriendListScreen extends Path {

    @ScreenScope(FriendListScreen.class)
    public static class Presenter extends ViewPresenter<FriendListView> {
        List<User> friends;
        @Inject
        UsersManager usersManager;

        @Inject
        public Presenter() {
            friends = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                friends.add(new User(i, "User " + i));
            }
        }

        @Override
        public void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            usersManager.provideUsersPipe()
                    .observe()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ActionStateSubscriber<UsersAction>()
                    .onStart(action -> System.out.println("Request is being sent " + action))
                    .onProgress((action, progress) -> System.out.println("Request in progress: " + progress))
                    .onSuccess(action -> processUsers(action.getResponse()))
                    .onFail((action, throwable) -> System.err.println("Request failed " + throwable))
            );
            usersManager.users();
        }

        private void processUsers(List<UserEntity> friends) {
            if (!hasView()) return;
            getView().showFriends(this.friends);
        }

        public void onFriendSelected(int position) {
            Flow.get(getView()).set(new FriendScreen(friends.get(position)));
        }
    }

    @ScreenScope(FriendListScreen.class)
    @dagger.Component(dependencies = MainActivity.Component.class)
    public interface Component {
        void inject(FriendListView friendListView);
    }

}
