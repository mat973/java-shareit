package ru.practicum.shareit.request.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestResponseDto {
    private Long id;
    private String description;
    private Long requesterId;
    private LocalDateTime created;
    @Builder.Default
    private List<ItemForRequestDro> items = Collections.emptyList();
}
