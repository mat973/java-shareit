package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CommentTest {

    @Test
    void testCommentEqualsAndHashCode() {
        Comment comment1 = Comment.builder()
                .id(1L)
                .text("Great item!")
                .created(LocalDateTime.now())
                .build();

        Comment comment2 = Comment.builder()
                .id(1L)
                .text("Totally different text")
                .created(LocalDateTime.now().minusDays(1))
                .build();

        Comment comment3 = Comment.builder()
                .id(2L)
                .text("Other comment")
                .created(LocalDateTime.now())
                .build();

        assertThat(comment1).isEqualTo(comment2);
        assertThat(comment1).hasSameHashCodeAs(comment2);
        assertThat(comment1).isNotEqualTo(comment3);
    }

    @Test
    void testCommentBuilderAndFields() {
        User author = User.builder().id(1L).name("John").email("john@example.com").build();
        Item item = Item.builder().id(2L).name("Item").description("Desc").available(true).owner(author).build();
        LocalDateTime now = LocalDateTime.now();

        Comment comment = Comment.builder()
                .id(3L)
                .text("Nice item!")
                .author(author)
                .item(item)
                .created(now)
                .build();

        assertThat(comment.getId()).isEqualTo(3L);
        assertThat(comment.getText()).isEqualTo("Nice item!");
        assertThat(comment.getAuthor()).isEqualTo(author);
        assertThat(comment.getItem()).isEqualTo(item);
        assertThat(comment.getCreated()).isEqualTo(now);
    }
}
