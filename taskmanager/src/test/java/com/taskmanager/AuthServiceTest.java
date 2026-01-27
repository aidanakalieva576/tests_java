package com.taskmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taskmanager.model.User;
import com.taskmanager.service.AuthService;

public class AuthServiceTest {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceTest.class);

    @Test
    void register_shouldCreateUser() {
        log.info("Тест register_shouldCreateUser — старт");

        AuthService auth = new AuthService();
        User user = auth.register("alice", "12345");

        log.debug("Проверяем имя пользователя");
        assertEquals("alice", user.getUsername(), "Имя пользователя должно быть 'alice'");

        log.info("Тест register_shouldCreateUser — успешно завершён");
    }

    @Test
    void register_emptyUsername_shouldThrow() {
        log.info("Тест register_emptyUsername_shouldThrow — старт");

        AuthService auth = new AuthService();

        log.debug("Ожидаем IllegalArgumentException при пустом имени пользователя");
        assertThrows(IllegalArgumentException.class,
                () -> auth.register("", "12345"),
                "Пустое имя пользователя должно выбрасывать исключение");

        log.info("Тест register_emptyUsername_shouldThrow — успешно завершён");
    }

    @Test
    void register_existingUser_shouldThrow() {
        log.info("Тест register_existingUser_shouldThrow — старт");

        AuthService auth = new AuthService();
        auth.register("bob", "abc");

        log.debug("Пытаемся зарегистрировать существующего пользователя");
        assertThrows(IllegalArgumentException.class,
                () -> auth.register("bob", "xyz"),
                "Регистрация существующего пользователя должна выбрасывать исключение");

        log.info("Тест register_existingUser_shouldThrow — успешно завершён");
    }

    @Test
    void login_correctCredentials_shouldReturnTrue() {
        log.info("Тест login_correctCredentials_shouldReturnTrue — старт");

        AuthService auth = new AuthService();
        auth.register("charlie", "pass");

        log.debug("Проверяем логин с корректными данными");
        assertTrue(auth.login("charlie", "pass"),
                "Логин с правильными данными должен вернуть true");

        log.info("Тест login_correctCredentials_shouldReturnTrue — успешно завершён");
    }

    @Test
    void login_wrongPassword_shouldReturnFalse() {
        log.info("Тест login_wrongPassword_shouldReturnFalse — старт");

        AuthService auth = new AuthService();
        auth.register("dave", "pass");

        log.debug("Проверяем логин с неправильным паролем");
        assertFalse(auth.login("dave", "wrong"),
                "Логин с неправильным паролем должен вернуть false");

        log.info("Тест login_wrongPassword_shouldReturnFalse — успешно завершён");
    }

    @Test
    void login_nonExistingUser_shouldReturnFalse() {
        log.info("Тест login_nonExistingUser_shouldReturnFalse — старт");

        AuthService auth = new AuthService();

        log.debug("Проверяем логин несуществующего пользователя");
        assertFalse(auth.login("nonexistent", "123"),
                "Логин несуществующего пользователя должен вернуть false");

        log.info("Тест login_nonExistingUser_shouldReturnFalse — успешно завершён");
    }
}
