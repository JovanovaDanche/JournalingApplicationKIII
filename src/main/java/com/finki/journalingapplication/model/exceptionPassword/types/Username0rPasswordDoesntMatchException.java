package com.finki.journalingapplication.model.exceptionPassword.types;

public class Username0rPasswordDoesntMatchException extends RuntimeException{
    public Username0rPasswordDoesntMatchException(String message) {
        super(message);
    }
}
