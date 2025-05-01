package ru.practicum.exception;

public class NotUnicEmailException extends RuntimeException {
    public NotUnicEmailException(String message) {
        super(message);
    }
}
