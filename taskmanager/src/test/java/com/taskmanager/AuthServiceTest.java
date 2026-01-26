package com.taskmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.taskmanager.model.User;
import com.taskmanager.service.AuthService;

public class AuthServiceTest {

    @Test
    void register_shouldCreateUser() {
        AuthService auth = new AuthService();
        User user = auth.register("alice", "12345");
        assertEquals("alice", user.getUsername(), "Имя пользователя должно быть 'alice'");
        System.out.println("Тест register_shouldCreateUser прошёл успешно");
    }

    @Test
    void register_emptyUsername_shouldThrow() {
        AuthService auth = new AuthService();
        assertThrows(IllegalArgumentException.class,
                () -> auth.register("", "12345"),
                "Пустое имя пользователя должно выбрасывать исключение");
        System.out.println("Тест register_emptyUsername_shouldThrow прошёл успешно");
    }

    @Test
    void register_existingUser_shouldThrow() {
        AuthService auth = new AuthService();
        auth.register("bob", "abc");
        assertThrows(IllegalArgumentException.class,
                () -> auth.register("bob", "xyz"),
                "Регистрация существующего пользователя должна выбрасывать исключение");
        System.out.println("Тест register_existingUser_shouldThrow прошёл успешно");
    }

    @Test        // Проверяем логин с правильным паролем
    void login_correctCredentials_shouldReturnTrue() {
        AuthService auth = new AuthService();
        auth.register("charlie", "pass");
        assertTrue(auth.login("charlie", "pass"), "Логин с правильными данными должен вернуть true");
        System.out.println("Тест login_correctCredentials_shouldReturnTrue прошёл успешно");
    }

    @Test         // Проверяем логин с неправильным паролем
    void login_wrongPassword_shouldReturnFalse() {
        AuthService auth = new AuthService();
        auth.register("dave", "pass");
        assertFalse(auth.login("dave", "wrong"), "Логин с неправильным паролем должен вернуть false");
        System.out.println("Тест login_wrongPassword_shouldReturnFalse прошёл успешно");
    }

    @Test        // Проверяем логин для пользователя которого нет
    void login_nonExistingUser_shouldReturnFalse() {
        AuthService auth = new AuthService();
        assertFalse(auth.login("nonexistent", "123"), "Логин несуществующего пользователя должен вернуть false");
        System.out.println("Тест login_nonExistingUser_shouldReturnFalse прошёл успешно");
    }
}
