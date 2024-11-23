package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor

public class FilmController {
    private final FilmService filmService;


    @GetMapping
    public Collection<FilmDto> allFilms() {
        return filmService.allFilms();
    }

    @GetMapping("/popular")
    public List<FilmDto> popularFilms(@RequestParam(required = false, defaultValue = "10") Integer count) {
        return filmService.mostLiked(count);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDto addFilm(@Valid @RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping
    public FilmDto update(@RequestBody UpdateFilmRequest request) {
        return filmService.update(request);

    }

    @PutMapping("/{id}/like/{userId}")
    public FilmDto addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public FilmDto deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        return filmService.deleteLike(id, userId);
    }
}
