package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger("UserController");
    private final Map<Integer, User> users = new HashMap<>();


    @GetMapping
    public Collection<User> allUsers() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Starting to create new user");
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new ValidationException("Некорректный email");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        user.setId(getId());
        users.put(user.getId(), user);
        log.info("Created new user");
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (user.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }
        log.info("Updating user");

        if (users.containsKey(user.getId())) {
            User oldUser = users.get(user.getId());
            if (user.getEmail() != null) {
                oldUser.setEmail(user.getEmail());
            }
            if (user.getName() != null) {
                oldUser.setName(user.getName());
            }
            if (user.getLogin() != null) {
                oldUser.setLogin(user.getLogin());
            }
            if (user.getBirthday() != null) {
                oldUser.setBirthday(user.getBirthday());
            }
            log.info("Updated old user");
            return oldUser;

        }
        log.error("User not found");
        throw new NotFoundException("Юзер с id = " + user.getId() + " не найден");
    }

    private int getId() {
        int currentMaxId = users.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
