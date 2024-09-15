package com.finki.journalingapplication.model.exceptionPassword.types;

public class UsernameExistsException extends RuntimeException {

    public UsernameExistsException(String msg) {
        super(msg);
    }
}
