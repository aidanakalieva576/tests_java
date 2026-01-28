// package com.taskmanager.service;

// import java.util.HashMap;
// import java.util.Map;

// import com.taskmanager.model.User;

// public class AuthService {

//     private Map<String, User> users = new HashMap<>();

//     // Регистрация нового пользователя
//     public User register(String username, String password) {
//         if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
//             throw new IllegalArgumentException("Имя пользователя и пароль не могут быть пустыми");
//         }
//         if (users.containsKey(username)) {
//             throw new IllegalArgumentException("Пользователь уже существует");
//         }
//         User user = new User(username, password);
//         users.put(username, user);
//         return user;
//     }

//     // Авторизация пользователя
//     public boolean login(String username, String password) {
//         User user = users.get(username);
//         if (user == null) return false;
//         return user.getPassword().equals(password);
//     }

//     // Проверка, существует ли пользователь
//     public boolean userExists(String username) {
//         return users.containsKey(username);
//     }
// }


package com.taskmanager.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.taskmanager.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    private Map<String, User> users = new HashMap<>();

    // 1. Регистрация нового пользователя с хэшированием пароля
    public User register(String username, String password) {
        validateCredentials(username, password);

        if (users.containsKey(username)) {
            throw new IllegalArgumentException("Пользователь уже существует");
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, hashedPassword);
        users.put(username, user);
        return user;
    }

    // 2. Авторизация пользователя
    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user == null) return false;
        return BCrypt.checkpw(password, user.getPassword());
    }

    // 3. Проверка, существует ли пользователь
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    // ---------- НОВЫЕ ФУНКЦИИ ----------

    // 4. Удаление пользователя
    public boolean deleteUser(String username) {
        return users.remove(username) != null;
    }

    // 5. Смена пароля
    public void changePassword(String username, String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("Пароль не может быть пустым");
        }

        User user = users.get(username);
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден");
        }

        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        user.setPassword(hashedPassword);
    }

    // 6. Проверка пароля пользователя (без логина)
    public boolean checkPassword(String username, String password) {
        User user = users.get(username);
        if (user == null) return false;
        return BCrypt.checkpw(password, user.getPassword());
    }

    // 7. Получить количество зарегистрированных пользователей
    public int getUserCount() {
        return users.size();
    }

    // 8. Получить пользователя по имени
    public User getUser(String username) {
        return users.get(username);
    }

    // 9. Очистить всех пользователей (например, для тестов)
    public void clearUsers() {
        users.clear();
    }

    // 10. Получить список всех имён пользователей
    public Set<String> getAllUsernames() {
        return users.keySet();
    }

    // 11. Валидация логина и пароля
    private void validateCredentials(String username, String password) {
        if (username == null || username.isEmpty()
                || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Имя пользователя и пароль не могут быть пустыми");
        }
    }

    // 12. Проверка, пуст ли сервис
    public boolean isEmpty() {
        return users.isEmpty();
    }
}
