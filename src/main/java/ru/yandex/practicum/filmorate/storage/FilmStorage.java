package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface FilmStorage {
    Map<Integer, Film> getFilms();

    Collection<Film> allFilms();

    Film addFilm(Film film);

    Film update(Film newFilm);

    Film getFilmById(Integer id);

    List<Film> mostLiked(Integer count);
}
