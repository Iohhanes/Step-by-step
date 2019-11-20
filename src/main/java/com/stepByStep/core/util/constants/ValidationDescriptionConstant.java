package com.stepByStep.core.util.constants;

public interface ValidationDescriptionConstant {

    String EMPTY_FIELD_MESSAGE = "This field is required";
    String INVALID_USERNAME_MESSAGE = "Please use between 6 and 20, latin characters, numbers and underscores";
    String DUPLICATE_USERNAME_MESSAGE = "Someone already has that username";
    String INVALID_SIGN_MESSAGE = "Try one with at least 8 latin characters or(and) numbers";
    String INVALID_LOGIN_DATA_MESSAGE = "Invalid username or password";
    String INVALID_NAME_IN_ORDER_MESSAGE = "Please use between 3 and 20 characters";
    String INVALID_EMAIL_IN_ORDER_MESSAGE = "Incorrect email";
    String INVALID_PHONE_IN_ORDER_MESSAGE =
            "The phone number cannot be less than 7 characters and must contain only numbers";
}
