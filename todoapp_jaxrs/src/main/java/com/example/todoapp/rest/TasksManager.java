/**
 *
 */
package com.example.todoapp.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author jbr
 *
 */
public class TasksManager {

    private static final TasksManager INSTANCE = createTaskManager();

    private static TasksManager createTaskManager() {
        TasksManager manager = new TasksManager();
        Task task1 = new Task();
        task1.setDescription("Prepare the talk");
        task1.setCompleted(true);
        manager.add(task1);

        Task task2 = new Task();
        task2.setDescription("Present the talk");
        task2.setCompleted(false);
        manager.add(task2);
        return manager;
    }

    private List<Task> tasks = new ArrayList<>();

    public static TasksManager getDefault() {
        return INSTANCE;
    }

    public List<Task> getAll() {
        return tasks;
    }

    public Optional<Task> findById(String taskId) {
        return tasks.stream()
                .filter(t -> t.getId()
                        .equals(taskId))
                .findAny();
    }

    public Task add(Task task) {
        task.setId(UUID.randomUUID()
                .toString());
        tasks.add(task);
        return task;
    }

    public Optional<Task> update(Task task) {
        Optional<Task> findTask = findById(task.getId());
        if (findTask.isPresent()) {
            Task taskToUpdate = findTask.get();
            taskToUpdate.setDescription(task.getDescription());
            taskToUpdate.setCompleted(task.isCompleted());
            return Optional.of(taskToUpdate);
        }
        return findTask;
    }

    public boolean delete(String taskId) {
        Optional<Task> findTask = findById(taskId);
        if (findTask.isPresent()) {
            return tasks.remove(findTask.get());
        }
        return false;
    }
}
