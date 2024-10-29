package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserService {
    private final UserStorage userStorage;
    private static final Logger log = LoggerFactory.getLogger("UserService");


    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User deleteFriend(Integer id, Integer friendId) {
        log.info("Starting to delete a friend");
        if (userStorage.getUserById(friendId) == null) {
            log.error("Friend not found");
            throw new NotFoundException("Друг не найден");
        }
        User user = userStorage.getUserById(id);
        User friend = userStorage.getUserById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(id);
        userStorage.update(user);
        userStorage.update(friend);
        log.info("Friend deleted");
        return userStorage.getUserById(id);

    }

    public User addFriend(Integer id, Integer friendId) {
        log.info("Starting to add new friend");
        if (userStorage.getUserById(id) == null || userStorage.getUserById(friendId) == null) {
            log.error("User not found");
            throw new NotFoundException("Друг не найден");
        }
        User user = userStorage.getUserById(id);
        User friend = userStorage.getUserById(friendId);
        if (user.getFriends().contains(friendId)) {
            log.error("Friend already exists");
            throw new ValidationException("Такой друг уже добавлен");
        }
        user.addFriend(friend.getId());
        friend.addFriend(user.getId());
        userStorage.update(user);
        userStorage.update(friend);
        log.info("Added new friend");
        return user;
    }

    public Collection<User> allFriends(Integer id) {
        User user = userStorage.getUserById(id);
        Collection<User> friends = new ArrayList<>();
        for (Integer userId : user.getFriends()) {
            friends.add(userStorage.getUserById(userId));
        }
        return friends;
    }
}
