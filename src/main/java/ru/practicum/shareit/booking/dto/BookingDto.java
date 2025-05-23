package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@Builder
public class BookingDto {
    private Long id;
    @NotNull
    @NotNull(message = "Дата начала обязательна")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$",
            message = "Неверный формат даты для поля start. Используйте YYYY-MM-DDTHH:mm:ss")
    private String start;
    @NotNull(message = "Дата начала обязательна")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$",
            message = "Неверный формат даты поля end. Используйте YYYY-MM-DDTHH:mm:ss")
    private String end;
    @NotNull(message = "Бронируемый предмт должент иметь id")
    @Positive(message = "id предмета неможет быть негативным")
    private Long itemId;
    private Long bookerId;
    private Status status;
}
