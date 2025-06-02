package ru.practicum.shareit.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExeptionController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto itemNotFoundExceptionHandler(ItemNotFoundException e) {
        log.error(" сообщение : {} ",  e.getMessage());
        return new ExceptionDto(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto userNotFoundExceptionHandler(UserNotFoundException e) {
        log.error(" сообщение : {} ",  e.getMessage());
        return new ExceptionDto(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionDto notUnicEmailExceptionHandler(NotUnicEmailException e) {
        log.error(" сообщение : {} ",  e.getMessage());
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
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionDto permitionDenidedExceptionHandler(PermitionDenidedException e) {
        log.error(" сообщение : {} ",  e.getMessage());
        return new ExceptionDto(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto startDateIsBeforeNowExceptionHandler(DateInvalidException e) {
        log.error(" сообщение : {} ",  e.getMessage());
        return new ExceptionDto(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto itemNotAvailableExceptionHandler(ItemNotAvailableException e) {
        log.error("Стек трейс : {}, сообщение : {} ", e.getStackTrace(), e.getMessage());
        return new ExceptionDto(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto bookingNotFoundException(BookingNotFoundException e) {
        log.error("Стек трейс : {}, сообщение : {} ", e.getStackTrace(), e.getMessage());
        return new ExceptionDto(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto commentFailExceptionHandler(CommentFailException e) {
        log.error("Стек трейс : {}, сообщение : {} ", e.getStackTrace(), e.getMessage());
        return new ExceptionDto(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto itemRequestNotFoundExceptionHandler(ItemRequestNotFoundException e){
        log.error("Стек трейс : {}, сообщение : {} ", e.getStackTrace(), e.getMessage());
        return new ExceptionDto(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDto handleThrowable(final Throwable e) {
        log.error("Стек трейс : {}, сообщение : {} ", e.getStackTrace(), e.getMessage());
        return new ExceptionDto(e.getMessage());
    }
}
