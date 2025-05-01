package ru.practicum.shareit.item;


import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public class ItemMapper {
    public static Item toItem(ItemDto itemDto, Long userId) {
        return Item.builder()
                .id(itemDto.getId())
                .userId(userId)
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
}
