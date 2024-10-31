package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    public User getUserById(Integer id);

    public Collection<User> allUsers();

    public User create(User user);

    public User update(User user);
}
