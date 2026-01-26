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

import com.taskmanager.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    private Map<String, User> users = new HashMap<>();

    // Регистрация нового пользователя с хэшированием пароля
    public User register(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Имя пользователя и пароль не могут быть пустыми");
        }
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("Пользователь уже существует");
        }
        // Создаём хэш пароля
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, hashedPassword);
        users.put(username, user);
        return user;
    }

    // Авторизация пользователя (с проверкой хэша)
    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user == null) return false;
        // Проверяем введённый пароль с сохранённым хэшем
        return BCrypt.checkpw(password, user.getPassword());
    }

    // Проверка, существует ли пользователь
    public boolean userExists(String username) {
        return users.containsKey(username);
    }
}
