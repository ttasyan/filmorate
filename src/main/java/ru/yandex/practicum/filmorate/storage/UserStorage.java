package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
     User getUserById(Integer id);

     Collection<User> allUsers();

     User create(User user);

     User update(User user);
}
