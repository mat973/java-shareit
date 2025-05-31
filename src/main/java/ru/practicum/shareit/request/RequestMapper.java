package ru.practicum.shareit.request;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemForRequestDro;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class RequestMapper {
    public static ItemRequest mapToItemRequest(ItemRequestDto itemRequestDto, User user){
        return ItemRequest.builder()
                .id(itemRequestDto.getId())
                .requester(user)
                .description(itemRequestDto.getDescription())
                .created(LocalDateTime.now())
                .build();
    }

    public static  ItemRequestDto mapToItemRequestDto(ItemRequest itemRequest){
        return ItemRequestDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .requesterId(itemRequest.getRequester().getId())
                .build();
    }

    public static ItemRequestResponseDto mapToItemRequestResponseDto(ItemRequest itemRequest, List<Item> items){
        return ItemRequestResponseDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .requesterId(itemRequest.getRequester().getId())
                .items(items == null? Collections.emptyList() :items.stream()
                        .map(x -> new ItemForRequestDro(x.getId(), x.getName(), x.getOwner().getId()))
                        .toList())
                .build();
    }
}
