package ru.yandex.practicum.filmorate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class ValidationException extends  RuntimeException {
    private String parameter;
    private String reason;

    public ValidationException(String s) {
        super(s);
    }
}