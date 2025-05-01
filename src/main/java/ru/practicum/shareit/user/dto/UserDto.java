package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import ru.practicum.validinterface.Create;

@Data
@Builder
public class UserDto {
    private Long id;
    @NotBlank(groups = Create.class, message = "Не может быть пустым почта")
    @Email(groups = Create.class, message = "Почта должна быть почта, а не не почта")
    private String email;
    @NotBlank(groups = Create.class, message = "Не может быть пустым имя")
    private String name;
}
