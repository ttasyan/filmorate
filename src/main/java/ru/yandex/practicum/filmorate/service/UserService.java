package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.FriendshipRepository;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.Friendship;
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

        List<Friendship> userList = friendshipRepository.allFriends(id);
        List<Friendship> friendList = friendshipRepository.allFriends(friendId);

        if (!checkFriendshipExists(userList, id, friendId)) {
            return response;
        }
        checkAndUpdateFriendshipStatus(id, friendId, friendList, false);

        if (friendshipRepository.deleteFriend(id, friendId)) {
            log.info("Friend deleted");
            return response;
        }

        throw new InternalServerException("Не удалось удалить друга с id: " + friendId);

    }

    public UserDto addFriend(Integer id, Integer friendId) {
        log.info("Starting to add new friend");
        UserDto response = UserMapper.mapToUserDto(userStorage.getUserById(id));
        UserDto friend = UserMapper.mapToUserDto(userStorage.getUserById(friendId));
        List<Friendship> userList = friendshipRepository.allFriends(id);
        List<Friendship> friendList = friendshipRepository.allFriends(friendId);
        if (checkFriendshipExists(userList, id, friendId)) {
            throw new InternalServerException("Пользователь с id: " + id + " уже добавлял пользователя с id: "
                    + friendId + " в друзья");
        }
        boolean status = checkAndUpdateFriendshipStatus(id, friendId, friendList, true);
        Friendship friendship = friendshipRepository.addFriend(id, friendId, status);
        userList.add(friendship);
        response.setFriendIds(userList.stream()
                .map(Friendship::getFriendId)
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
        return friendshipRepository.commonFriends(id, otherId).stream()
                .map(Friendship::getFriendId)
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

    private boolean checkAndUpdateFriendshipStatus(Integer id, Integer friendId, List<Friendship> friendFriends,
                                                   boolean accept) {
        if (friendFriends.stream().anyMatch(friend -> friend.getFriendId() == (id))) {
            friendshipRepository.updateStatus(friendId, id, accept);
        } else {
            accept = !accept;
        }
        return accept;
    }

    private boolean checkFriendshipExists(List<Friendship> userFriends, Integer userId, Integer friendId) {
        return userFriends.stream().anyMatch(friend -> friend.getFriendId() == (friendId));
    }

}