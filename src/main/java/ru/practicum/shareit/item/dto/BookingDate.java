package ru.practicum.shareit.item.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDate {
    private LocalDateTime start;
    private LocalDateTime end;

    public BookingDate(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }
}
