package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.model.ItemRequest;

interface  RequestRepository extends JpaRepository<Long, ItemRequest> {
}