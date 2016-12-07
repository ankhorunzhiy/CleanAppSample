package com.cleanappsample.repository;

/**
 * Created by Lubenets Vladyslav on 12/7/16.
 */


import com.cleanappsample.di.UsersManager;
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
@Singleton
public class UserDataRepository implements UserRepository {

    @Inject
    UsersManager usersManager;

    @Override
    public Observable<List<User>> users() {
        Observable<List<User>> listObservable = usersManager.provideUsersPipe()
                .observeSuccess()
                .filter(usersAction -> usersAction != null && usersAction.getResponse() != null)
                .flatMap(usersAction -> new UserEntityMapper().convert(usersAction.getResponse()));
        usersManager.users();
        return listObservable;
    }

    @Override
    public Observable<User> user(int userId) {
        return null;
    }
}
