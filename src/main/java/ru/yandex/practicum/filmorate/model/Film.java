package ru.yandex.practicum.filmorate.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validation.ReleaseDateConstraint;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    private Integer id;
    @NotEmpty
    private String name;
    @Size(max = 200)
    private String description;
    @ReleaseDateConstraint
    private LocalDate releaseDate;
    @Positive
    private Integer duration;
    @Builder.Default
    private Set<Integer> likes = new HashSet<>();
    private List<@Valid Genre> genres = new ArrayList<>();
    @Valid
    private Mpa mpa;

    public void addLike(Integer filmId) {
        likes.add(filmId);
    }

    public void setLikes() {
        likes = new HashSet<>();
    }
}

