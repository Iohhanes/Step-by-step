package com.stepByStep.core.util.constants;

public enum TestEnum {
    CART_ITEM_IS_NULL_EXCEPTION("Input cart item is null"),
    CART_ITEM_NOT_FOUND_EXCEPTION("This cart item not found"),
    ORDER_BOARD_GAME_IS_NULL_EXCEPTION("Input order board game is null"),
    BOARD_GAME_NOT_FOUND_EXCEPTION("This board game not found"),
    INVALID_ITEM_QUANTITY_EXCEPTION("Invalid item quantity: ");

    private String value;

    TestEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
