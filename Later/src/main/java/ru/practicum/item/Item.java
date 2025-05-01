package ru.practicum.item;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class Item {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private Boolean available;
}