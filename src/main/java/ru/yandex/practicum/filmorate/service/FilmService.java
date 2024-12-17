package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.*;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final FilmGenreRepository filmGenreRepository;
    private final FilmLikeRepository filmLikeRepository;
    private final MpaRepository mpaRepository;
    private final GenreRepository genreRepository;
    private final FilmRepository filmRepository;
    private static final Logger log = LoggerFactory.getLogger("FilmService");

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage,
                       FilmGenreRepository filmGenreRepository, FilmLikeRepository filmLikeRepository,
                       MpaRepository mpaRepository, GenreRepository genreRepository, FilmRepository filmRepository) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.filmGenreRepository = filmGenreRepository;
        this.filmLikeRepository = filmLikeRepository;
        this.mpaRepository = mpaRepository;
        this.genreRepository = genreRepository;
        this.filmRepository = filmRepository;
    }

    public FilmDto addLike(Integer id, Integer userId) {
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
        FilmDto response = FilmMapper.mapToFilmDto(film);
        response.getLikes().add(userId);

        log.info("Added new like");
        return response;

    }

    public FilmDto deleteLike(Integer id, Integer userId) {
        log.info("Starting to delete like");
        Film film = filmStorage.getFilms().get(id);

        if (userStorage.getUserById(userId) == null || userStorage.getUserById(id) == null) {
            log.error("User not found");
            throw new NotFoundException("Юзер не найден");
        }

        filmLikeRepository.deleteLike(id, userId);
        log.info("Like deleted");
        return FilmMapper.mapToFilmDto(film);
    }

    public List<FilmDto> mostLiked(Integer count) {
        return filmStorage.mostLiked(count).stream()
                .map(FilmMapper::mapToFilmDto)
                .map(this::addGenresToFilmDto)
                .toList();
    }

    public Collection<FilmDto> allFilms() {
        return filmStorage.allFilms().stream()
                .map(FilmMapper::mapToFilmDto)
                .map(this::addGenresToFilmDto)
                .toList();
    }

    public FilmDto addFilm(Film film) {
        Film newFilm = filmStorage.addFilm(film);
        if (newFilm.getId() == null) {
            throw new ValidationException("Фильм не был добавлен корректно, ID равен null");
        }
        filmRepository.addFilm(film);
        setMpa(film);
        setGenres(film);
        return FilmMapper.mapToFilmDto(newFilm);
    }

    public FilmDto update(UpdateFilmRequest request) {
        if (!request.hasId()) {
            throw new InternalServerException("Не передан id фильма");
        }

        Film updateFilm = FilmMapper.updateFilmFields(filmStorage.getFilmById(request.getId()), request);
        updateFilm = filmStorage.update(updateFilm);
        setMpa(updateFilm);
        setGenres(updateFilm);
        return FilmMapper.mapToFilmDto(updateFilm);

    }

    public FilmDto getWithGenre(Integer id) {
        return FilmMapper.mapToFilmDto(filmStorage.getFilmById(id));
    }

    private FilmDto addGenresToFilmDto(FilmDto filmDto) {
        List<Genre> filmGenresList = filmGenreRepository.getGenresByFilmId(filmDto.getId())
                .stream()
                .map(FilmGenre::getGenre)
                .toList();
        filmDto.setGenres(filmGenresList);
        return filmDto;
    }

    private void setMpa(Film film) {
        if (film.getMpa() != null) {
            Integer mpaId = film.getMpa().getId();
            Mpa mpa = mpaRepository.findById(mpaId);
            film.setMpa(mpa);
        }
    }

    private void setGenres(Film film) {
        if (film.getGenres() != null) {
            List<Genre> genres = film.getGenres().stream()
                    .map(genre -> genreRepository.findById(genre.getId()))
                    .distinct()
                    .toList();
            film.setGenres(genres);
        }
    }

}
