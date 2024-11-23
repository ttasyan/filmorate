package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.FriendshipRepository;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;
    private final FriendshipRepository friendshipRepository;
    private static final Logger log = LoggerFactory.getLogger("UserService");


    @Autowired
    public UserService(UserStorage userStorage, FriendshipRepository friendshipRepository) {
        this.userStorage = userStorage;
        this.friendshipRepository = friendshipRepository;
    }

    public UserDto deleteFriend(Integer id, Integer friendId) {
        log.info("Starting to delete a friend");
        UserDto response = UserMapper.mapToUserDto(userStorage.getUserById(id));
        UserDto friend = UserMapper.mapToUserDto(userStorage.getUserById(friendId));

        friendshipRepository.deleteFriend(id, friendId);

        response.getFriendIds().remove(friendId);
        friend.getFriendIds().remove(id);
        UpdateUserRequest updateRequest = new UpdateUserRequest();
        updateRequest.setId(response.getId());

        update(updateRequest);
        updateRequest.setId(friend.getId());

        update(updateRequest);

        log.info("Friend deleted");
        return response;

    }

    public UserDto addFriend(Integer id, Integer friendId) {
        log.info("Starting to add new friend");
        UserDto response = UserMapper.mapToUserDto(userStorage.getUserById(id));
        UserDto friend = UserMapper.mapToUserDto(userStorage.getUserById(friendId));
        List<User> userList = friendshipRepository.allFriends(id);


         friendshipRepository.addFriend(id, friendId);
        userList.add(userStorage.getUserById(friendId));
        response.setFriendIds(userList.stream()
                .map(User::getId)
                .collect(Collectors.toSet()));

        log.info("Added new friend");
        return response;
    }

    public Collection<UserDto> allFriends(Integer id) {
        User user = userStorage.getUserById(id);
        if (user == null) {
            throw new NotFoundException("Юзер не найден");
        }

        return user.getFriendIds().stream()
                .map(userStorage::getUserById)
                .map(UserMapper::mapToUserDto)
                .toList();

    }

    public List<UserDto> commonFriends(Integer id, Integer otherId) {
        User user1 = userStorage.getUserById(id);
        User user2 = userStorage.getUserById(otherId);

        if (user1 == null || user2 == null) {
            throw new NotFoundException("One or both users not found");
        }
        Set<Integer> friends1 = user1.getFriendIds();

        Set<Integer> friends2 = user2.getFriendIds();

        friends1.retainAll(friends2);

        return friends1.stream()
                .map(userStorage::getUserById)
                .map(UserMapper::mapToUserDto)
                .toList();

    }

    public List<UserDto> allUsers() {
        return userStorage.allUsers().stream()
                .map(UserMapper::mapToUserDto)
                .toList();
    }

    public UserDto create(User user) {
        return UserMapper.mapToUserDto(userStorage.create(user));
    }

    public UserDto update(UpdateUserRequest request) {
        User user = userStorage.getUserById(request.getId());
        User updateUser = UserMapper.updateUserFields(user, request);
        updateUser = userStorage.update(updateUser);
        return UserMapper.mapToUserDto(updateUser);
    }
}