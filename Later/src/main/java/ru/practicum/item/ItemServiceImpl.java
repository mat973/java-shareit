package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.exception.ItemNotFoundException;
import ru.practicum.exception.UserNotFoundException;
import ru.practicum.user.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;

    public List<ItemDto> getItems(long userId) {
        return itemRepository.findByUserId(userId).stream().map(ItemMapper::toItemDto).toList();
    }

    @Override
    public ItemDto addNewItem(Long userId, ItemDto itemDto) {
        if (!userService.existUserById(userId)) {
            throw new UserNotFoundException(userId);
        }

        return ItemMapper.toItemDto(itemRepository.save(ItemMapper.toItem(itemDto, userId)));
    }


    public void deleteItem(long userId, long itemId) {
        if (!userService.existUserById(userId)) {
            throw new UserNotFoundException(userId);
        }
        itemRepository.deleteByUserIdAndItemId(userId, itemId);
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto, Long userId) {
        if (!userService.existUserById(userId)) {
            throw new UserNotFoundException(userId);
        }

        if (!itemRepository.existById(itemDto.getId())) {
            throw new ItemNotFoundException(itemDto.getId());
        }

        return ItemMapper.toItemDto(itemRepository.update(ItemMapper.toItem(itemDto, userId)));
    }

    @Override
    public ItemDto getItem(long itemId) {
        if (!itemRepository.existById(itemId)) {
            throw new ItemNotFoundException(itemId);
        }
        return ItemMapper.toItemDto(itemRepository.getItemById(itemId));
    }

    @Override
    public List<ItemDto> getItemsByDescription(String text) {
        if (text.isBlank()) {
            return List.of();
        }
        return itemRepository.getItemsByDiscription(text.toLowerCase()).stream().map(ItemMapper::toItemDto).toList();
    }
}
