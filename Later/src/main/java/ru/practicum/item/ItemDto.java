package ru.practicum.item;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
public class ItemDto {
    private Long itemId;
    @NotBlank(groups = Create.class, message = "Имя не может быть пустым")
    private String name;
    @
    private String description;
    private Boolean available;
}
