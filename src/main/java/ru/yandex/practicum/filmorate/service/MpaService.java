package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.MpaRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaRepository mpaRepository;

    public Collection<Mpa> allRatings() {
        return mpaRepository.allRatings();
    }

    public Mpa getRatingById(int id) {
        if (!mpaRepository.allRatings().contains(id)) {
            throw new NotFoundException("Рейтинг с заданным id не найден");
        }
        return mpaRepository.findById(id);
    }
}
