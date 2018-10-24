package com.example.todoapp.rest;

import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Path("/task")
@Tags(
        value = @Tag(
                name = "task",
                description = "Task management"))
public class TodoappEndpoint {

    TasksManager manager = TasksManager.getDefault();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "tasksGetAll",
            summary = "Get the list of all tasks")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "List of all tasks",
                            content = @Content(
                                    schema = @Schema(
                                            type = SchemaType.ARRAY,
                                            implementation = Task.class))),
                    @APIResponse(
                            responseCode = "default",
                            description = "Generic error response",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Error.class))) })
    public Response getAllTasks() {
        return Response.ok(manager.getAll())
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "tasksCreate",
            summary = "Create a new task")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "The created task",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Task.class))),
                    @APIResponse(
                            responseCode = "default",
                            description = "Generic error response",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Error.class))) })
    public Response createTask(Task task) {
        Task result = manager.add(task);
        return Response.ok(result)
                .build();
    }

    @PUT
    @Path("/{taskId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "tasksUpdate",
            summary = "Update an existing task")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "The updated task",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Task.class))),
                    @APIResponse(
                            responseCode = "default",
                            description = "Generic error response",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Error.class))) })
    public Response updateTask(@Parameter(
            description = "The id of the task",
            name = "taskId",
            example = "e1cb23d0-6cbe-4a29-b586-bfa424bc93fd",
            required = true,
            schema = @Schema(
                    type = SchemaType.STRING)) @PathParam("taskId") String taskId, Task task) {
        if (taskId != null && (task.getId() == null || taskId.equals(task.getId()))) {
            task.setId(taskId);
            Optional<Task> result = manager.update(task);
            if (result.isPresent()) {
                return Response.ok(result.get())
                        .build();
            } else {
                return Response.status(Status.BAD_REQUEST)
                        .entity(new Error(Error.CODE_TASK_CAN_NOT_BE_UPDATED, "Could not update task"))
                        .build();
            }
        } else {
            return Response.status(Status.BAD_REQUEST)
                    .entity(new Error(Error.CODE_TASK_ID_MISSMATCH_CAN_NOT_BE_UPDATED, "There is a missmatch between the taskId provided as path parameter and in the request body"))
                    .build();
        }
    }

    @GET
    @Path("/{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "tasksRead",
            summary = "Get a single task based on its id")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Task.class))),
                    @APIResponse(
                            responseCode = "default",
                            description = "Generic error response",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Error.class))) })
    public Response readTask(
            @Parameter(
                    description = "The id of the task",
                    name = "taskId",
                    example = "e1cb23d0-6cbe-4a29-b586-bfa424bc93fd",
                    required = true,
                    schema = @Schema(
                            type = SchemaType.STRING)) @PathParam("taskId") String taskId) {
        Optional<Task> findById = manager.findById(taskId);
        if (findById.isPresent()) {
            return Response.ok(findById.get())
                    .build();
        } else {
            return Response.status(Status.NOT_FOUND)
                    .entity(new Error(Error.CODE_TASK_NOT_FOUND, "No task found with id '" + taskId + "'"))
                    .build();
        }
    }

    @DELETE
    @Path("/{taskId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "tasksDelete",
            summary = "Delete an existing task")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "The updated task",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Task.class))),
                    @APIResponse(
                            responseCode = "default",
                            description = "Generic error response",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Error.class))) })
    public Response deleteTask(@Parameter(
            description = "The id of the task",
            name = "taskId",
            example = "e1cb23d0-6cbe-4a29-b586-bfa424bc93fd",
            required = true,
            schema = @Schema(
                    type = SchemaType.STRING)) @PathParam("taskId") String taskId) {
        boolean result = manager.delete(taskId);
        if (result) {
            return Response.noContent()
                    .build();
        } else {
            return Response.status(Status.BAD_REQUEST)
                    .entity(new Error(Error.CODE_TASK_CAN_NOT_BE_DELETED, "Could not delete task"))
                    .build();
        }
    }
}
