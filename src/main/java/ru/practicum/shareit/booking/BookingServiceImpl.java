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
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;

import static ru.practicum.shareit.booking.BookingMapper.mapToBooking;
import static ru.practicum.shareit.booking.BookingMapper.mapResponseToBookingDto;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final UserService userService;
    private final ItemService itemService;
    private final BookingRepository bookingRepository;

    @Override
    public ResponseBookingDto createBooking(BookingDto bookingDto, long userId) {

        User booker = userService.getClearUser(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Item item = itemService.getClearIem(bookingDto.getItemId())
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
        if (booking.getItem().getOwner().getId() != userId){
            throw new PermitionDenidedException("Только владлц вещи может давать разршни или отклонять бронирование");
        }
        if (approved) {
            booking.setStatus(Status.APPROVED);
        }else {
            booking.setStatus(Status.REJECTED);
        }
        return mapResponseToBookingDto(booking);
    }

    @Override
    public ResponseBookingDto getBookingInfo(Long bookingId, long userId) {
        return null;
    }

    @Override
    public List<ResponseBookingDto> getAllBookingItemForOwner(long userId, State state) {
        return List.of();
    }

    @Override
    public List<ResponseBookingDto> getAllBookingItemForBooker(long userId, State state) {
        return List.of();
    }
}
