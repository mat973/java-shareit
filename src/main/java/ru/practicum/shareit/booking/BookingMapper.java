package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.booking.dto.BookingUserDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

public class BookingMapper {

    static Booking mapToBooking(BookingDto bookingDto, User owner, Item item) {
        return Booking.builder()
                .id(bookingDto.getId())
                .booker(owner)
                .item(item)
                .startDate(LocalDateTime.parse(bookingDto.getStart()))
                .endDate(LocalDateTime.parse(bookingDto.getEnd()))
                .status(bookingDto.getStatus())
                .build();
    }

    static ResponseBookingDto mapResponseToBookingDto(Booking booking) {
        return ResponseBookingDto.builder()
                .id(booking.getId())
                .booker(BookingUserDto.createBookingUserDto(booking.getBooker()))
                .item(BookingItemDto.createBookingItemDto(booking.getItem()))
                .start(booking.getStartDate().toString())
                .end(booking.getEndDate().toString())
                .status(booking.getStatus())
                .build();
    }


//    private static LocalDateTime parseStringToDate(String date){
//
//    }
}
