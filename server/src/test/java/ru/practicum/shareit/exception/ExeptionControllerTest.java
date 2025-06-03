package ru.practicum.shareit.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DummyController.class)
@AutoConfigureMockMvc
@Import(ExeptionController.class)
class ExeptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("should return 404 for ItemNotFoundException")
    void testItemNotFoundException() throws Exception {
        mockMvc.perform(get("/test/item-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Предмет с id 1 на найден"));
    }

    @Test
    void testUserNotFoundException() throws Exception {
        mockMvc.perform(get("/test/user-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Пользователь с id 1 не найден"));
    }

    @Test
    void testNotUnicEmailException() throws Exception {
        mockMvc.perform(get("/test/email-conflict"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Email already exists"));
    }

    @Test
    void testForbidden() throws Exception {
        mockMvc.perform(get("/test/permission-denied"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error").value("Access denied"));
    }

    @Test
    void testBookingNotFoundException() throws Exception {
        mockMvc.perform(get("/test/booking-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Бронирование с id 1 не найдено."));
    }

    @Test
    void testGenericExceptionHandler() throws Exception {
        mockMvc.perform(get("/test/unexpected"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Unexpected error"));
    }
}
