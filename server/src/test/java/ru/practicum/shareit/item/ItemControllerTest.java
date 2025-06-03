package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getItems_shouldReturnUserItems() throws Exception {
        long userId = 1L;
        ItemDto item = ItemDto.builder()
                .id(1L)
                .name("item name")
                .description("desc")
                .available(true)
                .build();

        Mockito.when(itemService.getItems(userId)).thenReturn(List.of(item));

        mockMvc.perform(get("/items")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(item.getId()))
                .andExpect(jsonPath("$[0].name").value(item.getName()))
                .andExpect(jsonPath("$[0].description").value(item.getDescription()))
                .andExpect(jsonPath("$[0].available").value(item.getAvailable()));
    }

    @Test
    void addItem_shouldReturnCreatedItem() throws Exception {
        long userId = 1L;
        ItemDto item = ItemDto.builder()
                .name("item")
                .description("desc")
                .available(true)
                .build();

        ItemDto created = ItemDto.builder()
                .id(10L)
                .name("item")
                .description("desc")
                .available(true)
                .build();

        Mockito.when(itemService.addNewItem(eq(userId), any(ItemDto.class))).thenReturn(created);

        mockMvc.perform(post("/items")
                        .header("X-Sharer-User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(created.getId()))
                .andExpect(jsonPath("$.name").value("item"));
    }

    @Test
    void updateItem_shouldReturnUpdatedItem() throws Exception {
        long userId = 1L;
        long itemId = 42L;

        ItemDto update = ItemDto.builder()
                .name("updated")
                .description("updated desc")
                .available(false)
                .build();

        ItemDto result = ItemDto.builder()
                .id(itemId)
                .name("updated")
                .description("updated desc")
                .available(false)
                .build();

        Mockito.when(itemService.updateItem(any(ItemDto.class), eq(userId))).thenReturn(result);

        mockMvc.perform(patch("/items/{itemId}", itemId)
                        .header("X-Sharer-User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemId))
                .andExpect(jsonPath("$.name").value("updated"));
    }

    @Test
    void deleteItem_shouldReturnOk() throws Exception {
        long userId = 1L;
        long itemId = 2L;

        mockMvc.perform(delete("/items/{itemId}", itemId)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());

        Mockito.verify(itemService).deleteItem(userId, itemId);
    }

    @Test
    void getItem_shouldReturnItemWithDetails() throws Exception {
        long userId = 1L;
        long itemId = 1L;

        ItemDto item = ItemDto.builder()
                .id(itemId)
                .name("item")
                .description("desc")
                .available(true)
                .build();

        Mockito.when(itemService.getItem(itemId, userId)).thenReturn(item);

        mockMvc.perform(get("/items/{itemId}", itemId)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemId));
    }

    @Test
    void searchItems_shouldReturnMatchingItems() throws Exception {
        String text = "search";
        ItemDto found = ItemDto.builder()
                .id(5L)
                .name("search result")
                .description("desc")
                .available(true)
                .build();

        Mockito.when(itemService.getItemsByDescription(text)).thenReturn(List.of(found));

        mockMvc.perform(get("/items/search")
                        .param("text", text))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(found.getId()));
    }

    @Test
    void createComment_shouldReturnCommentDto() throws Exception {
        long userId = 1L;
        long itemId = 1L;
        CommentDto comment = CommentDto.builder()
                .text("Nice item")
                .build();

        CommentDto saved = CommentDto.builder()
                .id(100L)
                .text("Nice item")
                .authorName("Test User")
                .created(LocalDateTime.now())
                .build();

        Mockito.when(itemService.createComment(eq(comment), eq(userId), eq(itemId))).thenReturn(saved);

        mockMvc.perform(post("/items/{itemId}/comment", itemId)
                        .header("X-Sharer-User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.text").value("Nice item"));
    }
}