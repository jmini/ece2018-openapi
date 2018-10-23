package com.example.todoapp.rest;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
        name = "Task",
        type = SchemaType.OBJECT,
        description = "Object representing a Task")
public class Task {

    @Schema(
            name = "id",
            description = "id of the taks",
            readOnly = true,
            example = "e1cb23d0-6cbe-4a29-b586-bfa424bc93fd")
    private String id;

    @Schema(
            name = "description",
            description = "description of the task",
            required = true,
            example = "My important task")
    private String description;

    @Schema(
            name = "indicates",
            description = "indicates if a taks is completed or not")
    private boolean completed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
