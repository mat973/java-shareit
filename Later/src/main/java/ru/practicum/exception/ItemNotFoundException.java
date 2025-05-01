package ru.practicum.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Long itemId) {
        super("Предмет с id " + itemId + " на найден");
    }
}
