package ru.practicum.shareit.item;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByOwnerId(Long ownerId);

    void deleteByOwnerIdAndId(Long ownerId, long itemId);

    boolean existsById(Long itemId);


    @Query(" select i from Item i " +
            "where i.available = TRUE AND upper(i.name) like upper(concat('%', ?1, '%')) " +
            " or upper(i.description) like upper(concat('%', ?1, '%'))")
    List<Item> getItemsByDescription(String text);
}