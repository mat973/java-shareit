package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.validinterface.Create;


@Data
@Builder
public class ItemDto {
    private Long id;
    @NotBlank(groups = Create.class, message = "Имя не может быть пустым")
    private String name;
    @NotBlank(groups = Create.class, message = "Описание не может быть пустым")
    private String description;
    @NotNull(groups = Create.class, message = "Достуность не может быть пустым")
    private Boolean available;
}
