package ru.practicum.shareit.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private Long id;

    @NotBlank(message = "Не может быть пустым почта")
    @Email(message = "Почта должна быть почта, а не не почта")
    private String email;

    @NotBlank(message = "Не может быть пустым имя")
    private String name;
}
