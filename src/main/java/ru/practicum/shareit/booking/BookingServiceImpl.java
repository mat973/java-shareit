package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.State;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.shareit.booking.BookingMapper.mapResponseToBookingDto;
import static ru.practicum.shareit.booking.BookingMapper.mapToBooking;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;

    @Override
    public ResponseBookingDto createBooking(BookingDto bookingDto, long userId) {

        User booker = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Item item = itemRepository.findById(bookingDto.getItemId())
                .orElseThrow(() -> new ItemNotFoundException(bookingDto.getItemId()));
        if (!item.getAvailable()) {
            throw new ItemNotAvailableException("Предмет не доступен");
        }
        bookingDto.setStatus(Status.WAITING);
        bookingDto.setBookerId(userId);
        Booking booking = mapToBooking(bookingDto, booker, item);
        if (booking.getStartDate().isBefore(LocalDateTime.now())) {
            throw new DateInvalidException("Время начала бронирование не может быть рньше текущщего времени");
        }
        if (booking.getEndDate().isBefore(LocalDateTime.now())) {
            throw new DateInvalidException("Время конца бронирование не может быть рньше текущщего времени");
        }

        if (!booking.getStartDate().isBefore(booking.getEndDate())) {
            throw new DateInvalidException("Время конца бронирования должно быть позже время начлаа бронирования");
        }

        return mapResponseToBookingDto(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    public ResponseBookingDto decideBooking(Long bookingId, long userId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId));
        if (booking.getItem().getOwner().getId() != userId) {
            throw new PermitionDenidedException("Только владлц вещи может давать разршни или отклонять бронирование");
        }
        if (approved) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }
        return mapResponseToBookingDto(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseBookingDto getBookingInfo(Long bookingId, long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId));
        if (!(booking.getBooker().getId() == userId || booking.getItem().getOwner().getId() == userId)) {
            throw new PermitionDenidedException("Только владелец вещи или пользователь отправивший " +
                    "запрос на бронирование могут смотреть информацию о запросе на бронирование");
        }
        return mapResponseToBookingDto(booking);
    }

    @Override
    public List<ResponseBookingDto> getAllBookingItemForOwner(long userId, State state) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        return switch (state) {
            case ALL -> bookingRepository.findAllByOwnerIdOrderByStartDateDesc(userId).stream()
                    .map(BookingMapper::mapResponseToBookingDto)
                    .toList();
            case CURRENT -> bookingRepository.findCurrentBookingsByOwner(userId, LocalDateTime.now()).stream()
                    .map(BookingMapper::mapResponseToBookingDto)
                    .toList();
            case PAST -> bookingRepository.findPastBookingsByOwner(userId, LocalDateTime.now()).stream()
                    .map(BookingMapper::mapResponseToBookingDto)
                    .toList();
            case FUTURE -> bookingRepository.findFutureBookingsByOwner(userId, LocalDateTime.now()).stream()
                    .map(BookingMapper::mapResponseToBookingDto)
                    .toList();
            case WAITING -> bookingRepository.findWaitingBookingsByOwner(userId).stream()
                    .map(BookingMapper::mapResponseToBookingDto)
                    .toList();
            case REJECTED -> bookingRepository.findRejectedBookingsByOwner(userId).stream()
                    .map(BookingMapper::mapResponseToBookingDto)
                    .toList();
        };
    }

    @Override
    public List<ResponseBookingDto> getAllBookingItemForBooker(long userId, State state) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        switch (state) {
            case CURRENT -> {
                return bookingRepository.findCurrentBookings(userId, LocalDateTime.now()).stream()
                        .map(BookingMapper::mapResponseToBookingDto)
                        .toList();
            }
            case PAST -> {
                return bookingRepository.findPastBookings(userId, LocalDateTime.now()).stream()
                        .map(BookingMapper::mapResponseToBookingDto)
                        .toList();
            }
            case FUTURE -> {
                return bookingRepository.findFutureBookings(userId, LocalDateTime.now()).stream()
                        .map(BookingMapper::mapResponseToBookingDto)
                        .toList();
            }
            case WAITING -> {
                return bookingRepository.findWaitingBookings(userId).stream()
                        .map(BookingMapper::mapResponseToBookingDto)
                        .toList();
            }
            case REJECTED -> {
                return bookingRepository.findRejectedBookings(userId).stream()
                        .map(BookingMapper::mapResponseToBookingDto)
                        .toList();
            }
            default -> {
                return bookingRepository.findAllByBooker_IdOrderByStartDateDesc(userId).stream()
                        .map(BookingMapper::mapResponseToBookingDto)
                        .toList();
            }
        }
    }


}
