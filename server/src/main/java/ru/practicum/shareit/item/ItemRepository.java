package ru.practicum.shareit.item;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByOwnerId(Long ownerId);

    void deleteByOwnerIdAndId(Long ownerId, long itemId);

    boolean existsById(Long itemId);

    List<Item> findByItemRequest_IdIn(List<Long> ids);

    @Query("""
            SELECT i FROM Item i
            WHERE i.available = TRUE
            AND (
                upper(i.name) LIKE upper(concat('%', :text, '%'))
                OR upper(i.description) LIKE upper(concat('%', :text, '%'))
            )
            """)
    List<Item> getItemsByDescription(String text);

}