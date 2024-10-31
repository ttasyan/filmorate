package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ReleaseDateConstraint;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@Builder
public class Film {
    private Integer id;
    private String name;
    @Size(max = 200)
    private String description;
    @ReleaseDateConstraint
    private LocalDate releaseDate;
    @Positive
    private Integer duration;
    @Builder.Default
    private Set<Integer> likes = new HashSet<>();

    public void addLike(Integer filmId) {
        likes.add(filmId);
    }

    public void setLikes() {
        likes = new HashSet<>();
    }
}

