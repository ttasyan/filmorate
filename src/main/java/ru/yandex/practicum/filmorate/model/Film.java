package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ReleaseDateConstraint;


import java.time.LocalDate;


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
}
