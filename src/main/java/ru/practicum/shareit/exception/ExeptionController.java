package ru.practicum.shareit.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ExeptionController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto itemNotFoundExceptionHandler(ItemNotFoundException e) {
        return new ExceptionDto(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto userNotFoundExceptionHandler(UserNotFoundException e) {
        return new ExceptionDto(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionDto notUnicEmailExceptionHandler(NotUnicEmailException e) {
        return new ExceptionDto(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleValidation(MethodArgumentNotValidException e) {
        boolean isNegativeIdError = e.getBindingResult().getFieldErrors().stream()
                .anyMatch(err -> err.getField().toLowerCase().contains("id")
                        && err.getDefaultMessage().toLowerCase().contains("положительным"));

        String result = e.getBindingResult().getFieldErrors().stream()
                .map(err -> "В поле '" + err.getField() + "' ошибка: " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));


        return new ResponseEntity<>(new ExceptionDto(result), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionDto permitionDenidedExceptionHandler(PermitionDenidedException e) {
        return new ExceptionDto(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDto handleThrowable(final Throwable e) {
        return new ExceptionDto(e.getMessage());
    }
}
