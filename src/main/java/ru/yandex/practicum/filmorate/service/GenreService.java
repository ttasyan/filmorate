package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public Collection<Genre> allGenres() {
        return genreRepository.allGenres();
    }

    public Genre getGenreById(int id) {

        try {
            return genreRepository.findById(id);
        } catch (Exception e) {
            throw new NotFoundException("Жанр с заданным id не найден");
        }
    }
}
