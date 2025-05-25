package ru.practicum.shareit.item;


import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<ItemDto> getItems(long userId);

    ItemDto addNewItem(Long userId, ItemDto item);

    void deleteItem(long userId, long itemId);

    ItemDto updateItem(ItemDto itemDto, Long userId);

    ItemDto getItem(long itemId, long userId);

    List<ItemDto> getItemsByDescription(String text);

    boolean existItemById(Long itemId);

    Optional<Item> getClearIem(Long itemId);

    CommentDto createComment(CommentDto commentDto, long userId, Long itemId);
}
