package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.FilmGenreRepository;
import ru.yandex.practicum.filmorate.dal.FilmLikeRepository;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final FilmGenreRepository filmGenreRepository;
    private final FilmLikeRepository filmLikeRepository;
    private static final Logger log = LoggerFactory.getLogger("FilmService");

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage,
                       FilmGenreRepository filmGenreRepository, FilmLikeRepository filmLikeRepository) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.filmGenreRepository = filmGenreRepository;
        this.filmLikeRepository = filmLikeRepository;
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
        filmLikeRepository.addLike(id, userId);
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

//        film.getLikes().remove(userId);
//        filmStorage.update(film);
        filmLikeRepository.deleteLike(id, userId);
        log.info("Like deleted");
        return FilmMapper.mapToFilmDto(film);
    }

    public List<FilmDto> mostLiked(Integer count) {
        List<Film> allFilms = new ArrayList<>(filmStorage.getFilms().values().stream()
                .sorted(Comparator.comparingInt(f -> f.getLikes().size()))
                .toList()
                .reversed());

        List<Film> topFilms = new ArrayList<>();
        for (int i = 0; i < Math.min(count, allFilms.size()); i++) {
            topFilms.add(allFilms.get(i));
        }
        return topFilms.stream()
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
        if (newFilm.getGenres() != null) {
            for (Genre genre : newFilm.getGenres()) {
                if (genre.getId() == null) {
                    throw new ValidationException("Жанр не имеет ID");
                }
                filmGenreRepository.addGenreToFilm(newFilm.getId(), genre.getId());
            }
        } else {
            newFilm.setGenres(new ArrayList<>());
        }
        return FilmMapper.mapToFilmDto(newFilm);
    }

    public FilmDto update(UpdateFilmRequest request) {
        if (!request.hasId()) {
            throw new InternalServerException("Не передан id фильма");
        }

        Film updateFilm = FilmMapper.updateFilmFields(filmStorage.getFilmById(request.getId()), request);
        updateFilm = filmStorage.update(updateFilm);
        return FilmMapper.mapToFilmDto(updateFilm);

    }

    private FilmDto addGenresToFilmDto(FilmDto filmDto) {
        List<Genre> filmGenresList = filmGenreRepository.getGenresByFilmId(filmDto.getId())
                .stream()
                .map(FilmGenre::getGenre)
                .toList();
        filmDto.setGenres(filmGenresList);
        return filmDto;
    }
}
