package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Max(value = 6)
    private Integer id;
    private String name;
}