package com.org.tenpo.challenge.core.exeption;

public class RequestLogSaveException extends CoreException {

    public RequestLogSaveException() {
    }

    public RequestLogSaveException(String message, Integer code) {
        super(code, message);
    }
}
