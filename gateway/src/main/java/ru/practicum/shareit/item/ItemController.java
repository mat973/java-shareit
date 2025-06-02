package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {

    private final ItemClient itemClient;

    @GetMapping
    public ResponseEntity<Object> getItems(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Gateway: Получение вещей пользователя с id={}", userId);
        return itemClient.getItems(userId);
    }

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                                          @Validated @RequestBody ItemDto itemDto) {
        log.info("Gateway: Создание новой вещи {} пользователем {}", itemDto, userId);
        return itemClient.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                                             @RequestBody ItemDto itemDto,
                                             @PathVariable Long itemId) {
        log.info("Gateway: Обновление вещи с id={}, полями {}, пользователем {}", itemId, itemDto, userId);
        return itemClient.updateItem(userId, itemId, itemDto);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Object> deleteItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable long itemId) {
        log.info("Gateway: Удаление вещи с id={} пользователем {}", itemId, userId);
        return itemClient.deleteItem(userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                          @PathVariable long itemId) {
        log.info("Gateway: Получение вещи с id={} пользователем {}", itemId, userId);
        return itemClient.getItem(userId, itemId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam String text) {
        log.info("Gateway: Поиск вещей по описанию '{}'", text);
        return itemClient.search(text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@Validated @RequestBody CommentDto commentDto,
                                                @RequestHeader("X-Sharer-User-Id") long userId,
                                                @PathVariable Long itemId) {
        log.info("Gateway: Создание комментария {} пользователем {} к вещи {}", commentDto, userId, itemId);
        return itemClient.createComment(userId, itemId, commentDto);
    }
}


