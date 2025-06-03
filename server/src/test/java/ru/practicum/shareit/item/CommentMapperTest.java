package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentMapperTest {

    @Test
    void toCommentDto_shouldMapCorrectly() {
        User author = User.builder().id(1L).name("Ann").build();

        Comment comment = Comment.builder()
                .id(7L)
                .text("Good")
                .author(author)
                .created(LocalDateTime.of(2024, 12, 12, 12, 0))
                .build();

        CommentDto dto = CommentMapper.toDto(comment);

        assertEquals(comment.getId(), dto.getId());
        assertEquals(comment.getText(), dto.getText());
        assertEquals(comment.getAuthor().getName(), dto.getAuthorName());
        assertEquals(comment.getCreated(), dto.getCreated());
    }
}

