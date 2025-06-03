package ru.practicum.shareit.item;


import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ItemMapperTest {

    @Test
    void toItem_shouldMapCorrectly() {
        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("Drill")
                .description("Powerful drill")
                .available(true)
                .requestId(99L)
                .build();

        Long userId = 10L;
        ItemRequest request = ItemRequest.builder()
                .id(99L)
                .description("Need drill")
                .build();

        Item item = ItemMapper.toItem(itemDto, userId, request);

        assertEquals(itemDto.getId(), item.getId());
        assertEquals(itemDto.getName(), item.getName());
        assertEquals(itemDto.getDescription(), item.getDescription());
        assertEquals(itemDto.getAvailable(), item.getAvailable());
        assertEquals(userId, item.getOwner().getId());
        assertEquals(request, item.getItemRequest());
    }

    @Test
    void toItemDto_shouldMapCorrectly() {
        ItemRequest request = ItemRequest.builder().id(99L).build();

        Item item = Item.builder()
                .id(1L)
                .name("Hammer")
                .description("Heavy hammer")
                .available(false)
                .itemRequest(request)
                .build();

        ItemDto dto = ItemMapper.toItemDto(item);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getDescription(), dto.getDescription());
        assertEquals(item.getAvailable(), dto.getAvailable());
        assertEquals(request.getId(), dto.getRequestId());
    }

    @Test
    void toItemDtoWithComment_shouldMapWithBookingsAndComments() {
        LocalDateTime now = LocalDateTime.now();

        Item item = Item.builder()
                .id(1L)
                .name("Saw")
                .description("Electric saw")
                .available(true)
                .build();

        Booking pastBooking = Booking.builder()
                .id(1L)
                .startDate(now.minusDays(5))
                .endDate(now.minusDays(2))
                .build();

        Booking futureBooking = Booking.builder()
                .id(2L)
                .startDate(now.plusDays(1))
                .endDate(now.plusDays(2))
                .build();

        Comment comment = Comment.builder()
                .id(5L)
                .text("Great tool")
                .author(User.builder().id(2L).name("User1").build())
                .created(now.minusDays(1))
                .build();

        ItemDto dto = ItemMapper.toItemDtoWithComment(item, List.of(comment), List.of(pastBooking, futureBooking));

        assertEquals(item.getId(), dto.getId());
        assertEquals(1, dto.getComments().size());

        assertNotNull(dto.getLastBooking());
        assertEquals(pastBooking.getStartDate(), dto.getLastBooking().getStart());
        assertEquals(pastBooking.getEndDate(), dto.getLastBooking().getEnd());

        assertNotNull(dto.getNextBooking());
        assertEquals(futureBooking.getStartDate(), dto.getNextBooking().getStart());
        assertEquals(futureBooking.getEndDate(), dto.getNextBooking().getEnd());
    }

    @Test
    void toItemsDtoWithDate_shouldMapListWithBookings() {
        LocalDateTime now = LocalDateTime.now();

        Item item1 = Item.builder()
                .id(1L)
                .name("Item1")
                .description("Desc1")
                .available(true)
                .build();

        Booking past = Booking.builder()
                .startDate(now.minusDays(3))
                .endDate(now.minusDays(1))
                .build();

        Booking future = Booking.builder()
                .startDate(now.plusDays(1))
                .endDate(now.plusDays(2))
                .build();

        Map<Long, List<Booking>> bookingsByItem = Map.of(
                item1.getId(), List.of(past, future)
        );

        List<ItemDto> dtos = ItemMapper.toItemsDtoWithDate(List.of(item1), bookingsByItem);

        assertEquals(1, dtos.size());
        ItemDto dto = dtos.get(0);

        assertEquals(item1.getName(), dto.getName());
        assertNotNull(dto.getLastBooking());
        assertNotNull(dto.getNextBooking());
    }
}


