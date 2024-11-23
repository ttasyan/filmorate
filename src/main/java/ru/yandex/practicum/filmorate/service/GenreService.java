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
private Map<Integer, Genre> genres = new HashMap<>();
    public Collection<Genre> allGenres() {
        return genres.values();
    }

    public Genre getGenreById(int id) {

      if (!genres.containsKey(id)) {
            throw new NotFoundException("Рейтинг с заданным id не найден");
        }
        return genres.get(id);
    }
}
