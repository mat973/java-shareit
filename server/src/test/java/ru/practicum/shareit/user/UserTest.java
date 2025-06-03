package ru.practicum.shareit.user;


import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.model.User;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void testEqualsAndHashCode() {
        User user1 = User.builder().id(1L).email("a@mail.com").build();
        User user2 = User.builder().id(1L).email("a@mail.com").build();
        User user3 = User.builder().id(2L).email("b@mail.com").build();

        assertThat(user1).isEqualTo(user2);
        assertThat(user1).hasSameHashCodeAs(user2);
        assertThat(user1).isNotEqualTo(user3);
    }

    @Test
    void testBuilderAndFields() {
        User user = User.builder().id(1L).name("Alice").email("alice@mail.com").build();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("Alice");
        assertThat(user.getEmail()).isEqualTo("alice@mail.com");
    }
}

