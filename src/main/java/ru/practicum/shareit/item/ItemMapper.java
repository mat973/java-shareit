package ru.practicum.shareit.item;


import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.BookingDate;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ItemMapper {
    public static Item toItem(ItemDto itemDto, Long userId) {
        return Item.builder()
                .id(itemDto.getId())
                .owner(User.builder()
                        .id(userId)
                        .build())
                .description(itemDto.getDescription())
                .name(itemDto.getName())
                .available(itemDto.getAvailable())
                .build();
    }

    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public static ItemDto toItemDtoWithComment(Item item, List<Comment> comments, List<Booking> bookings) {
        Booking last = bookings.stream()
                .filter(b -> b.getStartDate().isBefore(LocalDateTime.now()))
                .max(Comparator.comparing(Booking::getEndDate))
                .orElse(null);

        Booking next = bookings.stream()
                .filter(b -> b.getStartDate().isAfter(LocalDateTime.now()))
                .min(Comparator.comparing(Booking::getStartDate))
                .orElse(null);

        List<CommentDto> commentDtos = comments.stream().map(CommentMapper::toDto).toList();
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .comments(commentDtos)
                .lastBooking(last != null ? new BookingDate(last.getStartDate(), last.getEndDate()) : null)
                .nextBooking(next != null ? new BookingDate(next.getStartDate(), next.getEndDate()) : null)
                .build();
    }

    public static List<ItemDto> toItemsDtoWithDate(List<Item> items, Map<Long, List<Booking>> bookingsByItem) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        for (Item item : items) {
            List<Booking> bookings = bookingsByItem.getOrDefault(item.getId(), List.of());

            Booking last = bookings.stream()
                    .filter(b -> b.getStartDate().isBefore(LocalDateTime.now()))
                    .max(Comparator.comparing(Booking::getEndDate))
                    .orElse(null);

            Booking next = bookings.stream()
                    .filter(b -> b.getStartDate().isAfter(LocalDateTime.now()))
                    .min(Comparator.comparing(Booking::getStartDate))
                    .orElse(null);
            itemDtoList.add(ItemDto.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .description(item.getDescription())
                    .available(item.getAvailable())
                    .lastBooking(last != null ? new BookingDate(last.getStartDate(), last.getEndDate()) : null)
                    .nextBooking(next != null ? new BookingDate(next.getStartDate(), next.getEndDate()) : null)
                    .build());
        }
        return itemDtoList;
    }
}
