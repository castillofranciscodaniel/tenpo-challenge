package com.org.tenpo.challenge.core.exeption;

public class CoreException extends RuntimeException {

    private Integer code;

    private String message;

    public CoreException() {
        super();
    }

    public CoreException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CoreException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
