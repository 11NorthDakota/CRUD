package by.northdakota.Exception;

import lombok.Getter;

import java.util.List;
import by.northdakota.Validator.Error;

public class ValidationException extends RuntimeException {
    @Getter
    private final List<Error> errors;

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
}
