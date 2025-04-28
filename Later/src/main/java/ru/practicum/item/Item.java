package ru.practicum.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
class Item {
    private Long id;
    @NotNull
    @Positive
    private Long userId;
    private String url;
}