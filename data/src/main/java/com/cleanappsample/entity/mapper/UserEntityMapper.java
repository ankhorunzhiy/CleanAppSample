package com.cleanappsample.entity.mapper;


import com.cleanappsample.domain.User;
import com.cleanappsample.entity.UserEntity;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Anton Khorunzhyi on 10/25/2016.
 */

public class UserEntityMapper {

    @Inject
    public UserEntityMapper() {
    }

    public User convert(UserEntity userEntity){
        User user = User.builder()
                .userId(userEntity.id())
                .coverUrl(userEntity.coverUrl())
                .description(userEntity.description())
                .email(userEntity.email())
                .followers(userEntity.followers())
                .fullName(userEntity.fullName())
                .build();
        return user;
    }

    public Observable<List<User>> convert(List<UserEntity> userEntities){
        return Observable.from(userEntities)
                .map(this::convert)
                .filter(user -> user != null)
                .toList();
    }
}
