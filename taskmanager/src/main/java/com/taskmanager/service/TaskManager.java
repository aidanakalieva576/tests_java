package com.taskmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.taskmanager.model.Task;

public class TaskManager {

    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    public Task addTask(String title) {
        Task task = new Task(nextId++, title);
        tasks.add(task);
        return task;
    }

    public boolean removeTask(int id) {
        return tasks.removeIf(task -> task.getId() == id);
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task getTaskById(int id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean completeTask(int id) {
        Task task = getTaskById(id);
        if (task == null) return false;
        task.complete();
        return true;
    }
}
