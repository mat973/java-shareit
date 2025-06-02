package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.exception.CommentFailException;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.ItemRequestNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.ItemMapper.toItemDto;
import static ru.practicum.shareit.item.ItemMapper.toItemsDtoWithDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final RequestRepository requestRepository;

    public List<ItemDto> getItems(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (user.getId() != userId) {
            return itemRepository.findByOwnerId(userId).stream().map(item -> toItemDto(item)).toList();
        }
        List<Item> items = itemRepository.findByOwnerId(userId);
        List<Booking> bookings = bookingRepository.findByItemIdIn(items.stream().map(Item::getId).toList());
        Map<Long, List<Booking>> bookingsByItem = bookings.stream()
                .collect(Collectors.groupingBy(booking -> booking.getItem().getId()));
        return toItemsDtoWithDate(items, bookingsByItem);
    }

    @Override
    @Transactional(readOnly = false)
    public ItemDto addNewItem(Long userId, ItemDto itemDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        ItemRequest itemRequest = null;
        if (itemDto.getRequestId() != null){
            itemRequest = (requestRepository.findById(itemDto.getRequestId()
            ).orElseThrow(() -> new ItemRequestNotFoundException(itemDto.getRequestId())));
        }
        return toItemDto(itemRepository.save(ItemMapper.toItem(itemDto, userId, itemRequest)));
    }


    public void deleteItem(long userId, long itemId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        itemRepository.deleteByOwnerIdAndId(userId, itemId);
    }

    @Override
    @Transactional(readOnly = false)
    public ItemDto updateItem(ItemDto itemDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        Item updateItem = itemRepository.findById(itemDto.getId())
                .orElseThrow(() -> new ItemNotFoundException(itemDto.getId()));
        if (itemDto.getAvailable() != null) {
            updateItem.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getName() != null) {
            updateItem.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            updateItem.setDescription(itemDto.getDescription());
        }
        return toItemDto(updateItem);
    }

    @Override
    public ItemDto getItem(long itemId, long userId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
        List<Booking> bookings = new ArrayList<>();
        if (item.getOwner().getId() == userId) {
            bookings = bookingRepository.findAllByItemIdAndStatus(itemId, Status.APPROVED);
        }
        List<Comment> comments = commentRepository.findByItemId(itemId);
        return ItemMapper.toItemDtoWithComment(item, comments, bookings);
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

    @Override
    @Transactional(readOnly = false)
    public CommentDto createComment(CommentDto commentDto, long userId, Long itemId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
        boolean hasUsed = bookingRepository.hasUserBookedItemBefore(
                userId, itemId, LocalDateTime.now(), Status.APPROVED
        );
        if (!hasUsed) {
            throw new CommentFailException("Пользователь не брал вещь или аренда не завершена");
        }
        Comment comment = Comment.builder()
                .text(commentDto.getText())
                .item(item)
                .author(user)
                .created(LocalDateTime.now())
                .build();
        return CommentMapper.toDto(commentRepository.save(comment));
    }
}
