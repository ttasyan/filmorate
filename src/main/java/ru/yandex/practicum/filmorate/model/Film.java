package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ReleaseDateConstraint;


import java.time.LocalDate;


@Data
@Builder
public class Film {
    private Integer id;
    @NotBlank(message =  "Имя не может быть пустым")
    private String name;
    @Max(200)
    private String description;
    @ReleaseDateConstraint
    private LocalDate releaseDate;
    @Positive(message = "Длительность не должна быть меньше 0")
    private Integer duration;
}
