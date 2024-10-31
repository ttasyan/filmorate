package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private static final Logger log = LoggerFactory.getLogger("FilmService");

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film addLike(Integer id, Integer userId) {
        log.info("Starting to add new like");
        Film film = filmStorage.getFilms().get(id);
        if (film == null) {
            log.error("Film not found");
            throw new NotFoundException("Фильм с id = " + id + " не найден");
        }
        if (userStorage.getUserById(userId) == null) {
            log.error("User not found");
            throw new NotFoundException("Юзер не найден");
        }
        if (film.getLikes().contains(userId)) {
            log.error("Like by user {} already exists", userId);
            throw new ValidationException("Этот юзер уже поставил лайк");
        }
        film.addLike(userId);
        filmStorage.update(film);
        log.info("Added new like");
        return filmStorage.getFilms().get(id);

    }

    public Film deleteLike(Integer id, Integer userId) {
        log.info("Starting to delete like");
        Film film = filmStorage.getFilms().get(id);
        if (film == null) {
            log.error("Film not found");
            throw new NotFoundException("Фильм с id = " + id + " не найден");
        }
        if (userStorage.getUserById(userId) == null || userStorage.getUserById(id) == null) {
            log.error("User not found");
            throw new NotFoundException("Юзер не найден");
        }

        film.getLikes().remove(userId);
        filmStorage.update(film);
        log.info("Like deleted");
        return filmStorage.getFilms().get(id);
    }

    public List<Film> mostLiked(Integer count) {
        return new ArrayList<>(filmStorage.getFilms().values().stream()
                .sorted(Comparator.comparingInt(f -> f.getLikes().size()))
                .limit(count)
                .toList().reversed());
    }

    public Collection<Film> allFilms() {
        return filmStorage.allFilms();
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film update(Film newFilm) {
        return filmStorage.update(newFilm);

    }
}
