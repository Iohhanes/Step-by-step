package com.stepByStep.core.util;

import java.util.ResourceBundle;

public class ConfigurationPathManger {

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("path_config");

    private ConfigurationPathManger() {
    }

    public static String getPath(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
}
