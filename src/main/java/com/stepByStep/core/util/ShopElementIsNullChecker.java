package com.stepByStep.core.util;

import lombok.extern.log4j.Log4j2;

@Log4j2
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
