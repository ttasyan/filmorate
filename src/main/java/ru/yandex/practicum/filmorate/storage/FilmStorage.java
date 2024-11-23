package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Map;

public interface FilmStorage {
    public Map<Integer, Film> getFilms();

    public Collection<Film> allFilms();

    public Film addFilm(Film film);

    public Film update(Film newFilm);

    public Film getFilmById(Integer id);
}
