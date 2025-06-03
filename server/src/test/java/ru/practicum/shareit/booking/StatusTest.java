package ru.practicum.shareit.booking;


import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.model.Status;

import static org.assertj.core.api.Assertions.assertThat;

class StatusTest {

    @Test
    void testEnumValues() {
        assertThat(Status.valueOf("WAITING")).isEqualTo(Status.WAITING);
        assertThat(Status.values()).contains(Status.APPROVED, Status.REJECTED);
    }
}

