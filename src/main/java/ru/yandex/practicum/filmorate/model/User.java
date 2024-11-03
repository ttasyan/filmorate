package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class User {
    private Integer id;
    @Email
    @NotNull
    private String email;
    @NotBlank(message = "Логин не должен быть пустым")
    private String login;
    private String name;
    @Past(message = "День рождения не может быть в будущем")
    private LocalDate birthday;
    @Builder.Default
    private Set<Integer> friends = new HashSet<>();
    private Map<Integer, Status> statusOfFriends = new HashMap<>();

    public void addFriend(Integer friendId, Status status) {
        friends.add(friendId);
        statusOfFriends.put(friendId, status);
    }

    public void deleteFriend(Integer friendId) {
        friends.remove(friendId);
        statusOfFriends.remove(friendId);
    }

    public void setFriends() {
        friends = new HashSet<>();
    }
}
