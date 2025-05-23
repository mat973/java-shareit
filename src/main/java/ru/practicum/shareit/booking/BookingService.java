package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.model.State;

import java.util.List;

public interface BookingService {
    ResponseBookingDto createBooking(BookingDto bookingDto, long userId);

    ResponseBookingDto decideBooking(Long bookingId, long userId, Boolean approved);

    ResponseBookingDto getBookingInfo(Long bookingId, long userId);

    List<ResponseBookingDto> getAllBookingItemForOwner(long userId, State state);

    List<ResponseBookingDto> getAllBookingItemForBooker(long userId, State state);
}
