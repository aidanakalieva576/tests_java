package com.taskmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.taskmanager.model.Task;
import com.taskmanager.service.TaskManager;

public class TaskManagerTest {

    @Test
    void addTask_shouldAddTask() {
        TaskManager manager = new TaskManager();
        Task task = manager.addTask("Test task");
        assertEquals("Test task", task.getTitle(), "Название задачи должно быть 'Test task'");
        System.out.println("Тест addTask_shouldAddTask прошёл успешно");
    }

    @Test
    void addTask_shouldIncreaseTaskCount() {
        TaskManager manager = new TaskManager();
        manager.addTask("Task 1");
        manager.addTask("Task 2");
        assertEquals(2, manager.getAllTasks().size(), "Количество задач должно быть 2");
        System.out.println("Тест addTask_shouldIncreaseTaskCount прошёл успешно");
    }

    @Test
    void addTask_emptyTitle_shouldThrowException() {
        TaskManager manager = new TaskManager();
        assertThrows(IllegalArgumentException.class, () -> manager.addTask(""), "Должно выбрасываться исключение для пустого названия");
        System.out.println("Тест addTask_emptyTitle_shouldThrowException прошёл успешно");
    }

    @Test
    void removeTask_existingTask_shouldReturnTrue() {
        TaskManager manager = new TaskManager();
        Task task = manager.addTask("Task");
        assertTrue(manager.removeTask(task.getId()), "Удаление существующей задачи должно вернуть true");
        System.out.println("Тест removeTask_existingTask_shouldReturnTrue прошёл успешно");
    }

    @Test
    void removeTask_nonExistingTask_shouldReturnFalse() {
        TaskManager manager = new TaskManager();
        assertFalse(manager.removeTask(999), "Удаление несуществующей задачи должно вернуть false");
        System.out.println("Тест removeTask_nonExistingTask_shouldReturnFalse прошёл успешно");
    }

    @Test
    void getTaskById_existingTask_shouldReturnTask() {
        TaskManager manager = new TaskManager();
        Task task = manager.addTask("Task");
        assertNotNull(manager.getTaskById(task.getId()), "Существующая задача должна возвращаться");
        System.out.println("Тест getTaskById_existingTask_shouldReturnTask прошёл успешно");
    }

    @Test
    void getTaskById_nonExistingTask_shouldReturnNull() {
        TaskManager manager = new TaskManager();
        assertNull(manager.getTaskById(1), "Несуществующая задача должна возвращать null");
        System.out.println("Тест getTaskById_nonExistingTask_shouldReturnNull прошёл успешно");
    }

    @Test
    void completeTask_shouldMarkTaskAsCompleted() {
        TaskManager manager = new TaskManager();
        Task task = manager.addTask("Task");
        manager.completeTask(task.getId());
        assertTrue(task.isCompleted(), "Задача должна быть отмечена как выполненная");
        System.out.println("Тест completeTask_shouldMarkTaskAsCompleted прошёл успешно");
    }

    @Test
    void completeTask_nonExistingTask_shouldReturnFalse() {
        TaskManager manager = new TaskManager();
        assertFalse(manager.completeTask(10), "Завершение несуществующей задачи должно вернуть false");
        System.out.println("Тест completeTask_nonExistingTask_shouldReturnFalse прошёл успешно");
    }

    @Test
    void newTask_shouldNotBeCompleted() {
        Task task = new Task(1, "Task");
        assertFalse(task.isCompleted(), "Новая задача не должна быть выполнена по умолчанию");
        System.out.println("Тест newTask_shouldNotBeCompleted прошёл успешно");
    }
}
