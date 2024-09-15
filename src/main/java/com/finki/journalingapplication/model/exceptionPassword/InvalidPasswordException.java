package com.finki.journalingapplication.model.exceptionPassword;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(int conditionViolated) {
        super(getErrorMessage(conditionViolated));
    }

    private static String getErrorMessage(int conditionViolated) {
        switch (conditionViolated) {
            case 1:
                return "Password length should contain at least 8 characters";
            case 2:
                return "Password should not contain any space";
            case 3:
                return "Password should contain at least one digit(0-9)";
            case 4:
                return "Password should contain at least one special character";
            case 5:
                return "Password should contain at least one uppercase letter(A-Z)";
            case 6:
                return "Password should contain at least one lowercase letter(a-z)";
            default:
                return "Unknown error";
        }
    }
}
