package ru.practicum.item;

import java.util.List;

public interface ItemService {
    List<ItemDto> getItems(long userId);

    ItemDto addNewItem(Long userId, ItemDto item);

    void deleteItem(long userId, long itemId);

    ItemDto updateItem(ItemDto itemDto, Long userId);

    ItemDto getItem(long itemId);

    List<ItemDto> getItemsByDescription(String text);
}
