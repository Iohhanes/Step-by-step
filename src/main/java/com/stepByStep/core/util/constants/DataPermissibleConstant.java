package com.stepByStep.core.util.constants;

public interface DataPermissibleConstant {

    int MIN_PERMISSIBLE_QUANTITY_ITEM = 0;
    int MAX_PERMISSIBLE_QUANTITY_ITEM = Integer.MAX_VALUE - 1;
    int MIN_PERMISSIBLE_LENGTH_BOARD_GAME_TITLE = 3;
    int MAX_PERMISSIBLE_LENGTH_BOARD_GAME_TITLE = 50;
    int MAX_PERMISSIBLE_LENGTH_BOARD_GAME_DESCRIPTION = 255;
    double MIN_PERMISSIBLE_BOARD_GAME_PRICE = 0.01;
    double MAX_PERMISSIBLE_BOARD_GAME_PRICE = 1500;
    int MIN_PERMISSIBLE_BOARD_GAME_AGE = 0;
    int MAX_PERMISSIBLE_BOARD_GAME_AGE = 18;
    int MIN_PERMISSIBLE_BOARD_GAME_COUNT_PLAYERS = 2;
    int MAX_PERMISSIBLE_BOARD_GAME_COUNT_PLAYERS = 8;
    int MAX_PERMISSIBLE_LENGTH_ORDER_CUSTOMER_EMAIL=255;
    int MIN_PERMISSIBLE_LENGTH_ORDER_CUSTOMER_EMAIL=5;
}
