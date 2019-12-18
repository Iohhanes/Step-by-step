package com.stepByStep.core.util.constants;

public interface URLValueConstant {

    String MAIN_PAGE_URL = "/";
    String LOGIN_URL = "/login";
    String LOGIN_PROCESSING_URL="/login-processing";
    String REGISTRATION_URL = "/registration";
    String POSTING_FORM_URL = "/posting-form";
    String EDITING_FORM_URL = "/editing-form";
    String POSTING_BOARD_GAME_URL = "/posting-board-game";
    String DELETING_BOARD_GAME_URL = "/deleting-board-game";
    String EDITING_BOARD_GAME_URL = "/editing-board-game";
    String ORDERS_MANAGEMENT_URL = "/orders-management";
    String CHANGING_ORDER_STATUS = "/changing-order-status";
    String STORE_URL = "/store";
    String SORTING_ALPHABETICALLY_BOARD_GAMES_URL = "/sorting-alphabetically-board_games";
    String SEARCH_BOARD_GAME_URL = "/search-board-game";
    String BOARD_GAME_DETAILS_URL = "/board-game-{boardGameId}";
    String CART_USER_URL = "/cart";
    String ADDING_BOARD_GAME_TO_CART_URL = "/adding-board-game-to-user-cart";
    String DELETING_CART_ITEM_FROM_CART_URL = "/deleting-cart-item";
    String CLEANING_UP_USER_CART_URL = "/cleaning-up-user-cart";
    String CART_ITEM_DETAILS_URL = "/cart-item-{cartItemId}";
    String PLACING_FORM_URL = "/placing-from";
    String PLACING_ORDER_URL = "/placing-order";
    String ORDERS_USER_HISTORY_URL = "/orders-history";
    String ORDER_DETAILS_URL = "/order-{orderId}";
    String STATIC_RESOURCES_URL="/static/**";
    String IMAGES_URL="/images/**";
    String BOARD_GAME_DETAILS_PATTERN_URL="/board-game-**";
    String REDIRECT_PART_URL = "redirect:";
}
