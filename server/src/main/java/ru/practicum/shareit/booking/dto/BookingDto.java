package ru.practicum.shareit.booking.dto;


import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.model.Status;


@Data
@Builder
public class BookingDto {
    private Long id;
    private String start;
    private String end;
    private Long itemId;
    private Long bookerId;
    private Status status;
}
