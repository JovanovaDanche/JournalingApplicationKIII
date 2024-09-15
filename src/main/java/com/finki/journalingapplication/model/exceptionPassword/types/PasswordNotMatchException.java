package com.finki.journalingapplication.model.exceptionPassword.types;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException(String msg) {
        super(msg);
    }
}
