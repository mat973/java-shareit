package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

@AllArgsConstructor
@Data
public class BookingUserDto {
    private Long id;

    public static BookingUserDto createBookingUserDto(User user) {
        return new BookingUserDto(user.getId());
    }
}
