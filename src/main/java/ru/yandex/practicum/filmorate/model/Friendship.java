package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Friendship {
    private int userId;
    private int friendId;
    private boolean status;

}
