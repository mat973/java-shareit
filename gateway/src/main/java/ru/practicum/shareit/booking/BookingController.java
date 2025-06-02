package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;


@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

	private final BookingClient bookingClient;

	@PostMapping
	public ResponseEntity<Object> createBooking(
			@RequestHeader("X-Sharer-User-Id") long userId,
			@Validated @RequestBody BookingDto bookingDto) {
		log.info("Gateway: создание бронирования {} пользователем {}", bookingDto, userId);
		return bookingClient.createBooking(userId, bookingDto);
	}

	@PatchMapping("/{bookingId}")
	public ResponseEntity<Object> decideBooking(
			@RequestHeader("X-Sharer-User-Id") long userId,
			@PathVariable Long bookingId,
			@RequestParam("approved") Boolean approved) {
		log.info("Gateway: подтверждение/отклонение бронирования {}, решение: {}, от пользователя {}", bookingId, approved, userId);
		return bookingClient.decideBooking(userId, bookingId, approved);
	}

	@GetMapping("/{bookingId}")
	public ResponseEntity<Object> getBookingInfo(
			@RequestHeader("X-Sharer-User-Id") long userId,
			@PathVariable Long bookingId) {
		log.info("Gateway: получение информации о бронировании {} пользователем {}", bookingId, userId);
		return bookingClient.getBookingInfo(userId, bookingId);
	}

	@GetMapping("/owner")
	public ResponseEntity<Object> getAllBookingItemForOwner(
			@RequestHeader("X-Sharer-User-Id") long userId,
			@RequestParam(defaultValue = "ALL") String state) {
		log.info("Gateway: получение всех бронирований владельца {} с фильтром по состоянию {}", userId, state);
		return bookingClient.getAllBookingItemForOwner(userId, state);
	}

	@GetMapping
	public ResponseEntity<Object> getAllBookingItemForBooker(
			@RequestHeader("X-Sharer-User-Id") long userId,
			@RequestParam(defaultValue = "ALL") String state) {
		log.info("Gateway: получение всех бронирований пользователя {} с фильтром по состоянию {}", userId, state);
		return bookingClient.getAllBookingItemForBooker(userId, state);
	}
}

