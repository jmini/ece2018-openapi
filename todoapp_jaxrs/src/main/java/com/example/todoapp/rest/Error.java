package com.example.todoapp.rest;

public class Error {

    public static final int CODE_TASK_NOT_FOUND = 1010;
    public static final int CODE_TASK_CAN_NOT_BE_UPDATED = 1020;
    public static final int CODE_TASK_CAN_NOT_BE_DELETED = 1030;

    private int code;

    private String message;

    public Error(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
