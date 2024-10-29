package ru.yandex.practicum.filmorate.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger("InMemoryUserStorage");

    @Override
    public User getUserById(Integer id) {
        log.info("Ищем пользователя с id = {}", id);
        if (!users.containsKey(id)) {
            log.error("Юзер не найден");
            throw new NotFoundException("Юзер не найден");
        }
        return users.get(id);
    }


    @Override
    public Collection<User> allUsers() {
        return users.values();
    }

    @Override
    public User create(User user) {
        log.info("Starting to create new user");
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new ValidationException("Некорректный email");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        user.setId(getId());
        user.setFriend();
        users.put(user.getId(), user);
        log.info("Created new user");
        return user;
    }

    @Override
    public User update(User user) {
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
            if (user.getFriends() != null) {
                oldUser.setFriends(user.getFriends());
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
