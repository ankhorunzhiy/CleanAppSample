package com.cleanappsample.entity.mapper;


import com.cleanappsample.actions.UsersAction;
import com.cleanappsample.domain.ActionWrapper;
import com.cleanappsample.domain.JanetExceptionWrapper;
import com.cleanappsample.domain.User;
import com.cleanappsample.entity.UserEntity;

import java.util.List;

import javax.inject.Inject;

import io.techery.janet.ActionState;
import rx.Observable;

/**
 * Created by Anton Khorunzhyi on 10/25/2016.
 */

public class UserEntityMapper {

    @Inject
    public UserEntityMapper() {
    }

    public User convert(UserEntity userEntity) {
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

    public Observable<ActionWrapper<List<User>>> convert(ActionState<UsersAction> userEntities) {
        return Observable.from(userEntities.action.getResponse())
                .map(this::convert)
                .filter(user -> user != null)
                .toList()
                .map(users -> convertToDomainModel(userEntities, users));
    }

    private ActionWrapper<List<User>> convertToDomainModel(ActionState<UsersAction> userEntities, List<User> users) {
        ActionState.Status status = userEntities.status;
        ActionWrapper.Status domainStatus = null;
        switch (status) {
            case FAIL:
                domainStatus = ActionWrapper.Status.FAIL;
                break;
            case PROGRESS:
                domainStatus = ActionWrapper.Status.PROGRESS;
                break;
            case START:
                domainStatus = ActionWrapper.Status.START;
                break;
            case SUCCESS:
                domainStatus = ActionWrapper.Status.SUCCESS;
                break;
        }
        JanetExceptionWrapper janetExceptionWrapper = new JanetExceptionWrapper(userEntities.exception.getMessage(), userEntities.exception.getCause());
        return new ActionWrapper<>(domainStatus, janetExceptionWrapper, userEntities.progress, users);
    }
}
