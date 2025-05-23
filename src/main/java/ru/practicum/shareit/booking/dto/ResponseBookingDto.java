package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

@Data
@Builder
public class ResponseBookingDto {
    private Long id;
    private String start;
    private String end;
    private BookingItemDto item;
    private BookingUserDto booker;
    private Status status;
}
