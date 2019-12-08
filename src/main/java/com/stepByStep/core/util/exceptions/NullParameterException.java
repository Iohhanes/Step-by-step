package com.stepByStep.core.util.exceptions;

public class NullParameterException extends Exception {

    public NullParameterException() {
    }

    public NullParameterException(String message) {
        super(message);
    }

    public NullParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullParameterException(Throwable cause) {
        super(cause);
    }
}
