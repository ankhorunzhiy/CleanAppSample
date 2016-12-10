package com.cleanappsample.repository;

/**
 * Created by Lubenets Vladyslav on 12/7/16.
 */


import com.cleanappsample.di.UsersManager;
import com.cleanappsample.domain.ActionWrapper;
import com.cleanappsample.domain.User;
import com.cleanappsample.domain.repository.UserRepository;
import com.cleanappsample.entity.mapper.UserEntityMapper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * {@link UserRepository} for retrieving user data.
 */
public class UserDataRepository implements UserRepository {

    @Inject
    UsersManager usersManager;

    public UserDataRepository() {
        this.usersManager = new UsersManager();
    }

    @Override
    public Observable<ActionWrapper<List<User>>> users() {
        Observable<ActionWrapper<List<User>>> listObservable = usersManager.provideUsersPipe()
                .observe()
                .filter(usersAction -> usersAction != null && usersAction.action.getResponse() != null)
                .flatMap(usersActionActionState -> new UserEntityMapper().convert(usersActionActionState));
        usersManager.users();
        return listObservable;
    }

    @Override
    public Observable<User> user(int userId) {
        return null;
    }
}
