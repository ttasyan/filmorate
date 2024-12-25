package ru.yandex.practicum.filmorate.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    private Integer id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;

    public boolean hasId() {
        return !(id == null || id <= 0);
    }

    public boolean hasEmail() {
        return !(email == null || email.isBlank());
    }

    public boolean hasLogin() {
        return !(login == null || login.isBlank());
    }

    public boolean hasName() {
        return !(name == null || name.isBlank());
    }

    public boolean hasBirthday() {
        return !(birthday == null);
    }

}

