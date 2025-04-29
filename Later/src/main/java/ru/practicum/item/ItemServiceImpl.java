package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.exception.UserNotFoundException;
import ru.practicum.user.UserService;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;
    private final UserService userService;
    public List<Item> getItems(long userId) {
        return itemRepository.findByUserId(userId);
    }

    public Item addNewItem(Long userId, Item item) {
        if (!userService.existUserById(userId)){
            throw new UserNotFoundException(userId);
        }
        item.setId(userId);
        return itemRepository.save(item);
    }

    public void deleteItem(long userId, long itemId) {
        if (!userService.existUserById(userId)){
            throw new UserNotFoundException(userId);
        }
        itemRepository.deleteByUserIdAndItemId(userId, itemId);
    }
}
