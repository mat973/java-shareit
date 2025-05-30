package ru.practicum.shareit.request.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.dto.UserDto;

@Getter
@Setter
public class ItemRequestDto {
    private Long id;
    @NotEmpty(message = "Опсиание не можт быть путсым")
    private String description;
    private UserDto userDto;

}
