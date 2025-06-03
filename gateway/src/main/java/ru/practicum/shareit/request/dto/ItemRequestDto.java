package ru.practicum.shareit.request.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    private Long id;
    @NotBlank(message = "Опсиание не можт быть путсым")
    private String description;
    private Long requesterId;
    private LocalDateTime created;
}
