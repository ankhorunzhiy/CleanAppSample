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
import com.cleanappsample.UIThread;
import com.cleanappsample.cache.UserCacheImpl;
import com.cleanappsample.di.UserModule;
import com.cleanappsample.domain.ActionWrapper;
import com.cleanappsample.domain.executor.PostExecutionThread;
import com.cleanappsample.domain.executor.ThreadExecutor;
import com.cleanappsample.domain.interactor.GetUserList;
import com.cleanappsample.domain.interactor.UseCase;
import com.cleanappsample.domain.repository.UserRepository;
import com.cleanappsample.entity.UserEntity;
import com.cleanappsample.executor.JobExecutor;
import com.cleanappsample.mapper.UserDataMapper;
import com.cleanappsample.model.UserModel;
import com.cleanappsample.repository.UserDataRepository;
import com.cleanappsample.view.FriendListView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import flow.Flow;
import flow.path.Path;
import io.techery.presenta.addition.flow.path.Layout;
import io.techery.presenta.di.ScreenScope;
import io.techery.presenta.mortarscreen.component.WithComponent;
import mortar.ViewPresenter;
import rx.Subscriber;

@Layout(R.layout.friend_list_view) @WithComponent(FriendListScreen.Component.class)
public class FriendListScreen extends Path {

    @ScreenScope(FriendListScreen.class)
    public static class Presenter extends ViewPresenter<FriendListView> {
        UseCase getUserListUserCase;
        List<UserModel> friends;
        @Inject
        UserDataMapper userMapper;
        @Inject
        UserCacheImpl userCache;
        @Inject
        PostExecutionThread postExecutionThread;
        @Inject
        ThreadExecutor threadExecutor;
        @Inject
        UserDataRepository userDataRepository;

        @Inject
        public Presenter() {
            this.getUserListUserCase = new GetUserList(userDataRepository, threadExecutor, postExecutionThread);
        }

        @Override
        public void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            getUserListUserCase.execute(new Subscriber<ActionWrapper<List<UserEntity>>>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(ActionWrapper<List<UserEntity>> o) {
                    processUsers(o.data);
                }
            });
        }

        private void processUsers(List<UserEntity> users) {
            if (!hasView()) return;
            userCache.put(users);
//            userCache.get(1).subscribe(userEntity -> {
//                Toast.makeText(getView().getContext(), "Followers = " + userEntity.followers(), Toast.LENGTH_SHORT).show();
//            });
            friends = userMapper.convert(users);
            getView().showFriends(friends);
        }

        public void onFriendSelected(int position) {
            if(friends.isEmpty()) return;
            Flow.get(getView()).set(new FriendScreen(friends.get(position)));
        }
    }

    @ScreenScope(FriendListScreen.class)
    @dagger.Component(dependencies = MainActivity.Component.class, modules = UserModule.class)
    public interface Component {
        void inject(FriendListView friendListView);
    }

}
