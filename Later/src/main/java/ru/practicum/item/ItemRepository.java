package ru.practicum.item;

import java.util.List;

public interface ItemRepository {

    List<Item> findByUserId(long userId);

    Item save(Item item);

    void deleteByUserIdAndItemId(long userId, long itemId);

    Item update(Item item);

    Boolean existById(Long itemId);

    Item getItemById(long itemId);

    List<Item> getItemsByDiscription(String text);
}