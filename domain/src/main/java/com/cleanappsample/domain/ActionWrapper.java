package com.cleanappsample.domain;

/**
 * Created by Lubenets Vladyslav on 12/8/16.
 */

public class ActionWrapper<D> {

    /** Action status **/
    public enum Status {
        START,
        PROGRESS,
        SUCCESS,
        FAIL
    }


    public final Status status;
    public final JanetExceptionWrapper exception;
    public final int progress;
    public final D data;

    public ActionWrapper(Status status, JanetExceptionWrapper exception, int progress, D data) {
        this.status = status;
        this.exception = exception;
        this.progress = progress;
        this.data = data;
    }
}
