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

@Path("/task")
public class TodoappEndpoint {

    TasksManager manager = TasksManager.getDefault();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTasks() {
        return Response.ok(manager.getAll())
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask(Task task) {
        Task result = manager.add(task);
        return Response.ok(result)
                .build();
    }

    @PUT
    @Path("/{taskId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTask(@PathParam("taskId") String taskId, Task task) {
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
    public Response readTask(@PathParam("taskId") String taskId) {
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
    public Response deleteTask(@PathParam("taskId") String taskId) {
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
