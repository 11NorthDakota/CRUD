package by.northdakota.Validator;

public interface Validator<T> {
    ValidationResult isValid(T t);
}
