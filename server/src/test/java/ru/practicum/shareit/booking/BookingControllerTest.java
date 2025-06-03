package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.model.State;
import ru.practicum.shareit.booking.model.Status;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    private BookingDto bookingDto;
    private ResponseBookingDto responseDto;

    @BeforeEach
    void setUp() {
        bookingDto = BookingDto.builder()
                .itemId(1L)
                .start("2025-12-04T10:00:00")
                .end("2025-12-05T10:00:00")
                .build();

        responseDto = ResponseBookingDto.builder()
                .id(1L)
                .status(Status.WAITING)
                .build();
    }

    @Test
    void createBooking_shouldReturn200() throws Exception {
        when(bookingService.createBooking(any(), eq(1L))).thenReturn(responseDto);

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookingDto))
                        .header("X-Sharer-User-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void approveBooking_shouldReturnApproved() throws Exception {
        responseDto.setStatus(Status.APPROVED);
        when(bookingService.decideBooking(1L, 1L, true)).thenReturn(responseDto);

        mockMvc.perform(patch("/bookings/1")
                        .param("approved", "true")
                        .header("X-Sharer-User-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"));
    }

    @Test
    void getBooking_shouldReturnBooking() throws Exception {
        when(bookingService.getBookingInfo(1L, 1L)).thenReturn(responseDto);

        mockMvc.perform(get("/bookings/1")
                        .header("X-Sharer-User-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getAllBookingsForBooker_shouldReturnList() throws Exception {
        when(bookingService.getAllBookingItemForBooker(1L, State.ALL))
                .thenReturn(List.of(responseDto));

        mockMvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", "1")
                        .param("state", "ALL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getAllBookingsForOwner_shouldReturnList() throws Exception {
        when(bookingService.getAllBookingItemForOwner(1L, State.ALL))
                .thenReturn(List.of(responseDto));

        mockMvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", "1")
                        .param("state", "ALL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}
