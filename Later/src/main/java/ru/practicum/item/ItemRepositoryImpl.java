package ru.practicum.item;

import org.springframework.stereotype.Repository;
import ru.practicum.exception.ItemNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ItemRepositoryImpl implements ItemRepository{
    private static final Map<Long, Item> items = new HashMap<>();
    private static Long currentId;
    @Override
    public List<Item> findByUserId(long userId) {
        return items.values().stream().filter(item -> item.getUserId().equals(userId)).toList();
    }

    @Override
    public Item save(Item item) {
        item.setId(++currentId);
        return item;
    }

    @Override
    public void deleteByUserIdAndItemId(long userId, long itemId) {
        items.remove(findByUserId(userId).stream().filter(item -> item.getId()
                .equals(itemId)).findFirst()
                .orElseThrow(() -> new ItemNotFoundException(itemId)).getId());
    }
}
