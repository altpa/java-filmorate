package ru.yandex.practicum.filmorate.validatorAnnotations.spacesValidatorAnnotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
        ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SpacesValidator.class)
@Documented
public @interface NoSpaces {
    String message() default "Ошибка в данных юзера, логин не должен содержать пробелы: ${validatedValue}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
