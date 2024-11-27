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
public class Mpa {
    @Max(value = 5)
    private int id;
    private String name;
}
