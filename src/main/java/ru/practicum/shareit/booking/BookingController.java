package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.model.State;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseBookingDto createBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                            @Validated @RequestBody BookingDto bookingDto) {
        log.info("Запрос на создание бронирование вещи {} пользователем {}", bookingDto, userId);
        return bookingService.createBooking(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseBookingDto decideBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                            @RequestParam(value = "approved") Boolean approved,
                                            @PathVariable Long bookingId) {
        log.info("Запрос на подтвержение или отклонение бронирование пользователем с userId {} на запрос с bookingId {}" +
                "с решением approved {}", userId, bookingId, approved);
        return bookingService.decideBooking(bookingId, userId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseBookingDto getBookingInfo(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable Long bookingId) {
        log.info("Запрос на получени инофрмации о бронировании с bookingId{} пользоватем с userId {}", bookingId, userId);
        return bookingService.getBookingInfo(bookingId, userId);
    }


    @GetMapping("/owner")
    public List<ResponseBookingDto> getAllBookingItemForOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                                              @RequestParam(defaultValue = "ALL", value = "state") State state) {
        log.info("Получени запросов на бронирования вещей пользователя с userId {} с доп запросом на tate {}", userId, state);
        return bookingService.getAllBookingItemForOwner(userId, state);
    }


    @GetMapping()
    public List<ResponseBookingDto> getAllBookingItemForBooker(@RequestHeader("X-Sharer-User-Id") long userId,
                                                               @RequestParam(defaultValue = "ALL", value = "state") State state) {
        log.info("Получени запросов на бронирования вещей пользователем с userId {} с доп запросом на tate {}", userId, state);
        return bookingService.getAllBookingItemForBooker(userId, state);
    }
}
