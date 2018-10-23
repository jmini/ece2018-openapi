package com.example.todoapp.rest;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
        name = "Error",
        type = SchemaType.OBJECT,
        description = "Object representing an error")
public class Error {

    public static final int CODE_TASK_NOT_FOUND = 1010;
    public static final int CODE_TASK_CAN_NOT_BE_UPDATED = 1020;
    public static final int CODE_TASK_CAN_NOT_BE_DELETED = 1030;

    @Schema(
            name = "code",
            description = "Error code that identify of the error",
            example = "1000")
    private int code;

    @Schema(
            name = "message",
            description = "Short description of the error",
            example = "Could not perform the task")
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
