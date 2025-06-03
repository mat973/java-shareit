package ru.practicum.shareit.booking;


import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class BookingTest {

    @Test
    void testEqualsAndHashCode() {
        Booking b1 = Booking.builder().id(1L).build();
        Booking b2 = Booking.builder().id(1L).build();
        Booking b3 = Booking.builder().id(2L).build();

        assertThat(b1).isEqualTo(b2);
        assertThat(b1).hasSameHashCodeAs(b2);
        assertThat(b1).isNotEqualTo(b3);
    }

    @Test
    void testBuilderAndFields() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(1);
        User user = User.builder().id(1L).name("U").email("e@mail.com").build();
        Item item = Item.builder().id(1L).name("i").description("d").available(true).owner(user).build();

        Booking booking = Booking.builder()
                .id(10L)
                .startDate(start)
                .endDate(end)
                .item(item)
                .booker(user)
                .status(Status.APPROVED)
                .build();

        assertThat(booking.getStartDate()).isEqualTo(start);
        assertThat(booking.getEndDate()).isEqualTo(end);
        assertThat(booking.getItem()).isEqualTo(item);
        assertThat(booking.getBooker()).isEqualTo(user);
        assertThat(booking.getStatus()).isEqualTo(Status.APPROVED);
    }
}

