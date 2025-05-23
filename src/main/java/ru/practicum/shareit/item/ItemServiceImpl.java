package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;

    public List<ItemDto> getItems(long userId) {
        return itemRepository.findByOwnerId(userId).stream().map(ItemMapper::toItemDto).toList();
    }

    @Override
    @Transactional
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
        itemRepository.deleteByOwnerIdAndId(userId, itemId);
    }

    @Override
    @Transactional
    public ItemDto updateItem(ItemDto itemDto, Long userId) {
        if (!userService.existUserById(userId)) {
            throw new UserNotFoundException(userId);
        }

        Item updateItem = itemRepository.findById(itemDto.getId()).
                orElseThrow(() -> new ItemNotFoundException(itemDto.getId()));
        if (itemDto.getAvailable() != null){
            updateItem.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getName() != null){
            updateItem.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null){
            updateItem.setDescription(itemDto.getDescription());
        }
        return ItemMapper.toItemDto(updateItem);
    }

    @Override
    public ItemDto getItem(long itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new ItemNotFoundException(itemId);
        }
        return ItemMapper.toItemDto(itemRepository.getById(itemId));
    }

    @Override
    public List<ItemDto> getItemsByDescription(String text) {
        if (text.isBlank()) {
            return List.of();
        }
        return itemRepository.getItemsByDescription(text.toLowerCase()).stream().map(ItemMapper::toItemDto).toList();
    }

    @Override
    public boolean existItemById(Long itemId) {
        return itemRepository.existsById(itemId);
    }

    @Override
    public Optional<Item> getClearIem(Long itemId) {
        return itemRepository.findById(itemId);
    }
}
