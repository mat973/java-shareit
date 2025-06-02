package ru.practicum.shareit.exception;

public class ItemRequestNotFoundException extends RuntimeException {
    public ItemRequestNotFoundException(Long id) {
        super("Запрос на вещи с id " + id + " не найден");
    }
}
