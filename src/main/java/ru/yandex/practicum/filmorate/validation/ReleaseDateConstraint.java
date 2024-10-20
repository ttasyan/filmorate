package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy =  ReleaseDateValidator.class)
public @interface ReleaseDateConstraint {
    String message() default "Release date is wrong";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
