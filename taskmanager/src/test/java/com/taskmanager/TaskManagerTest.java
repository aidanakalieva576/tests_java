package com.taskmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taskmanager.model.Task;
import com.taskmanager.service.TaskManager;

public class TaskManagerTest {

    private static final Logger log = LoggerFactory.getLogger(TaskManagerTest.class);

    @Test
    void addTask_shouldAddTask() {
        log.info("Тест addTask_shouldAddTask — старт");

        TaskManager manager = new TaskManager();
        Task task = manager.addTask("Test task");

        log.debug("Проверяем название задачи");
        assertEquals("Test task", task.getTitle(), "Название задачи должно быть 'Test task'");

        log.info("Тест addTask_shouldAddTask — успешно завершён");
    }

    @Test
    void addTask_shouldIncreaseTaskCount() {
        log.info("Тест addTask_shouldIncreaseTaskCount — старт");

        TaskManager manager = new TaskManager();
        manager.addTask("Task 1");
        manager.addTask("Task 2");

        log.debug("Проверяем количество задач");
        assertEquals(2, manager.getAllTasks().size(), "Количество задач должно быть 2");

        log.info("Тест addTask_shouldIncreaseTaskCount — успешно завершён");
    }

    @Test
    void addTask_emptyTitle_shouldThrowException() {
        log.info("Тест addTask_emptyTitle_shouldThrowException — старт");

        TaskManager manager = new TaskManager();
        log.debug("Ожидаем исключение для пустого названия задачи");
        assertThrows(IllegalArgumentException.class,
                () -> manager.addTask(""),
                "Должно выбрасываться исключение для пустого названия");

        log.info("Тест addTask_emptyTitle_shouldThrowException — успешно завершён");
    }

    @Test
    void removeTask_existingTask_shouldReturnTrue() {
        log.info("Тест removeTask_existingTask_shouldReturnTrue — старт");

        TaskManager manager = new TaskManager();
        Task task = manager.addTask("Task");

        log.debug("Проверяем удаление существующей задачи");
        assertTrue(manager.removeTask(task.getId()), "Удаление существующей задачи должно вернуть true");

        log.info("Тест removeTask_existingTask_shouldReturnTrue — успешно завершён");
    }

    @Test
    void removeTask_nonExistingTask_shouldReturnFalse() {
        log.info("Тест removeTask_nonExistingTask_shouldReturnFalse — старт");

        TaskManager manager = new TaskManager();
        log.debug("Проверяем удаление несуществующей задачи");
        assertFalse(manager.removeTask(999), "Удаление несуществующей задачи должно вернуть false");

        log.info("Тест removeTask_nonExistingTask_shouldReturnFalse — успешно завершён");
    }

    @Test
    void getTaskById_existingTask_shouldReturnTask() {
        log.info("Тест getTaskById_existingTask_shouldReturnTask — старт");

        TaskManager manager = new TaskManager();
        Task task = manager.addTask("Task");

        log.debug("Проверяем получение существующей задачи по ID");
        assertNotNull(manager.getTaskById(task.getId()), "Существующая задача должна возвращаться");

        log.info("Тест getTaskById_existingTask_shouldReturnTask — успешно завершён");
    }

    @Test
    void getTaskById_nonExistingTask_shouldReturnNull() {
        log.info("Тест getTaskById_nonExistingTask_shouldReturnNull — старт");

        TaskManager manager = new TaskManager();
        log.debug("Проверяем получение несуществующей задачи по ID");
        assertNull(manager.getTaskById(1), "Несуществующая задача должна возвращать null");

        log.info("Тест getTaskById_nonExistingTask_shouldReturnNull — успешно завершён");
    }

    @Test
    void completeTask_shouldMarkTaskAsCompleted() {
        log.info("Тест completeTask_shouldMarkTaskAsCompleted — старт");

        TaskManager manager = new TaskManager();
        Task task = manager.addTask("Task");

        log.debug("Отмечаем задачу как выполненную");
        manager.completeTask(task.getId());

        assertTrue(task.isCompleted(), "Задача должна быть отмечена как выполненная");

        log.info("Тест completeTask_shouldMarkTaskAsCompleted — успешно завершён");
    }

    @Test
    void completeTask_nonExistingTask_shouldReturnFalse() {
        log.info("Тест completeTask_nonExistingTask_shouldReturnFalse — старт");

        TaskManager manager = new TaskManager();
        log.debug("Пытаемся завершить несуществующую задачу");
        assertFalse(manager.completeTask(10), "Завершение несуществующей задачи должно вернуть false");

        log.info("Тест completeTask_nonExistingTask_shouldReturnFalse — успешно завершён");
    }

    @Test
    void newTask_shouldNotBeCompleted() {
        log.info("Тест newTask_shouldNotBeCompleted — старт");

        Task task = new Task(1, "Task");
        log.debug("Проверяем, что новая задача не выполнена по умолчанию");
        assertFalse(task.isCompleted(), "Новая задача не должна быть выполнена по умолчанию");

        log.info("Тест newTask_shouldNotBeCompleted — успешно завершён");
    }
}
