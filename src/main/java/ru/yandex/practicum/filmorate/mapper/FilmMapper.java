package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.model.Film;

public class FilmMapper {
    public static Film updateFilmFields(Film film, UpdateFilmRequest request) {
        if (request.hasId()) {
            film.setId(request.getId());
        }
        if (request.hasDescription()) {
            film.setDescription(request.getDescription());
        }
        if (request.hasReleaseDate()) {
            film.setReleaseDate(request.getReleaseDate());
        }
        if (request.hasName()) {
            film.setName(request.getName());
        }
        if (request.hasDuration()) {
            film.setDuration(request.getDuration());
        }
        return film;
    }

    public static FilmDto mapToFilmDto(Film film) {
        FilmDto dto = new FilmDto();
        dto.setId(film.getId());
        dto.setDescription(film.getDescription());
        dto.setDuration(film.getDuration());
        dto.setName(film.getName());
        dto.setMpa(film.getMpa());
        dto.setLikes(film.getLikes());
        dto.setReleaseDate(film.getReleaseDate());
        dto.setGenres(film.getGenres());
        return dto;
    }
}
