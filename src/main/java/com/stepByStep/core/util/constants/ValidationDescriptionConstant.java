package com.stepByStep.core.util.constants;

public interface ValidationDescriptionConstant {

    String EMPTY_FIELD_MESSAGE = "This field is required";
    String INVALID_USERNAME_MESSAGE = "Please use between 6 and 20, latin characters, numbers and underscores";
    String DUPLICATE_USERNAME_MESSAGE = "Someone already has that username";
    String INVALID_SIGN_MESSAGE = "Try one with at least 8 latin characters or(and) numbers";
    String INVALID_LOGIN_DATA_MESSAGE = "Invalid username or password";
    String INVALID_CUSTOMER_NAME_IN_ORDER_MESSAGE = "Please use between 3 and 20 characters";
    String INVALID_CUSTOMER_EMAIL_IN_ORDER_MESSAGE = "Incorrect email";
    String INVALID_CUSTOMER_PHONE_IN_ORDER_MESSAGE =
            "The phone number cannot be less than 7 characters and must contain only numbers";
    String INVALID_TITLE_MESSAGE = "Please use between 3 and 50 characters";
    String INVALID_PRICE_MESSAGE = "The price cannot be less than 0,01$ and more than 1500$";
    String INVALID_DESCRIPTION_MESSAGE = "The description cannot be more than 255 characters";
    String INVALID_AGE_MESSAGE = "The age cannot be less than 0 and more than 18 years";
    String INVALID_COUNT_PLAYERS_MESSAGE = "The count of players cannot be less than 0 and more than 8";
    String INVALID_QUANTITY_ITEM_IN_STORE = "The quantity cannot be less than 0 and more than 2147483646";
    String INVALID_QUANTITY_ITEM_IN_CART = "Tne quantity cannot be less than 0 and more than set";
    String INVALID_QUANTITY_BOARD_GAME_IN_ORDER = "Tne quantity cannot be less than 0 and more than set in cart item";
}
