package ru.yandex.practicum.filmorate.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private static final Logger log = LoggerFactory.getLogger("InMemoryFilmStorage");
    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public Map<Integer, Film> getFilms() {
        return films;
    }

    @Override
    public Collection<Film> allFilms() {
        return films.values();
    }

    @Override
    public Film addFilm(Film film) {
        log.info("Starting to add new film");
        if (film.getName().isEmpty()) {
            throw new ValidationException("Название не может быть пустым");
        }
        film.setId(getId());
        film.setLikes();
        if (film.getGenres() == null) {
            film.setGenres(new ArrayList<>());
        }
        films.put(film.getId(), film);
        log.info("Added new film");
        return film;
    }

    @Override
    public Film update(Film newFilm) {
        if (newFilm.getId() == null) {
            throw new NotFoundException("Id должен быть указан");
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
            if (newFilm.getLikes() != null) {
                oldFilm.setLikes(newFilm.getLikes());
            }
            if (newFilm.getMpa() != null) {
                oldFilm.setMpa(newFilm.getMpa());
            }
            if (newFilm.getGenres() != null) {
                oldFilm.setGenres(newFilm.getGenres());
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

    @Override
    public Film getFilmById(Integer id) {
        return films.get(id);
    }

    @Override
    public List<Film> mostLiked(Integer count) {
        List<Film> allFilms = new ArrayList<>(getFilms().values().stream()
                .sorted(Comparator.comparingInt(f -> f.getLikes().size()))
                .toList()
                .reversed());

        List<Film> topFilms = new ArrayList<>();
        for (int i = 0; i < Math.min(count, allFilms.size()); i++) {
            topFilms.add(allFilms.get(i));
        }
        return topFilms;
    }
}
