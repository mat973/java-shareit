package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestMapperTest {

    private final User user = new User(1L, "Иван", "ivan@mail.com");

    @Test
    void mapToItemRequest_shouldMapCorrectly() {
        ItemRequestDto dto = ItemRequestDto.builder()
                .id(10L)
                .description("Нужна отвертка")
                .build();

        ItemRequest request = RequestMapper.mapToItemRequest(dto, user);

        assertEquals(dto.getId(), request.getId());
        assertEquals(dto.getDescription(), request.getDescription());
        assertEquals(user, request.getRequester());
        assertNotNull(request.getCreated());
    }

    @Test
    void mapToItemRequestDto_shouldMapCorrectly() {
        ItemRequest request = ItemRequest.builder()
                .id(20L)
                .description("Нужен молоток")
                .requester(user)
                .created(LocalDateTime.now())
                .build();

        ItemRequestDto dto = RequestMapper.mapToItemRequestDto(request);

        assertEquals(request.getId(), dto.getId());
        assertEquals(request.getDescription(), dto.getDescription());
        assertEquals(user.getId(), dto.getRequesterId());
        assertEquals(request.getCreated(), dto.getCreated());
    }

    @Test
    void mapToItemRequestResponseDto_withItems_shouldMap() {
        ItemRequest request = ItemRequest.builder()
                .id(30L)
                .description("Нужна дрель")
                .requester(user)
                .created(LocalDateTime.now())
                .build();

        Item item = Item.builder()
                .id(5L)
                .name("Дрель")
                .owner(user)
                .itemRequest(request)
                .build();

        ItemRequestResponseDto dto = RequestMapper.mapToItemRequestResponseDto(request, List.of(item));

        assertEquals(1, dto.getItems().size());
        assertEquals(item.getId(), dto.getItems().get(0).getItemId());
        assertEquals(item.getName(), dto.getItems().get(0).getName());
    }

    @Test
    void mapToItemRequestResponseDto_nullItems_shouldHandleGracefully() {
        ItemRequest request = ItemRequest.builder()
                .id(40L)
                .description("Нужен насос")
                .requester(user)
                .created(LocalDateTime.now())
                .build();

        ItemRequestResponseDto dto = RequestMapper.mapToItemRequestResponseDto(request, null);

        assertNotNull(dto.getItems());
        assertTrue(dto.getItems().isEmpty());
    }
}
