package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.validinterface.Create;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> get(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Получение веще пользователя с id {}", userId);
        return itemService.getItems(userId);
    }

    @PostMapping
    public ItemDto add(@RequestHeader("X-Sharer-User-Id") Long userId,
                       @Validated(Create.class) @RequestBody ItemDto itemDto) {
        log.info("Создание новой вещи {} пользователем {}", itemDto, userId);
        return itemService.addNewItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @RequestBody ItemDto itemDto, @PathVariable Long itemId) {
        log.info("Обновление вещи с id {}, полями {}, пользователем {}", itemId, itemDto, userId);
        itemDto.setId(itemId);
        return itemService.updateItem(itemDto, userId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@RequestHeader("X-Sharer-User-Id") long userId,
                           @PathVariable(name = "itemId") long itemId) {
        log.info("Удаление вещи {} пользователем с id {}", itemId, userId);
        itemService.deleteItem(userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@RequestHeader("X-Sharer-User-Id") long userId,
                           @PathVariable(name = "itemId") long itemId) {
        log.info("Получение вещи с id {} пользователем с id {}", itemId, userId);
        return itemService.getItem(itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemsByDescription(@RequestParam String text) {
        log.info("Поиск вещи по описанию text {}", text);
        return itemService.getItemsByDescription(text);
    }
}