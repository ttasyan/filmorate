package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ReleaseDateConstraint;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
public class Film {
    private Integer id;
    private String name;
    private String description;
    @ReleaseDateConstraint
    private LocalDate releaseDate;
    @Positive
    private Integer duration;
    private List<Integer> likes;

    public void addLike(Integer filmId) {
        likes.add(filmId);
    }

    public List<Integer> setLikes() {
        return likes = new ArrayList<>();
    }
}

