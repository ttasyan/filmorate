package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger("FilmController");
    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> allFilms() {
        return films.values();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Starting to add new film");
        if (film.getName().isEmpty()) {
            throw new ValidationException("Название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.debug("Description's length is more than 200");
            throw new ValidationException("Максимальное количество символов - 200");
        }
        film.setId(getId());
        films.put(film.getId(), film);
        log.info("Added new film");
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        if (newFilm.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }

        if (newFilm.getDescription().length() > 200) {
            log.debug("Description's length is more than 200");
            throw new ValidationException("Максимальное количество символов - 200");
        }
        log.info("Updating film");

        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());

            if (newFilm.getDescription() != null) {
                oldFilm.setDescription(newFilm.getDescription());
            }
            if (newFilm.getName() != null) {
                oldFilm.setName(newFilm.getName());
            }
            if (newFilm.getReleaseDate() != null) {
                oldFilm.setReleaseDate(newFilm.getReleaseDate());
            }
            if (newFilm.getDuration() != null) {
                oldFilm.setDuration(newFilm.getDuration());
            }
            log.info("Updated old film");
            return oldFilm;
        }
        log.debug("Film was not found");
        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    private int getId() {
        int currentMaxId = films.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
