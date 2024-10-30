package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

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
        return user.getFriends().stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    public List<User> commonFriends(Integer id, Integer otherId) {
        User user1 = userStorage.getUserById(id);
        User user2 = userStorage.getUserById(otherId);

        if (user1 == null || user2 == null) {
            throw new NotFoundException("One or both users not found");
        }
        Set<Integer> friends1 = user1.getFriends();
        Set<Integer> friends2 = user2.getFriends();
        Set<Integer> commonFriendIds = new HashSet<>(friends1);
        commonFriendIds.retainAll(friends2);
        return commonFriendIds.stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    public Collection<User> allUsers() {
        return userStorage.allUsers();
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }
}