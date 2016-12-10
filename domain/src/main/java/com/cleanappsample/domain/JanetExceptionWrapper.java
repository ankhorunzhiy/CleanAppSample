package com.cleanappsample.domain;

/**
 * Created by Lubenets Vladyslav on 12/8/16.
 */

public class JanetExceptionWrapper extends Throwable {

    public JanetExceptionWrapper(String message, Throwable cause) {
        super(message, cause);
    }

    public JanetExceptionWrapper() {

    }
}
