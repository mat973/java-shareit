package ru.practicum.item;

import org.springframework.stereotype.Repository;
import ru.practicum.exception.ItemNotFoundException;
import ru.practicum.exception.PermitionDenidedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private static final Map<Long, Item> items = new HashMap<>();
    private static Long currentId = 0L;

    @Override
    public List<Item> findByUserId(long userId) {
        return items.values().stream().filter(item -> item.getUserId().equals(userId)).toList();
    }

    @Override
    public Item save(Item item) {
        item.setId(++currentId);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public void deleteByUserIdAndItemId(long userId, long itemId) {
        Item newItem = items.get(itemId);
        if (newItem.getUserId() != userId) {
            throw new PermitionDenidedException("Только владлен вещи может ее изменять");
        }
        items.remove(findByUserId(userId).stream().filter(item -> item.getId()
                        .equals(itemId)).findFirst()
                .orElseThrow(() -> new ItemNotFoundException(itemId)).getId());
    }

    @Override
    public Item update(Item item) {
        Item newItem = items.get(item.getId());
        if (newItem.getUserId() != item.getUserId()) {
            throw new PermitionDenidedException("Только владлен вещи может ее изменять");
        }
        if (item.getAvailable() != null) {
            newItem.setAvailable(item.getAvailable());
        }
        if (item.getDescription() != null) {
            newItem.setDescription(item.getDescription());
        }
        if (item.getName() != null) {
            newItem.setName(item.getName());
        }
        return newItem;
    }

    @Override
    public Boolean existById(Long itemId) {
        return items.containsKey(itemId);
    }

    @Override
    public Item getItemById(long itemId) {
        return items.get(itemId);
    }

    @Override
    public List<Item> getItemsByDiscription(String text) {
        return items.values().stream()
                .filter(item -> item.getAvailable().equals(Boolean.TRUE)
                        && (item.getDescription().toLowerCase().contains(text)
                        || item.getName().toLowerCase().contains(text)))
                .toList();
    }
}
