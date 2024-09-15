package com.finki.journalingapplication.model.exceptionPassword.types;

public class UsernameInPasswordException extends RuntimeException{
    public UsernameInPasswordException(String msg) {
        super(msg);
    }
}
