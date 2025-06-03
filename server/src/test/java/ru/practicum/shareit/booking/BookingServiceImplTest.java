package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.State;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.exception.DateInvalidException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private User user;
    private Item item;
    private Booking booking;
    private BookingDto bookingDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        item = new Item();
        item.setId(1L);
        item.setAvailable(true);
        item.setOwner(user);

        bookingDto = BookingDto.builder()
                .id(1L)
                .start(LocalDateTime.now().plusDays(1).toString())
                .end(LocalDateTime.now().plusDays(2).toString())
                .itemId(1L)
                .build();

        booking = Booking.builder()
                .id(1L)
                .booker(user)
                .item(item)
                .startDate(LocalDateTime.parse(bookingDto.getStart()))
                .endDate(LocalDateTime.parse(bookingDto.getEnd()))
                .status(Status.WAITING)
                .build();
    }

    @Test
    void createBooking_shouldReturnResponseBookingDto_whenValidInput() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(bookingRepository.save(any())).thenReturn(booking);

        ResponseBookingDto result = bookingService.createBooking(bookingDto, 1L);

        assertEquals(1L, result.getId());
        verify(bookingRepository).save(any());
    }

    @Test
    void createBooking_shouldThrowException_whenStartInPast() {
        bookingDto.setStart(LocalDateTime.now().minusHours(1).toString());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        assertThrows(DateInvalidException.class, () ->
                bookingService.createBooking(bookingDto, 1L));
    }

    @Test
    void decideBooking_shouldApproveBooking_whenApprovedTrue() {
        booking.setStatus(Status.WAITING);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        ResponseBookingDto result = bookingService.decideBooking(1L, 1L, true);

        assertEquals(Status.APPROVED, result.getStatus());
    }

    @Test
    void decideBooking_shouldRejectBooking_whenApprovedFalse() {
        booking.setStatus(Status.WAITING);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        ResponseBookingDto result = bookingService.decideBooking(1L, 1L, false);

        assertEquals(Status.REJECTED, result.getStatus());
    }

    @Test
    void getBookingInfo_shouldReturnBooking_ifOwnerOrBooker() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        ResponseBookingDto result = bookingService.getBookingInfo(1L, 1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void getAllBookingItemForBooker_shouldReturnAll_whenStateIsAll() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(bookingRepository.findAllByBooker_IdOrderByStartDateDesc(1L))
                .thenReturn(List.of(booking));

        List<ResponseBookingDto> results = bookingService.getAllBookingItemForBooker(1L, State.ALL);

        assertEquals(1, results.size());
    }

    @Test
    void getAllBookingItemForOwner_shouldReturnFuture_whenStateIsFuture() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(bookingRepository.findFutureBookingsByOwner(eq(1L), any()))
                .thenReturn(List.of(booking));

        List<ResponseBookingDto> results = bookingService.getAllBookingItemForOwner(1L, State.FUTURE);

        assertEquals(1, results.size());
    }
}

