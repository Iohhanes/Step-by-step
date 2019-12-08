package com.stepByStep.core.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShopElementIsNullChecker {

    private ShopElementIsNullChecker() {

    }

    public static <T, E extends Exception> void checkNull(T element, E exception) throws E {
        if (element == null) {
            log.warn(exception.getMessage());
            throw exception;
        }
    }
}
