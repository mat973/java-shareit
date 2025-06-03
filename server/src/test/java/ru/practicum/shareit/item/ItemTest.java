package ru.practicum.shareit.item;


import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    @Test
    void testEqualsAndHashCode() {
        Item item1 = Item.builder().id(1L).name("Drill").build();
        Item item2 = Item.builder().id(1L).name("Other").build();
        Item item3 = Item.builder().id(2L).name("Different").build();

        assertThat(item1).isEqualTo(item2);
        assertThat(item1).hasSameHashCodeAs(item2);
        assertThat(item1).isNotEqualTo(item3);
    }

    @Test
    void testBuilderAndFields() {
        User owner = User.builder().id(1L).name("John").email("john@example.com").build();
        ItemRequest request = ItemRequest.builder().id(1L).description("Need drill").build();

        Item item = Item.builder()
                .id(2L)
                .name("Drill")
                .description("Good drill")
                .available(true)
                .owner(owner)
                .itemRequest(request)
                .build();

        assertThat(item.getName()).isEqualTo("Drill");
        assertThat(item.getDescription()).isEqualTo("Good drill");
        assertThat(item.getOwner()).isEqualTo(owner);
        assertThat(item.getItemRequest()).isEqualTo(request);
        assertThat(item.getAvailable()).isTrue();
    }
}
