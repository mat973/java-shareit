package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingMapperTest {

    private User user;
    private Item item;
    private Booking booking;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("User");

        item = new Item();
        item.setId(1L);
        item.setName("Item");
        item.setOwner(user);

        booking = Booking.builder()
                .id(1L)
                .booker(user)
                .item(item)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .status(Status.WAITING)
                .build();
    }

    @Test
    void toBookingDto_shouldMapCorrectly() {
        ResponseBookingDto dto = BookingMapper.mapResponseToBookingDto(booking);

        assertEquals(1L, dto.getId());
        assertEquals(Status.WAITING, dto.getStatus());
        assertEquals(1L, dto.getBooker().getId());
        assertEquals(1L, dto.getItem().getId());
    }

    @Test
    void toBooking_shouldMapCorrectly() {
        BookingDto bookingDto = BookingDto.builder()
                .start(LocalDateTime.now().toString())
                .end(LocalDateTime.now().plusHours(2).toString())
                .build();

        Booking result = BookingMapper.mapToBooking(bookingDto, user, item);

        assertEquals(item, result.getItem());
        assertEquals(user, result.getBooker());
    }
}

