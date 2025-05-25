package ru.practicum.shareit.item.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDate {
    LocalDateTime start;
    LocalDateTime end;

    public BookingDate(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }
}
