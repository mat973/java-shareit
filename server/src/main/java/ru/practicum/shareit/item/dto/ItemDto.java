package ru.practicum.shareit.item.dto;


import lombok.Builder;
import lombok.Data;


import java.util.List;


@Data
@Builder
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private BookingDate lastBooking;
    private BookingDate nextBooking;
    private List<CommentDto> comments;
    private Long requestId;
}
