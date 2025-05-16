package ru.practicum.shareit.booking.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "booker_id")
    private Long  bookerId;
    @Enumerated(EnumType.STRING)
    private Status status;
}
