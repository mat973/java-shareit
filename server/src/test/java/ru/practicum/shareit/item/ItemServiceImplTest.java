package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock private UserRepository userRepository;
    @Mock private BookingRepository bookingRepository;
    @Mock private CommentRepository commentRepository;
    @Mock private RequestRepository requestRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    private User user;
    private Item item;
    private ItemDto itemDto;

    @BeforeEach
    void setUp() {
        user = new User(1L, "test@mail.com", "Test User");
        item = Item.builder()
                .id(1L)
                .name("Drill")
                .description("Powerful drill")
                .available(true)
                .owner(user)
                .build();

        itemDto = ItemDto.builder()
                .id(1L)
                .name("Drill")
                .description("Powerful drill")
                .available(true)
                .build();
    }

    @Test
    void addNewItem_shouldSaveItem() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(itemRepository.save(any())).thenReturn(item);

        ItemDto result = itemService.addNewItem(1L, itemDto);

        assertEquals(item.getId(), result.getId());
        verify(itemRepository).save(any());
    }

    @Test
    void getItems_shouldReturnItemsWithBookings() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(itemRepository.findByOwnerId(1L)).thenReturn(List.of(item));
        when(bookingRepository.findByItemIdIn(any())).thenReturn(List.of());

        List<ItemDto> result = itemService.getItems(1L);

        assertEquals(1, result.size());
        assertEquals(item.getId(), result.get(0).getId());
    }

    @Test
    void getItem_shouldReturnItemDtoWithComment() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(commentRepository.findByItemId(1L)).thenReturn(List.of());

        ItemDto result = itemService.getItem(1L, 999L); // Не владелец — без бронирований

        assertEquals(item.getId(), result.getId());
    }

    @Test
    void updateItem_shouldUpdateFields() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        itemDto.setName("Updated Drill");
        itemDto.setDescription("Updated Description");

        ItemDto result = itemService.updateItem(itemDto, 1L);

        assertEquals("Updated Drill", result.getName());
        assertEquals("Updated Description", result.getDescription());
    }

    @Test
    void createComment_shouldReturnCommentDto() {
        CommentDto commentDto = CommentDto.builder().text("Nice item").build();
        Comment comment = Comment.builder().id(1L).text("Nice item").author(user).item(item).created(LocalDateTime.now()).build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(bookingRepository.hasUserBookedItemBefore(eq(1L), eq(1L), any(), eq(Status.APPROVED))).thenReturn(true);
        when(commentRepository.save(any())).thenReturn(comment);

        CommentDto result = itemService.createComment(commentDto, 1L, 1L);

        assertEquals("Nice item", result.getText());
        assertEquals(user.getName(), result.getAuthorName());
    }
}

