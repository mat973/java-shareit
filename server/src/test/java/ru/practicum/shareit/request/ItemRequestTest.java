package ru.practicum.shareit.request;


import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRequestTest {

    @Test
    void testBuilderAndFields() {
        User requester = User.builder().id(1L).name("Bob").email("bob@mail.com").build();
        LocalDateTime created = LocalDateTime.now();

        ItemRequest request = ItemRequest.builder()
                .id(5L)
                .description("Need something")
                .created(created)
                .requester(requester)
                .build();

        assertThat(request.getId()).isEqualTo(5L);
        assertThat(request.getDescription()).isEqualTo("Need something");
        assertThat(request.getCreated()).isEqualTo(created);
        assertThat(request.getRequester()).isEqualTo(requester);
    }
}

