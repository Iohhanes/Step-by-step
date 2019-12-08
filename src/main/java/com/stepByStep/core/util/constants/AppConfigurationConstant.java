package com.stepByStep.core.util.constants;

import java.io.File;

public interface AppConfigurationConstant {

    String UPLOAD_TEMP_PATH = new File(System.getProperty("java.io.tmpdir")).getAbsolutePath();
    long MAX_UPLOAD_SIZE_IN_KB = 1048576L;
    long MAX_REQUEST_SIZE_IN_KB = 1048576L;
    int FILE_SIZE_THRESHOLD_IN_KB = 524288;
}
